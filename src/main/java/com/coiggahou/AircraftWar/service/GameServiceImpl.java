package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.entity.GameRoomDTO;
import com.coiggahou.AircraftWar.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class GameServiceImpl implements GameService{

    private static final String GAME_STATUS_KEY = "Status";
    private static final String GAME_ID_PREFIX = "GameID_";
    private static final String PLAYER_A_ID_KEY = "PlayerID_A";
    private static final String PLAYER_A_SCORE_KEY = "PlayerScore_A";
    private static final String PLAYER_B_ID_KEY = "PlayerID_B";
    private static final String PLAYER_B_SCORE_KEY = "PlayerScore_B";
    private static final String ROOM_CREATE_TIME_KEY = "CreateTime";

    /**
     * 新对局等待中
     */
    private static final int GAME_STATUS_PENDING = 0;

    /**
     * 游戏中
     */
    private static final int GAME_STATUS_PLAYING = 1;

    /**
     * 结算状态
     */
    private static final int GAME_STATUS_SETTLING = 2;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private UserService userService;



//    @Override
//    public ApiResponse startNewGameByUserRequest(String userId) {
//
//        String targetGameId = null;
//
//        // 先扫描有没有等待中的对局
//        Set<String> gameIds = redisTemplate.keys(GAME_ID_PREFIX.concat("*"));
//        for (String gameId : gameIds) {
//            int status = (Integer)redisTemplate.opsForHash().get(gameId, "status");
//            if (GAME_STATUS_PENDING == status) {
//                targetGameId = gameId;
//                break;
//            }
//        }
//
//        // 找到等待中的对局可以加入
//        if (targetGameId != null) {
//            Object playerKeyA = redisTemplate.opsForHash().get(targetGameId, PLAYER_A_ID_KEY);
//            Object playerKeyB = redisTemplate.opsForHash().get(targetGameId, PLAYER_B_ID_KEY);
//            // 正常情况下，如果有Pending的对局，一定是A用户在等待，B用户为空
//            // 其余情况都是不应该出现的
//            if (playerKeyA != null && playerKeyB == null) {
//                redisTemplate.opsForHash().put(targetGameId, PLAYER_B_ID_KEY, userId);
//                return ApiResponse.ofStatusAndData()
//            } else {
//                throw new RuntimeException("不合法对局ID: " + targetGameId);
//            }
//        }
//
//        // 如果没有等待中的，新开一个
//        else {
//            String gameRedisKey = generateNewGame();
//            redisTemplate.opsForHash().put(gameRedisKey, PLAYER_A_ID_KEY, userId);
//        }
//
//
//        return null;
//    }


    /**
     * 创建一个新的游戏房间并加入，等待另一位玩家
     * @return 创建的新房间的ID
     */
    @Override
    public ApiResponse createNewGameRoom(String userId) {
        String generatedRandomUUID = genRandomUUID();
        String gameRedisKey = GAME_ID_PREFIX + generatedRandomUUID;
        redisTemplate.opsForHash().put(gameRedisKey, GAME_STATUS_KEY, GAME_STATUS_PENDING);
        redisTemplate.opsForHash().put(gameRedisKey, PLAYER_A_ID_KEY, userId);
        redisTemplate.opsForHash().put(gameRedisKey, PLAYER_A_SCORE_KEY, 0);
        redisTemplate.opsForHash().put(gameRedisKey, PLAYER_B_SCORE_KEY, 0);
        redisTemplate.opsForHash().put(gameRedisKey, ROOM_CREATE_TIME_KEY, new Date().toString());
        return ApiResponse.ofStatusAndData(ApiResponseStatus.SUCCESS, gameRedisKey);
    }

    /**
     * 获取所有处于等待状态的房间的信息
     * @return
     */
    @Override
    public List<GameRoomDTO> getAllPendingGameRooms() {


        List<GameRoomDTO> pendingGameRooms = new ArrayList<>();

        // 先获取所有房间的信息，过滤出处于pending状态的房间
        Set<String> gameRoomKeys = redisTemplate.keys(GAME_ID_PREFIX.concat("*"));
        for (String gameRoomKey : gameRoomKeys) {

            Collection<Object> hashKeysToQuery = new ArrayList<>();
            hashKeysToQuery.add(GAME_STATUS_KEY);
            hashKeysToQuery.add(ROOM_CREATE_TIME_KEY);
            hashKeysToQuery.add(PLAYER_A_ID_KEY);

            List<Object> queryResults = redisTemplate.opsForHash().multiGet(gameRoomKey, hashKeysToQuery);

            int status = (int) queryResults.get(0);
            String createTimeStr = (String) queryResults.get(1);
            String playerIdA = (String) queryResults.get(2);
            if (GAME_STATUS_PENDING == status) {
                pendingGameRooms.add(new GameRoomDTO(
                        gameRoomKey,
                        status,
                        createTimeStr,
                        playerIdA,
                        userService.getUserById(playerIdA).getUsername(),
                        0,
                        null,
                        null,
                        0
                ));
            }
        }
        return pendingGameRooms;
    }

    @Override
    public ApiResponse joinGameRoom(String gameRoomId, String userId) {
        Boolean requestGameRoomExists = redisTemplate.hasKey(gameRoomId);
        if (Boolean.FALSE.equals(requestGameRoomExists)) {
            return ApiResponse.ofStatus(ApiResponseStatus.GAME_ROOM_NOT_EXIST);
        }
        if (!userService.hasUser(userId)) {
            return ApiResponse.ofStatus(ApiResponseStatus.USER_NOT_EXIST);
        }
        redisTemplate.opsForHash().put(gameRoomId, PLAYER_B_ID_KEY, userId);
        redisTemplate.opsForHash().put(gameRoomId, GAME_STATUS_KEY, GAME_STATUS_PLAYING);
        return ApiResponse.ofStatus(ApiResponseStatus.GAME_ROOM_READY);
    }

    /**
     * 从Redis里根据gameRoomId取出整个Hash，封装成GameRoomDTO
     * @param roomId
     * @return
     */
    private GameRoomDTO getFullRoomInfoFromRedis(String roomId) {
        if (roomId == null) {
            return null;
        }
        Collection<Object> hashKeysToQuery = new ArrayList<>();
        hashKeysToQuery.add(GAME_STATUS_KEY);
        hashKeysToQuery.add(ROOM_CREATE_TIME_KEY);
        hashKeysToQuery.add(PLAYER_A_ID_KEY);
        hashKeysToQuery.add(PLAYER_A_SCORE_KEY);
        hashKeysToQuery.add(PLAYER_B_ID_KEY);
        hashKeysToQuery.add(PLAYER_B_SCORE_KEY);
        List<Object> queryResults = redisTemplate.opsForHash().multiGet(roomId, hashKeysToQuery);
        Integer status = (Integer) queryResults.get(0);
        String createTimeStr = (String) queryResults.get(1);
        String playerIdA = (String) queryResults.get(2);
        Integer playerScoreA = (Integer) queryResults.get(3);
        String playerIdB = (String) queryResults.get(4);
        Integer playerScoreB = (Integer) queryResults.get(5);

        User playerA = userService.getUserById(playerIdA);
        User playerB = userService.getUserById(playerIdB);

        return new GameRoomDTO(
                roomId,
                status,
                createTimeStr,
                playerIdA,
                playerA == null ? "" : playerA.getUsername(),
                playerScoreA == null ? 0 : playerScoreA,
                playerIdB,
                playerB == null ? "" : playerB.getUsername(),
                playerScoreB == null ? 0 : playerScoreB
        );
    }

    @Override
    public ApiResponse getRoomInfo(String roomId) {
        if (roomId == null) {
            return ApiResponse.ofStatus(ApiResponseStatus.SERVER_ERROR);
        }
        GameRoomDTO gameRoomInfo = getFullRoomInfoFromRedis(roomId);
        return ApiResponse.ofStatusAndData(ApiResponseStatus.SUCCESS, gameRoomInfo);
    }

    /**
     * 客户端定时发送请求，同步对局状态
     * @param gameRoomId
     * @param userId 客户端用户id
     * @param score 该用户在发出请求时的得分
     * @return
     */
    @Override
    public ApiResponse updateGameInfoById(String gameRoomId, String userId, int score) {
        Boolean gameRoomExists = redisTemplate.hasKey(gameRoomId);
        boolean userExists = userService.hasUser(userId);
        if (Boolean.FALSE.equals(gameRoomExists) || !userExists) {
            return ApiResponse.ofStatus(ApiResponseStatus.VALIDATION_ERROR);
        }
        GameRoomDTO gameRoomInfo = getFullRoomInfoFromRedis(gameRoomId);
        String playerIdA = gameRoomInfo.getPlayerIdA();
        String playerIdB = gameRoomInfo.getPlayerIdB();

        // 通过比对id判断用户是A还是B，更新分数
        if (Objects.equals(playerIdA, userId)) {
            redisTemplate.opsForHash().put(gameRoomId, PLAYER_A_SCORE_KEY, score);
        } else if (Objects.equals(playerIdB, userId)) {
            redisTemplate.opsForHash().put(gameRoomId, PLAYER_B_SCORE_KEY, score);
        } else {
            return ApiResponse.ofStatus(ApiResponseStatus.SERVER_ERROR);
        }
        return ApiResponse.ofStatusAndData(
                ApiResponseStatus.SUCCESS,
                gameRoomInfo
                );

    }

    /**
     * 生成一串UUID作为房间号码
     * @return UUID String
     */
    private String genRandomUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidStr = uuid.toString().replace("-", "");
        return uuidStr;
    }
}

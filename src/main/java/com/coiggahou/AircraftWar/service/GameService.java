package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.entity.GameRoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GameService {
//    String startNewGameByUserRequest(String userId);

    /**
     * 创建新的游戏房间
     * @return 返回新创建的房间ID
     */
    ApiResponse createNewGameRoom(String userId);

    /**
     * 获取所有处于等待中状态的游戏房间
     * @return 等待游戏房间列表
     */
    List<GameRoomDTO> getAllPendingGameRooms();

    /**
     * 玩家加入一个游戏房间
     * @param gameRoomId 带前缀的游戏房间ID
     * @param userId 要加入房间的用户ID
     * @return
     */
    ApiResponse joinGameRoom(String gameRoomId, String userId);

    /**
     * 获取房间信息
     * @param roomId
     * @return
     */
    ApiResponse getRoomInfo(String roomId);

    ApiResponse updateGameInfoById(String gameRoomId, String userId, int score);
}

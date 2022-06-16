package com.coiggahou.AircraftWar.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coiggahou.AircraftWar.dto.UserInfoDTO;
import com.coiggahou.AircraftWar.dto.UserLoginDTO;
import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.entity.GameRecord;
import com.coiggahou.AircraftWar.entity.User;
import com.coiggahou.AircraftWar.mapper.GameRecordMapper;
import com.coiggahou.AircraftWar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GameRecordMapper gameRecordMapper;


    private boolean validateCredentials(UserLoginDTO userLoginDTO) {
        String username = userLoginDTO.getUsername();
        String password = userLoginDTO.getPassword();
        if (Objects.isNull(username) || Objects.isNull(password)) {
            return false;
        }
        if (Objects.equals(username, "") || Objects.equals(password, "")) {
            return false;
        }
        return true;
    }


    /**
     * 用户注册业务逻辑
     * @param userLoginDTO
     * @return
     */
    @Override
    public ApiResponse register(UserLoginDTO userLoginDTO) {
        if (!validateCredentials(userLoginDTO)) {
            return ApiResponse.ofStatus(ApiResponseStatus.VALIDATION_ERROR);
        }

        // 用户已存在，无法注册
        if (userMapper.exists(new QueryWrapper<User>().eq("username", userLoginDTO.getUsername()))) {
            return ApiResponse.ofStatus(ApiResponseStatus.REGISTER_FAILED);
        }
        int affectedRows = userMapper.insert(new User(null, userLoginDTO.getUsername(), userLoginDTO.getPassword(), null, 0, null));
        if (affectedRows < 1) {
            return ApiResponse.ofStatus(ApiResponseStatus.REGISTER_FAILED);
        }
        return ApiResponse.ofStatus(ApiResponseStatus.SUCCESS);
    }

    private UserInfoDTO getUserVisibleInfo(String userId) {
        User selectedUser = userMapper.selectById(userId);
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("user_id", userId);
        List<GameRecord> relatedGameRecords = gameRecordMapper.selectByMap(queryMap);
        return new UserInfoDTO(
                selectedUser.getId(),
                selectedUser.getUsername(),
                selectedUser.getAvatarPath(),
                selectedUser.getCoin(),
                relatedGameRecords
        );
    }
    /**
     * 登录逻辑
     * @param userLoginDTO
     * @return
     */
    @Override
    public ApiResponse login(UserLoginDTO userLoginDTO) {
        if (!validateCredentials(userLoginDTO)) {
            return ApiResponse.ofStatus(ApiResponseStatus.VALIDATION_ERROR);
        }
        // 用户不存在则无法登录
        if (!userMapper.exists(new QueryWrapper<User>().eq("username", userLoginDTO.getUsername()))) {
            return ApiResponse.ofStatus(ApiResponseStatus.USER_NOT_EXIST);
        }
        Map<String, Object> userQueryMap = new HashMap<>();
        userQueryMap.put("username", userLoginDTO.getUsername());
        userQueryMap.put("password", userLoginDTO.getPassword());
        List<User> users = userMapper.selectByMap(userQueryMap);
        if (users.size() == 1) {
            User loggedinUser = users.get(0);
            return ApiResponse.ofStatusAndData(ApiResponseStatus.SUCCESS, getUserVisibleInfo(loggedinUser.getId()));
        } else if (users.size() > 1) {
            return ApiResponse.ofStatus(ApiResponseStatus.SERVER_ERROR);
        } else {
            return ApiResponse.ofStatus(ApiResponseStatus.USERNAME_OR_PASSWORD_ERROR);
        }
    }

    @Override
    public boolean hasUser(String userId) {
        User user = userMapper.selectById(userId);
        return user != null;
    }

    @Override
    public User getUserById(String userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    @Override
    public UserInfoDTO getUserInfo(String userId) {
        if (!hasUser(userId)) {
            return null;
        }
        return getUserVisibleInfo(userId);
    }
}

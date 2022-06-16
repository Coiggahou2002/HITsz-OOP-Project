package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.dto.UserInfoDTO;
import com.coiggahou.AircraftWar.dto.UserLoginDTO;
import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ApiResponse register(UserLoginDTO userLoginDTO);
    ApiResponse login(UserLoginDTO userLoginDTO);
    boolean hasUser(String userId);
    User getUserById(String userId);
    UserInfoDTO getUserInfo(String userId);
}

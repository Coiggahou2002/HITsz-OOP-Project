package com.coiggahou.AircraftWar.controller;

import com.coiggahou.AircraftWar.dto.UserInfoDTO;
import com.coiggahou.AircraftWar.dto.UserLoginDTO;
import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author coiggahou
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 注册接口
     * @param userLoginDTO
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public ApiResponse register(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.register(userLoginDTO);
    }

    /**
     * 登录接口
     * @param userLoginDTO
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ApiResponse login(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO);
    }

    @GetMapping("get_info")
    @ResponseBody
    public ApiResponse getInfo(@RequestParam String userId) {
        UserInfoDTO userInfoDTO = userService.getUserInfo(userId);
        if (userInfoDTO == null) {
            return ApiResponse.ofStatus(ApiResponseStatus.REQUEST_PARAM_ERROR);
        }
        return ApiResponse.ofStatusAndData(ApiResponseStatus.SUCCESS, userInfoDTO);
    }
}

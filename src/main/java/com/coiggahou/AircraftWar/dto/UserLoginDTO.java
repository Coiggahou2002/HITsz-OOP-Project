package com.coiggahou.AircraftWar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户DTO，用于登录注册
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    private String username;
    private String password;
}

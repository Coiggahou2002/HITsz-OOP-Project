package com.coiggahou.AircraftWar.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private Integer code;
    private String msg;
    private Object data;

    public static ApiResponse ofStatus(ApiResponseStatus status) {
        return new ApiResponse(status.getCode(), status.getMsg(), null);
    }
}

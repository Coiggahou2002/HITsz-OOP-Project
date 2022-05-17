package com.coiggahou.AircraftWar.controller;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author coiggahou
 */
@Controller
public class TestController {
    @GetMapping("/test")
    @ResponseBody
    public ApiResponse<String> test() {
        return new ApiResponse<String>(200, "good", "happy");
    }
}

package com.coiggahou.AircraftWar.controller;


import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.service.GamePropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("props")
public class PropController {

    @Autowired
    private GamePropService gamePropService;

    @GetMapping("get_all")
    @ResponseBody
    public ApiResponse getAllProps() {
        return ApiResponse.ofStatusAndData(
                ApiResponseStatus.SUCCESS,
                gamePropService.getAllProps()
        );
    }

    @PostMapping("buy_prop")
    @ResponseBody
    public ApiResponse buyProp(@RequestParam String userId,
                               @RequestParam String propId) {
        return gamePropService.buyProp(userId, propId);
    }
}

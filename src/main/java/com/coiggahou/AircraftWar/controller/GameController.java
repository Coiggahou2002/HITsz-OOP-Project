package com.coiggahou.AircraftWar.controller;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.GameRoomDTO;
import com.coiggahou.AircraftWar.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("create_new_room")
    @ResponseBody
    public ApiResponse createNewRoom(@RequestParam String userId) {
        return gameService.createNewGameRoom(userId);
    }

    @PostMapping("join_game_room")
    @ResponseBody
    public ApiResponse joinGameRoom(@RequestParam String gameRoomId, @RequestParam String userId) {
        return gameService.joinGameRoom(gameRoomId, userId);
    }

    @GetMapping("get_pending_rooms")
    @ResponseBody
    public List<GameRoomDTO> getAllPendingRooms() {
        return gameService.getAllPendingGameRooms();
    }

    @GetMapping("get_room_info_by_id")
    @ResponseBody
    public ApiResponse getRoomInfoById(@RequestParam String gameRoomId) {
        return gameService.getRoomInfo(gameRoomId);
    }

    @PostMapping("update_game_info_by_id")
    @ResponseBody
    public ApiResponse updateGameInfoById(@RequestParam String gameRoomId, @RequestParam String userId, @RequestParam int score) {
        return gameService.updateGameInfoById(gameRoomId, userId, score);
    }

}

package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.GameProp;

import java.util.List;

public interface GamePropService {
    List<GameProp> getAllProps();
    ApiResponse buyProp(String propId, String userId);
}

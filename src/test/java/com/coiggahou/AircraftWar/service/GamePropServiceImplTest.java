package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.entity.GameProp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GamePropServiceImplTest {

    @Autowired
    GamePropService gamePropService;

    @Test
    void getAllProps() {
        List<GameProp> allProps = gamePropService.getAllProps();
        for (GameProp allProp : allProps) {
            System.out.println(allProp.getName());
        }
    }
}
package com.coiggahou.AircraftWar.dto;

import com.coiggahou.AircraftWar.entity.GameRecord;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDTO {
    private String userId;
    private String username;
    private String avatarPath;
    private Integer coin;
    private List<GameRecord> records;
}

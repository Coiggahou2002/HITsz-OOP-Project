package com.coiggahou.AircraftWar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.annotation.sql.DataSourceDefinitions;
import java.util.Date;

/**
 * 游戏对局(房间)信息
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameRoomDTO {
    private String gameRoomId;
    private Integer status;
    private String createTime;
    private String playerIdA;
    private String playerNameA;
    private Integer playerScoreA;
    private String playerIdB;
    private String playerNameB;
    private Integer playerScoreB;
}

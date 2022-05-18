package com.coiggahou.AircraftWar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;
import java.util.Date;

/**
 * 游戏得分记录 实体类
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("records")
public class GameRecord {
    private String id;
    private Integer score;
    private Date createTime;
    private String userId;
}

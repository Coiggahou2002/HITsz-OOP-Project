package com.coiggahou.AircraftWar.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 游戏道具 实体类
 * @author coiggahou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("props")
public class GameProp {

    private String id;

    private String name;

    /**
     * 价值多少游戏币
     */
    private Integer worth;

    /**
     * 库存
     */
    private Integer stock;
}

package com.coiggahou.AircraftWar.service;

import com.coiggahou.AircraftWar.entity.ApiResponse;
import com.coiggahou.AircraftWar.entity.ApiResponseStatus;
import com.coiggahou.AircraftWar.entity.GameProp;
import com.coiggahou.AircraftWar.entity.User;
import com.coiggahou.AircraftWar.mapper.GamePropMapper;
import com.coiggahou.AircraftWar.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GamePropServiceImpl implements GamePropService{

    @Autowired
    private GamePropMapper gamePropMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<GameProp> getAllProps() {
        return gamePropMapper.selectByMap(new HashMap<String, Object>());
    }

    @Override
    public ApiResponse buyProp(String propId, String userId) {
        GameProp gameProp = gamePropMapper.selectById(propId);
        User user = userMapper.selectById(userId);
        if (gameProp == null || user == null) {
            return ApiResponse.ofStatus(ApiResponseStatus.REQUEST_PARAM_ERROR);
        }
        Integer userRemainCoin = user.getCoin();
        Integer propWorthCoin = gameProp.getWorth();
        Integer remainCoinAfterBuy = null;
        if (userRemainCoin >= propWorthCoin) {
            gameProp.setStock(gameProp.getStock() - 1); //库存减一
            gamePropMapper.updateById(gameProp);
            remainCoinAfterBuy = user.getCoin() - gameProp.getWorth();
            user.setCoin(remainCoinAfterBuy); //减少金币
            userMapper.updateById(user);
            return ApiResponse.ofStatus(ApiResponseStatus.SUCCESS);
        } else {
            return ApiResponse.ofStatusAndData(ApiResponseStatus.COIN_NOT_ENOUGH, remainCoinAfterBuy);
        }
    }
}

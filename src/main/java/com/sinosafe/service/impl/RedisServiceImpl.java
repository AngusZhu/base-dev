package com.sinosafe.service.impl;


import com.sinosafe.service.RedisService;
import com.sinosafe.vo.PointRule;
import org.springframework.stereotype.Service;

/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,07 2016
 * Time: 4:32 PM
 * description:
 */
@Service
public class RedisServiceImpl implements RedisService {


    @Override
    public boolean savePointsRuleToReids(PointRule rule) {
        return false;
    }

}

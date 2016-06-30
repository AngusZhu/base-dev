package com.sinosafe.service;

import com.sinosafe.vo.PointRule;

/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,07 2016
 * Time: 4:29 PM
 * description:
 */
public interface RedisService  {

    boolean savePointsRuleToReids(PointRule prule);


}

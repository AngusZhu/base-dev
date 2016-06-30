package com.sinosafe.service;

import com.sinosafe.vo.UserPointDetailsVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/23.
 */
public interface PointsConSumService {

    List<UserPointDetailsVo> findUserConsumPoint(Map<String,Object> map);
    List<UserPointDetailsVo> findUserAllPints(Map<String,Object> map);
}

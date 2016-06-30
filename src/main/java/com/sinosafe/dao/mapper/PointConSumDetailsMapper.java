package com.sinosafe.dao.mapper;

import com.sinosafe.entity.PointsConsumDetails;
import com.sinosafe.vo.UserPointDetailsVo;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/23.
 */
public interface PointConSumDetailsMapper {

    List<PointsConsumDetails> findConsumPointsByparams(Map<String,Object> map);
    List<UserPointDetailsVo> findUserAllPointsDetails(Map<String,Object> map);
}

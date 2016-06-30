package com.sinosafe.service;


import com.sinosafe.vo.UserPointDetailsVo;


import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/23.
 */
public interface PointsProductService {

    List<UserPointDetailsVo> findUserPointsProduct(Map<String,Object> map);
}

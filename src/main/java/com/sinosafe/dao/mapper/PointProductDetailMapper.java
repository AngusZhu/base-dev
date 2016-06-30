package com.sinosafe.dao.mapper;

import com.sinosafe.entity.PointProductionDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/4/22.
 */
public interface PointProductDetailMapper {

    /**
     * 查询用户收入积分详细
     */
     List<PointProductionDetail> findAllPointsByparams(Map<String,Object> map);

    /**
     * 保存积分生成明细
     * @param list
     */
    int savePointsProductDetail(List<PointProductionDetail> list);

    void saveConsumedDetailsToProdction(List<PointProductionDetail> newRecords);

    /**
     * 查询保单号，产品代码。目的：有，不生成积分；无，生成积分
     * 一条订单号可能存在多个产品代码
     * @param orderNo
     * @return
     */
    List<PointProductionDetail> findProductCodeByOrderNo(@Param("orderNo")String orderNo);
}

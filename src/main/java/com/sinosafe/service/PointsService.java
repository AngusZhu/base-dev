package com.sinosafe.service;

import com.sinosafe.excepiton.BusinessException;
import com.sinosafe.vo.PointVo;
import com.sinosafe.vo.PointsConsumptionVo;

import java.util.List;

/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,06 2016
 * Time: 7:51 PM
 * description:
 */
public interface PointsService {

    String queryPointsDetails(String json)throws BusinessException;


    Long findPointsByUserId(String userId) throws BusinessException;

    boolean consumePoints(PointsConsumptionVo consumeVO);

    /**
     * 预计可获得积分
     * 积分信息来源，源注册：REGIST，签到：LOGIN，下单：ORDER，升级APP：DOWNLOAD_APP...
     * @param pointInfo
     * @return
     */
    String getPointsInfo(String pointInfo);

    /**
     *增加用户积分
     * @param orderDetails
     * @return
     */
    String addPoints(String orderDetails);


}

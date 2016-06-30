package com.sinosafe.dao.mapper;

import com.sinosafe.entity.PointProductionDetail;
import com.sinosafe.entity.UserPoints;
import com.sinosafe.vo.PointsConsumptionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户积分
 * Created by LIUKEHAO on 2016/4/23.
 */
public interface UserPointsMapper {

    /**
     * 查询用户的积分
     * @param userId
     * @return
     */
    UserPoints findUserPoints(String userId);

    /**
     * 保存用户积分
     * table：user_points
     * @param user
     */
    void saveUserPoint(UserPoints user);

    /**
     * 删除用户积分
     * @param userId
     * @return
     */
    int deleteUserPonitById(String userId);

    /**
     * 保存用户积分入历史表（table：user_points_history）
     * @param user
     */
    void saveUserHistoryPoint(UserPoints user);

    /**
     * 保存用户消费明细
     * @param vo
     */
    void saveUserConsumePoints(PointsConsumptionVo vo);

    List<PointProductionDetail> getUserAllProductionDetail(@Param("userId") String userId);
}

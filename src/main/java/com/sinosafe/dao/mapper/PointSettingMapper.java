package com.sinosafe.dao.mapper;

import com.sinosafe.entity.PointSetting;
import com.sinosafe.vo.PointsSettingVO;

import java.util.List;
import java.util.Map;

/**
 * Created by wangjian on 2016/4/20.
 */
public interface PointSettingMapper {
    /**
     *查询最后一条积分规则的ID
     * @return
     */
    public int findLastRuleId();

    /**
     * 插入积分规则
     * @param list
     * @return
     */
    public int savePointsSetting(List<PointSetting> list);

    /**
     * 根据积分规则ID修改当前规则状态
     * @param
     * @param
     * @return
     */
    public int updatePointsSettingStatus(PointSetting pointSetting);

    /**
     * 查询是否已经存在规则
     * @param
     * @return
     */
    public int findIsPointsSetting(PointSetting pointSetting);

    /**
     * 根据规则ID查询规则
     * @param ruleId
     * @return
     */
    public List<PointSetting> findPointSettingByRuleId(int ruleId);

    /**
     * 根据 pointType productCode pointSource mechanism status
     * 查询积分类型分系统设置：fix:固定积分， ratio：比例积分
     * @param list
     * @return
     */
    public List<PointsSettingVO> findProductPointSetting(List<PointsSettingVO> list);


	 /**
     * 根据用户ID查询积分总数
     * @param uesrId
     * @return
     */
    public Long findPointByUserId(String uesrId);

    /**
     * 根据选择条件查询积分规则
     * @return
     */
    public List<PointSetting> findRuleByChooses(PointSetting pointSetting);
}
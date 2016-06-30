package com.sinosafe.service;

import com.sinosafe.common.ResponseResult;
import com.sinosafe.entity.PointSetting;

/**
 * Created by wangjian on 2016/4/20.
 */
public interface PointSettingService {
    /**
     * 插入规则
     * @param ob
     * @return
     */
    public ResponseResult savePointsSetting(Object[] ob);
    public ResponseResult updatePointsSetting(Object[] ob);
    public ResponseResult findRuleByChooses(PointSetting pointSetting);
    public ResponseResult findRuleByRuleId(int ruleId);
    public ResponseResult updatePointsSettingStatus(Object[] ob);
}

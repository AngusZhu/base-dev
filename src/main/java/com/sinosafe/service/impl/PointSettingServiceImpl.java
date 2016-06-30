package com.sinosafe.service.impl;

import com.sinosafe.common.PointsType;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.dao.mapper.PointSettingMapper;
import com.sinosafe.entity.PointSetting;
import com.sinosafe.service.LogService;
import com.sinosafe.service.PointSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by wangjian on 2016/4/20.
 */
@Service
public class PointSettingServiceImpl implements PointSettingService {
    private final static Logger logger = LoggerFactory.getLogger(PointSettingServiceImpl.class);
    @Autowired
    public LogService logServiceImpl;

    @Autowired
    public PointSettingMapper pointSettingMapper;

    @Override
    public ResponseResult savePointsSetting(Object[] ob) {
        List<PointSetting> list = new ArrayList<PointSetting>();
        try {

            String mes = ob[6].toString();
            String sources = ob[3].toString();

            String[] mechanisms = mes.split(",");
            String[] pointSource = sources.split(",");

            int lastRuleId = pointSettingMapper.findLastRuleId() + 1;

            if(PointsType.ORDER.toString().equals(ob[1].toString())) {
                String prodCodes = ob[2].toString();
                String[] productCode = prodCodes.split(",");

                int prodLength = productCode.length;
                int mechanismLength = mechanisms.length;
                int pointSourceLength = pointSource.length;
                int[] param = new int[]{prodLength, pointSourceLength, mechanismLength};
                Arrays.sort(param);
                for (int n = 0; n < param[0]; n++) {
                    for (int j = 0; j < param[1]; j++) {
                        for (int i = 0; i < param[2]; i++) {
                            PointSetting ps = new PointSetting();
                            if (prodLength <= mechanismLength && prodLength <= pointSourceLength && mechanismLength <= pointSourceLength) {
                                ps.setProductCode(productCode[n]);
                                ps.setMechanism(mechanisms[j]);
                                ps.setPointSource(pointSource[i]);
                            }
                            if (prodLength <= mechanismLength && prodLength <= pointSourceLength && pointSourceLength <= mechanismLength) {
                                ps.setProductCode(productCode[n]);
                                ps.setMechanism(mechanisms[i]);
                                ps.setPointSource(pointSource[j]);
                            }
                            if (mechanismLength <= prodLength && mechanismLength <= pointSourceLength && prodLength <= pointSourceLength) {
                                ps.setMechanism(mechanisms[n]);
                                ps.setProductCode(productCode[j]);
                                ps.setPointSource(pointSource[i]);
                            }
                            if (mechanismLength <= prodLength && mechanismLength <= pointSourceLength && pointSourceLength <= prodLength) {
                                ps.setMechanism(mechanisms[n]);
                                ps.setProductCode(productCode[i]);
                                ps.setPointSource(pointSource[j]);
                            }
                            if (pointSourceLength <= mechanismLength && pointSourceLength <= prodLength && mechanismLength <= prodLength) {
                                ps.setPointSource(pointSource[n]);
                                ps.setMechanism(mechanisms[j]);
                                ps.setProductCode(productCode[i]);
                            }
                            if (pointSourceLength <= mechanismLength && pointSourceLength <= prodLength && prodLength <= mechanismLength) {
                                ps.setPointSource(pointSource[n]);
                                ps.setMechanism(mechanisms[i]);
                                ps.setProductCode(productCode[j]);
                            }
                            ps.setRuleId(lastRuleId);
                            ps.setRuleName(ob[0].toString());
                            ps.setPointType(ob[1].toString());
                            if (ob[4].toString().length() == 0) {
                                ps.setPoints(0);
                            } else {
                                ps.setPoints(Integer.parseInt(ob[4].toString()));
                            }
                            //ps.setLimitPoints(Integer.parseInt(ob[5].toString()));
                            ps.setLimitPoints(0);
                            if (ob[7].toString().length() == 0) {
                                ps.setRatio(0.00);
                            } else {
                                ps.setRatio(Double.parseDouble(ob[7].toString()));
                            }
                            ps.setMappingType(ob[8].toString());
                            ps.setStatus(Integer.parseInt(ob[9].toString()));
                            ps.setCreatedBy(ob[10].toString());
                            ps.setUpdatedBy("Admin");
                            logger.info(ps.toString());
                            list.add(ps);

                        }
                    }
                }
            }else {

                for(int i= 0;i < pointSource.length; i++){
                    PointSetting ps = new PointSetting();
                    ps.setRuleId(lastRuleId);
                    ps.setRuleName(ob[0].toString());
                    ps.setPointType(ob[1].toString());
                    ps.setPointSource(pointSource[i]);
                    if (ob[4].toString().length() == 0) {
                        ps.setPoints(0);
                    } else {
                        ps.setPoints(Integer.parseInt(ob[4].toString()));
                    }
                    //ps.setLimitPoints(Integer.parseInt(ob[5].toString()));
                    ps.setLimitPoints(0);
                    if (ob[7].toString().length() == 0) {
                        ps.setRatio(0.00);
                    } else {
                        ps.setRatio(Double.parseDouble(ob[7].toString()));
                    }
                    ps.setMappingType(ob[8].toString());
                    ps.setStatus(Integer.parseInt(ob[9].toString()));
                    ps.setCreatedBy(ob[10].toString());
                    ps.setUpdatedBy("Admin");
                    logger.info(ps.toString());
                    list.add(ps);
                }
            }
            for (int i = 0; i < list.size(); i++) {
                int ruleIds = pointSettingMapper.findIsPointsSetting(list.get(i));
                if (ruleIds > 0) {
                    return new ResponseResult(ResponseResult.POINT_SETTING_IS_HAVE, "ID为 " + ruleIds + " 的积分规则已经存在", null);
                }
            }
            int save = pointSettingMapper.savePointsSetting(list);
            if (save < 0) {
                logger.error("插入积分规则出错");
                return new ResponseResult(ResponseResult.ADD_POINT_SETTING_ERROR, "新增积分规则出错", null);
            }
            return new ResponseResult(ResponseResult.SUCESS, "新增积分规则成功", null);
        } catch (Exception e) {
          //  logServiceImpl.saveExceptionLog("savePointsSetting",e);
            logger.error(e.getMessage());
            e.printStackTrace();
            return new ResponseResult(ResponseResult.ADD_POINT_SETTING_ERROR, "新增积分规则出错", e.getMessage());
        }
    }

    @Override
    public ResponseResult updatePointsSetting(Object[] ob) {
        try {
            int ruleId = Integer.parseInt(ob[11].toString());
            Object[] oob = new Object[11];
            System.arraycopy(ob,0,oob,0,11);
            String updatedBy = ob[10].toString();
            PointSetting pointSetting = new PointSetting();
            pointSetting.setStatus(0);
            pointSetting.setRuleId(ruleId);
            pointSetting.setUpdatedBy(updatedBy);

            int result = pointSettingMapper.updatePointsSettingStatus(pointSetting);
            if(result < 0){
                logger.error("修改积分规则出错");
                return new ResponseResult(ResponseResult.UPDATE_POINT_SETTING_ERROR, "修改积分规则出错", null);
            }else {
                return this.savePointsSetting(oob);
            }
        }catch (Exception e){
            //logServiceImpl.saveExceptionLog("updatePointsSetting",e);
            logger.error(e.getMessage());
            return new ResponseResult(ResponseResult.UPDATE_POINT_SETTING_ERROR, "修改积分规则出错", e.getMessage());
        }
    }

    @Override
    public ResponseResult findRuleByChooses(PointSetting pointSetting) {
        try {
            return new ResponseResult(ResponseResult.SUCESS,"根据条件查询积分规则成功",pointSettingMapper.findRuleByChooses(pointSetting));
        }catch (Exception e){
            //logServiceImpl.saveExceptionLog("findRuleByChooses",e);
            logger.error(e.getMessage());
            return new ResponseResult(ResponseResult.FIND_RULE_ERROR,"根据条件查询积分规则出错",e.getMessage());
        }
    }

    /**
     * 根据规则ID查询积分规则
     * @param ruleId
     * @return
     */
    @Override
    public ResponseResult findRuleByRuleId(int ruleId) {
        try {
             return new ResponseResult(ResponseResult.SUCESS,"根据积分规则ID查询积分规则成功",pointSettingMapper.findPointSettingByRuleId(ruleId));
        }catch (Exception e){
            //logServiceImpl.saveExceptionLog("findRuleByRuleId",e);
            logger.error(e.getMessage());
            return new ResponseResult(ResponseResult.FIND_RULE_BY_RULEID_ERROR,"根据积分规则ID查询积分规则出错",null);
        }
    }

    @Override
    public ResponseResult updatePointsSettingStatus(Object[] ob) {
        try {
            int status = Integer.parseInt(ob[0].toString());
            int ruleId = Integer.parseInt(ob[2].toString());
            PointSetting pointSetting = new PointSetting();
            String updatedBy = ob[1].toString();
            pointSetting.setUpdatedBy(updatedBy);
            pointSetting.setRuleId(ruleId);
            if(status == 0){//规则作废
                pointSetting.setStatus(0);
            }else if(status == 2) {//失效规则
                pointSetting.setStatus(2);
            }else if(status == 1){//启用规则
                List<PointSetting> list = pointSettingMapper.findPointSettingByRuleId(ruleId);
                for(int i=0 ; i < list.size(); i++){
                    list.get(i).setStatus(1);
                    int ruleIds = pointSettingMapper.findIsPointsSetting(list.get(i));
                    if (ruleIds > 0) {
                        return new ResponseResult(ResponseResult.POINT_SETTING_IS_HAVE, "ID为 " + ruleIds + " 的积分规则已经存在", null);
                    }
                }
                pointSetting.setStatus(1);
            }
            return new ResponseResult(ResponseResult.SUCESS,"启用积分规则成功",pointSettingMapper.updatePointsSettingStatus(pointSetting));
        }catch (Exception e){
            //logServiceImpl.saveExceptionLog("findRuleByRuleId",e);
            logger.error(e.getMessage());
            return new ResponseResult(ResponseResult.STRAT_RULE_ERROR,"启用积分规则失败",null);
        }
    }

}
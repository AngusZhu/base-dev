package com.sinosafe.controller.pointsetting;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosafe.common.PointsType;
import com.sinosafe.common.RequestUtils;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.entity.PointSetting;
import com.sinosafe.service.PointSettingService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangjian on 2016/4/25.
 */
@RestController
@RequestMapping
public class PointSettingController {
    private final static Logger logger = LoggerFactory.getLogger(PointSettingController.class);

    @Autowired
    PointSettingService pointSettingServiceImpl;



    @RequestMapping(value = "/rule", method = RequestMethod.POST)
    public String savePointsSetting(@RequestBody String param) {
        try {
            logger.info("--------开始新增积分规则--PointSettingController.savePointsSetting()--------");
            logger.info("--------PointSettingController.savePointsSetting Inparam:"+param);
            JSONObject jsonObject = JSONObject.parseObject(param);
            String ruleName = jsonObject.getString("ruleName");
            String pointType = jsonObject.getString("pointType");
            String pointSource = jsonObject.getString("pointSource");
            String mappingType = jsonObject.getString("mappingType");
            if (StringUtils.isEmpty(pointSource)) {
                logger.error("未选择系统类型");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "未选择系统类型", null).toString();
            }
            if (StringUtils.isEmpty(ruleName)) {
                logger.error("未填写规则名称");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "未填写规则名称", null).toString();
            }
            if (StringUtils.isEmpty(pointType)) {
                logger.error("积分操作类型不能为空");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "积分操作类型不能为空", null).toString();
            }
            if (StringUtils.isEmpty(mappingType)) {
                logger.error("映射类型不能为空");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "映射类型不能为空", null).toString();
            }
            String points = "";
            String ratio = "";
            String mechanism = "";
            String productCode = "";
            if(PointsType.ORDER.toString().equals(pointType)){
                mechanism = jsonObject.getString("mechanism");
                productCode = jsonObject.getString("productCode");
                if (StringUtils.isEmpty(productCode) || StringUtils.isEmpty(mechanism)) {
                    return new ResponseResult(ResponseResult.MORE_PARAM, "下单时必须有机构和产品编码", null).toString();
                }
            }

            if("FIX".equals(mappingType)){
                points = jsonObject.getString("points");
            }
            if("RATIO".equals(mappingType)){
                ratio = jsonObject.getString("ratio");
            }
            //String limitPoints = jsonObject.getString("limitPoints");
            String status = jsonObject.getString("status");
            String createdBy = jsonObject.getString("createdBy");
            Object[] ob = new Object[]{ruleName, pointType, productCode, pointSource, points, "", mechanism, ratio, mappingType, status, createdBy};
            return  pointSettingServiceImpl.savePointsSetting(ob).toString();
        }catch (Exception e){
            logger.error("-----------新增积分规则失败:" + e.toString());
            return new ResponseResult(ResponseResult.ADD_POINT_SETTING_ERROR, "新增积分规则出错", null).toString();
        }
    }

    @RequestMapping(value = "/updateRule", method = RequestMethod.POST)
    public String updatePointsSetting(@RequestBody String param){
        try {
            JSONObject jsonObject = RequestUtils.transStringToJson(param);
            int ruleId = jsonObject.getInteger("ruleId");
            String ruleName = jsonObject.getString("ruleName");
            String pointType = jsonObject.getString("pointType");
            String pointSource = jsonObject.getString("pointSource");
            String mappingType = jsonObject.getString("mappingType");
            String updatedBy = jsonObject.getString("updatedBy");
            if(StringUtils.isEmpty(updatedBy)){
                logger.error("用户未登陆");
                return new ResponseResult(ResponseResult.NO_SIGN, "用户未登陆", null).toString();
            }
            if (StringUtils.isEmpty(pointSource)) {
                logger.error("未选择系统类型");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "未选择系统类型", null).toString();
            }
            if (StringUtils.isEmpty(ruleName)) {
                logger.error("未填写规则名称");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "未填写规则名称", null).toString();
            }
            if (StringUtils.isEmpty(pointType)) {
                logger.error("积分操作类型不能为空");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "积分操作类型不能为空", null).toString();
            }
            if (StringUtils.isEmpty(mappingType)) {
                logger.error("映射类型不能为空");
                return new ResponseResult(ResponseResult.NO_POINT_SOURCE, "映射类型不能为空", null).toString();
            }
            String points = "";
            String ratio = "";
            String mechanism = "";
            String productCode = "";
            if(PointsType.ORDER.toString().equals(pointType)){
                mechanism = jsonObject.getString("mechanism");
                productCode = jsonObject.getString("productCode");
                if (StringUtils.isEmpty(productCode) || StringUtils.isEmpty(mechanism)) {
                    return new ResponseResult(ResponseResult.MORE_PARAM, "下单时必须有机构和产品编码", null).toString();
                }
            }

            if("FIX".equals(mappingType)){
                points = jsonObject.getString("points");
            }
            if("RATIO".equals(mappingType)){
                ratio = jsonObject.getString("ratio");
            }
            //String limitPoints = jsonObject.getString("limitPoints");
            String status = jsonObject.getString("status");
            String createdBy = jsonObject.getString("createdBy");
            Object[] ob = new Object[]{ruleName, pointType, productCode, pointSource, points, "", mechanism, ratio, mappingType, status, createdBy,ruleId};
            return pointSettingServiceImpl.updatePointsSetting(ob).toString();
        }catch (Exception e){
            logger.error("-----------修改积分规则失败:" + e.toString());
            return new ResponseResult(ResponseResult.UPDATE_POINT_SETTING_ERROR, "修改积分规则出错", null).toString();
        }
    }


    @RequestMapping(value = "/rule", method = RequestMethod.GET)
    public String findRuleByChooses(PointSetting  pointSetting) throws Exception {
        try {
            return pointSettingServiceImpl.findRuleByChooses(pointSetting).toString();
        }catch (Exception e){
            logger.error("-----------根据条件查询积分规则出错:" + e.toString());
            return new ResponseResult(ResponseResult.FIND_RULE_ERROR, "根据条件查询积分规则出错", null).toString();
        }
    }


    @RequestMapping(value = "/updateRuleStatus", method = RequestMethod.POST)
    public String findRuleByRuleId(@RequestBody String param) {
        try {
            JSONObject jsonObject = JSON.parseObject(param);
            int status = jsonObject.getInteger("status");
            String updatedBy = jsonObject.getString("updatedBy");
            int ruleId = jsonObject.getInteger("ruleId");
            return  pointSettingServiceImpl.updatePointsSettingStatus(new Object[]{status,updatedBy,ruleId}).toString();
        }catch (Exception e){
            logger.error("-----------启用积分规则失败:" + e.toString());
            return new ResponseResult(ResponseResult.STRAT_RULE_ERROR, "启用积分规则失败", null).toString();
        }
    }
}

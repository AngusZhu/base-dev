package com.sinosafe.controller.fuintegral;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosafe.common.*;
import com.sinosafe.service.LogService;
import com.sinosafe.service.PointsService;
import com.sinosafe.service.PrimarykeyService;
import com.sinosafe.vo.PointsConsumptionVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,09 2016
 * Time: 8:23 AM
 * description:
 */
@RestController
@RequestMapping
public class PointsController {

    private final static Logger logger = LoggerFactory.getLogger(PointsController.class);

    @Autowired
    private PointsService pointsServiceImpl;

    @Autowired
    private LogService logServiceImpl;

    @Autowired
    private PrimarykeyService primarykeyServiceImpl;

    @RequestMapping(value = "/points", method = RequestMethod.POST)
    public String queryPoints(@RequestBody String params) throws Exception {
        JSONObject pointObject = null;
        String loginId = "";
        String firmNo = "";
        try {
            logger.info("----------enter PointsController.queryPoints-----");
            logger.info("----------Inparam:"+params);

            //解析loginId

            pointObject = RequestUtils.transStringToJson(params);
            String unProcessLoginId = pointObject.getString("loginId");
            loginId = AesUtil.defaultDecrypt(unProcessLoginId);
            logger.error(" query points loginId =" + loginId);

            //入参入库
            Long primaryKey = primarykeyServiceImpl.getSeqence();
            logServiceImpl.saveRequestLog(primaryKey, SystemCode.FUIOU, "PointsController.queryPoints", loginId, params);


            //firmNo 校验
            firmNo = pointObject.getString("firmNo");
            if (!SystemCode.SINOSAFE.toString().equals(firmNo)) {
                logger.info("firmNo is " + firmNo + ",and is not sinosafe,failed!");
                //不是sinosafe,直接返回
                pointObject.put("loginId", AesUtil.defaultEncrypt(loginId));
                pointObject.put("token", MD5.getToken("1002", MD5.MD5_KEY, loginId, firmNo));
                pointObject.put("result", AesUtil.defaultEncrypt("1002"));
                pointObject.put("errorMsg", AesUtil.defaultEncrypt("firmNo not dismatch"));
                pointObject.put("points", "");
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.queryPoints", loginId, pointObject.toJSONString());
                return pointObject.toJSONString();
            }
            //token 校验
            String originToken = pointObject.getString("token");
            //md5(loginId|md5key|firmNo)
            String caculatedToken = MD5.getToken(loginId, MD5.MD5_KEY, firmNo);

            if (!StringUtils.equals(caculatedToken, originToken)) {
                logger.info("token valid failed!");
                //签名校验失败
                pointObject.put("loginId", AesUtil.defaultEncrypt(loginId));
                pointObject.put("token", MD5.getToken("1006", MD5.MD5_KEY, loginId, firmNo));
                pointObject.put("result",  AesUtil.defaultEncrypt("1006"));
                pointObject.put("errorMsg", AesUtil.defaultEncrypt("token valid failed."));
                pointObject.put("points", "");
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.queryPoints", loginId, pointObject.toJSONString());
                return pointObject.toJSONString();
            }


            Long totlePoints = pointsServiceImpl.findPointsByUserId(loginId);

            pointObject.put("loginId", AesUtil.defaultEncrypt(loginId));
            pointObject.put("firmNo", firmNo);
            pointObject.put("result", AesUtil.defaultEncrypt(ResponseResult.FUIOU_SUCESS));
            pointObject.put("points", totlePoints != null ? AesUtil.defaultEncrypt(String.valueOf(totlePoints)) : "");
            pointObject.put("token", MD5.getToken(ResponseResult.FUIOU_SUCESS, MD5.MD5_KEY, loginId, firmNo));


        } catch (Exception e) {
            logger.info("----------outParam:"+pointObject.toJSONString());
            logger.error("PointsController.queryPoints failed:" + e.getCause());
            e.printStackTrace();
            logServiceImpl.saveExceptionLog("PointsController.queryPoints", e);
            e.printStackTrace();
            pointObject.put("loginId", AesUtil.defaultEncrypt(loginId));
            pointObject.put("token", MD5.getToken("1099", MD5.MD5_KEY, loginId, firmNo));
            pointObject.put("result", AesUtil.defaultEncrypt("1099"));
            pointObject.put("errorMsg", AesUtil.defaultEncrypt("exception happened."));
            pointObject.put("points", "");
            return pointObject.toJSONString();
        }
        logger.info("----------outParam:"+pointObject.toJSONString());
        logger.info("----------left PointsController.queryPoints-----");
        return pointObject.toJSONString();
    }

    /**
     * 根据富友的扣除积分接口，返回是否能成功
     *
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/consume", method = RequestMethod.POST)
    public String consumePoints(@RequestBody String params) {
        logger.info("----------enter PointsController.consumePoints-----");
        logger.info("----------Inparam:"+params);
        Long primaryKey = primarykeyServiceImpl.getSeqence();
        PointsConsumptionVo cmpVO=new PointsConsumptionVo();
        JSONObject pointObject=null;
        String loginId = "";
        String firmNo = "";
        String orderNo = "";
        String points = "";
        String orderAmt = "";
        String userId = "";
        String productNm = "";
        //String password="";
        try {

            pointObject = RequestUtils.transStringToJson(params);

            String unProcessLoginId = pointObject.getString("loginId");
            loginId = AesUtil.defaultDecrypt(unProcessLoginId);

            String unProcessUserId = pointObject.getString("userId");
            userId = AesUtil.defaultDecrypt(unProcessUserId);

            String unProcessOrderNo = pointObject.getString("orderNo");
            orderNo = AesUtil.defaultDecrypt(unProcessOrderNo);

            String unProcessPoints = pointObject.getString("points");
            points = AesUtil.defaultDecrypt(unProcessPoints);

            String unProcessOrderAmt = pointObject.getString("orderAmt");
            orderAmt = AesUtil.defaultDecrypt(unProcessOrderAmt);

            String unProcessProductNm = pointObject.getString("productNm");
            productNm = AesUtil.defaultDecrypt(unProcessProductNm);

            firmNo = pointObject.getString("firmNo");


            logServiceImpl.saveRequestLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", orderNo, params);



            //校验firmNo
            if (!SystemCode.SINOSAFE.toString().equals(firmNo)) {
                logger.info("firmNo is " + firmNo + ",and is not sinosafe,failed!");
                //不是sinosafe,直接返回 md5(result|md5key|orderNoDest|loginId|orderNo|firmNo)
                JSONObject resObject=new JSONObject();
                resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
                resObject.put("firmNo",firmNo);
                resObject.put("points",AesUtil.defaultEncrypt(points));
                resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
                resObject.put("result", AesUtil.defaultEncrypt("1002"));
                resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
                resObject.put("orderNoDest", "");
                resObject.put("errorMsg", AesUtil.defaultEncrypt("firmNo mismatch"));
                resObject.put("token", MD5.getToken("1002", MD5.MD5_KEY, "", loginId, orderNo, firmNo));
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", orderNo, resObject.toJSONString());
                return resObject.toJSONString();
            }
            //参数校验
            if (StringUtils.isBlank(loginId)||isNullString(loginId)
                    ||StringUtils.isBlank(orderNo)||isNullString(orderNo)
                    ||StringUtils.isBlank(points)||isNullString(points)) {
                logger.info("parameter missing ,failed!");
                //不是sinosafe,直接返回 md5(result|md5key|orderNoDest|loginId|orderNo|firmNo)
                JSONObject resObject=new JSONObject();
                resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
                resObject.put("firmNo",firmNo);
                resObject.put("points",AesUtil.defaultEncrypt(points));
                resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
                resObject.put("result", AesUtil.defaultEncrypt("1003"));
                resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
                resObject.put("orderNoDest", "");
                resObject.put("errorMsg", AesUtil.defaultEncrypt("parameter null happens"));
                resObject.put("token", MD5.getToken("1003", MD5.MD5_KEY, "", loginId, orderNo, firmNo));
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", orderNo, resObject.toJSONString());
                return resObject.toJSONString();
            }

            //token 校验
            String originToken = pointObject.getString("token");
            //md5(orderAmt|md5key|points|loginId|userId|firmNo|orderNo)
            String caculatedToken = MD5.getToken(orderAmt, MD5.MD5_KEY, points, loginId, userId, firmNo, orderNo);

            if (!StringUtils.equals(caculatedToken, originToken)) {
                logger.info("token valid failed!");
                JSONObject resObject=new JSONObject();
                //签名校验失败 md5(result|md5key|orderNoDest|loginId|orderNo|firmNo)
                resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
                resObject.put("firmNo",firmNo);
                resObject.put("points",AesUtil.defaultEncrypt(points));
                resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
                resObject.put("result", AesUtil.defaultEncrypt("1006"));
                resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
                resObject.put("orderNoDest", "");
                resObject.put("errorMsg", AesUtil.defaultEncrypt("token valid failed."));
                resObject.put("token", MD5.getToken("1006", MD5.MD5_KEY, "", loginId, orderNo, firmNo));
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", loginId, resObject.toJSONString());
                return pointObject.toJSONString();
            }
           //判断是否数字
            //points	积分	是		需要扣除的积分
            //orderAmt	金额	是		进行消费时的金额（单位：分）

            cmpVO.setLoginId(loginId);
            cmpVO.setFirmNo(firmNo);
            cmpVO.setOrderAmt(orderAmt);
            cmpVO.setUserId(userId);
            cmpVO.setOrderNo(orderNo);
            cmpVO.setPoints(points);
            cmpVO.setProductNm(productNm);
            //扣除积分
            if (!pointsServiceImpl.consumePoints(cmpVO)) {
                logger.info("consumption  failed!");
                JSONObject resObject=new JSONObject();
                resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
                resObject.put("firmNo",firmNo);
                resObject.put("points",AesUtil.defaultEncrypt(points));
                resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
                resObject.put("result", AesUtil.defaultEncrypt(cmpVO.getErrorCode()));
                resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
                resObject.put("orderNoDest", "");
                resObject.put("errorMsg",AesUtil.defaultEncrypt(cmpVO.getErrorMsg()));
                resObject.put("token", MD5.getToken(cmpVO.getErrorCode(), MD5.MD5_KEY, "", loginId, orderNo, firmNo));
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", loginId, resObject.toJSONString());
                return resObject.toJSONString();
            }
            JSONObject resObject=new JSONObject();
            resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
            resObject.put("firmNo",firmNo);
            resObject.put("points",AesUtil.defaultEncrypt(points));
            resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
            resObject.put("result", AesUtil.defaultEncrypt(ResponseResult.FUIOU_SUCESS));
            resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
            resObject.put("orderNoDest", AesUtil.defaultEncrypt(String.valueOf(cmpVO.getOrderNoDest())));
            resObject.put("token", MD5.getToken(ResponseResult.FUIOU_SUCESS, MD5.MD5_KEY, String.valueOf(cmpVO.getOrderNoDest()), loginId, orderNo, firmNo));
            String response = JSON.toJSON(cmpVO).toString();
            logger.info("PointsController.consumePoints outParam:"+response);
            logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", orderNo, resObject.toJSONString());
            logger.info("----------OutParam:"+pointObject);
            logger.info("----------left PointsController.consumePoints-----");
            return resObject!=null?resObject.toJSONString():"";
        } catch (Exception e) {
            logger.error(e.getMessage(), e.getCause());
            e.printStackTrace();
            JSONObject resObject=new JSONObject();
            try {
                resObject.put("loginId",AesUtil.defaultEncrypt(loginId));
                resObject.put("firmNo",firmNo);
                resObject.put("points",AesUtil.defaultEncrypt(points));
                resObject.put("orderAmt",AesUtil.defaultEncrypt(orderAmt));
                resObject.put("result", AesUtil.defaultEncrypt(cmpVO.getErrorCode()));
                resObject.put("orderNo", AesUtil.defaultEncrypt(orderNo));
                resObject.put("orderNoDest", "");
                resObject.put("errorMsg",AesUtil.defaultEncrypt(cmpVO.getErrorMsg()));
                resObject.put("token", MD5.getToken(cmpVO.getErrorCode(), MD5.MD5_KEY, "", loginId, orderNo, firmNo));
                logServiceImpl.updateResponseLog(primaryKey, SystemCode.FUIOU, "PointsController.consumePoints", loginId, resObject.toJSONString());
                logger.info("----------OutParam:"+resObject);
                logger.info("----------left PointsController.consumePoints-----");
                return resObject.toJSONString();
            } catch (Exception e1) {
                logger.error("消费异常"+e1);
                e1.printStackTrace();
            } finally {
                logServiceImpl.saveExceptionLog("PointsController.consumePoints", e);
            }
        }
        logger.info("----------OutParam:"+pointObject);
        logger.info("----------left PointsController.consumePoints-----");
        return "";
    }

    private static boolean  isNullString(String str){
        return "null".equalsIgnoreCase(str);
    }

}

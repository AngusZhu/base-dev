package com.sinosafe.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sinosafe.common.ResponseResult;
import com.sinosafe.common.ReturnCode;
import com.sinosafe.service.impl.PointsServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by LIUKEHAO on 2016/4/26.
 */
public class CommonParaService {
    private final static Logger logger = LoggerFactory.getLogger(PointsServiceImpl.class);
        private boolean paraMissFlag;
        private String orderDetails;
        private Map<String, Object> resultMsg;
        private JSONObject pointObject;
        private String comCode;
        private String pointType;
        private String pointSource;
        private String orderNo;
        private String userId;

        public CommonParaService(String orderDetails, Map<String, Object> resultMsg) {
            this.orderDetails = orderDetails;
            this.resultMsg = resultMsg;
        }

       public boolean isParaMiss() {
            return paraMissFlag;
        }

        public JSONObject getPointObject() {
            return pointObject;
        }

        public String getComCode() {
            return comCode;
        }

        public String getPointType() {
            return pointType;
        }

        public String getPointSource() {
            return pointSource;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public String getUserId() {
            return userId;
        }

    public Map<String, Object> getResultMsg() {
        return resultMsg;
    }

    public CommonParaService parsePara() {
//            logger.info("调用积分生成CommonParaService.invoke时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+" || 参数："+orderDetails );
            pointObject = JSON.parseObject(orderDetails);
            comCode = pointObject.getString("mechanism");
            pointType = pointObject.getString("pointType");
            pointSource = pointObject.getString("source");
            orderNo = pointObject.getString("orderNo");
            userId = pointObject.getString("userId");
            if(StringUtils.isBlank(pointType) || StringUtils.isBlank(pointSource) || StringUtils.isBlank(userId) ){
                resultMsg.put(ReturnCode.RETURN_CODE.toString(), ResponseResult.PARA_MISS);
                resultMsg.put(ReturnCode.ERROR_DESC.toString(), "参数缺失");
                paraMissFlag = true;
                return this;
            }
//            resultMsg.put(ReturnCode.RETURN_CODE.toString() ResponseResult.PRODUCT_POINT_ERROR);
//            resultMsg.put("ERROR_DESC", "积分生成成功");
            paraMissFlag = false;
            return this;
        }
}

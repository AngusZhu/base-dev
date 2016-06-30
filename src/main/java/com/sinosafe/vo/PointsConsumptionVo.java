package com.sinosafe.vo;

import java.io.Serializable;

/**
 * Created by zhuhuanmin on 2016/4/21.
 */
public class PointsConsumptionVo extends CommonVO implements Serializable {

    private Long id;  //用户ID
    private String loginId;  //用户ID
    private String firmNo;  //公司代码
    private String cardNo;  //卡号,无用
    private String points; //消费的积分
    private String leftPoints; //消费的积分
    private String orderAmt; //消费的金额
    private String userId;  //用户ID
    private String orderNo; //订单号
    private String productNm; //产品
    private Integer productCount; //产品
    private String password; //消费密码
    private String token;   // md5(md5key|loginId|userNm|firmNo)
    private String orderNoDest; //积分系统消费生产的主键
    private String errorMsg;  //异常信息
    public String  errorCode; //错误码
    public String  thirdPartyId; //错误码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getFirmNo() {
        return firmNo;
    }

    public void setFirmNo(String firmNo) {
        this.firmNo = firmNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductNm() {
        return productNm;
    }

    public void setProductNm(String productNm) {
        this.productNm = productNm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOrderNoDest() {
        return orderNoDest;
    }

    public void setOrderNoDest(String orderNoDest) {
        this.orderNoDest = orderNoDest;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public void setLeftPoints(String leftPoints) {
        this.leftPoints = leftPoints;
    }

    public String getLeftPoints() {
        return leftPoints;
    }


    @Override
    public String toString() {
        return "PointsConsumptionVo{" +
                "id=" + id +
                ", loginId='" + loginId + '\'' +
                ", firmNo='" + firmNo + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", points='" + points + '\'' +
                ", leftPoints='" + leftPoints + '\'' +
                ", orderAmt='" + orderAmt + '\'' +
                ", userId='" + userId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", productNm='" + productNm + '\'' +
                ", productCount=" + productCount +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", orderNoDest='" + orderNoDest + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", thirdPartyId='" + thirdPartyId + '\'' +
                '}';
    }
}

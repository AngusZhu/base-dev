package com.sinosafe.vo;

import java.io.Serializable;

/**
 * Created by wangjian on 2016/4/20.
 */
public class PointsSettingVO  implements Serializable {
    private int ruleId;
    private String ruleName;
    private String pointType;
    private String pointSource;
    private int points;
    private int limitPoints;
    private String mechanism;//机构
    private Double ratio;//兑换比例
    private String mappingType;//兑换类型
    private int status;
    private String productCode;//产品

    private double fee; //金额
    public int getRuleId() {
        return ruleId;
    }
    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }
    public String getRuleName() {
        return ruleName;
    }
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }
    public String getPointType() {
        return pointType;
    }
    public void setPointType(String pointType) {
        this.pointType = pointType;
    }
    public String getPointSource() {
        return pointSource;
    }
    public void setPointSource(String pointSource) {
        this.pointSource = pointSource;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getLimitPoints() {
        return limitPoints;
    }
    public void setLimitPoints(int limitPoints) {
        this.limitPoints = limitPoints;
    }
    public String getMechanism() {
        return mechanism;
    }
    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }
    public Double getRatio() {
        return ratio;
    }
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
    public String getMappingType() {
        return mappingType;
    }
    public void setMappingType(String mappingType) {
        this.mappingType = mappingType;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "PointsSettingVO{" +
                "ruleId=" + ruleId +
                ", ruleName='" + ruleName + '\'' +
                ", pointType='" + pointType + '\'' +
                ", pointSource='" + pointSource + '\'' +
                ", points=" + points +
                ", limitPoints=" + limitPoints +
                ", mechanism='" + mechanism + '\'' +
                ", ratio=" + ratio +
                ", mappingType='" + mappingType + '\'' +
                ", status=" + status +
                ", productCode='" + productCode + '\'' +
                ", fee=" + fee +
                '}';
    }
}

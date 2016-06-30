package com.sinosafe.entity;

/**
 * Created with base.
 * User: anguszhu
 * Date: Apr,07 2016
 * Time: 4:33 PM
 * description:积分规则
 */
public class PointSetting {
    private int id;
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
    private String createdBy;
    private String updatedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public String getMechanism() {
        return mechanism;
    }

    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }

    public int getLimitPoints() {
        return limitPoints;
    }

    public void setLimitPoints(int limitPoints) {
        this.limitPoints = limitPoints;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getPointSource() {
        return pointSource;
    }

    public void setPointSource(String pointSource) {
        this.pointSource = pointSource;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String toString() {
        return "PointSetting{" +
                "id=" + id +
                ", ruleId=" + ruleId +
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
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}

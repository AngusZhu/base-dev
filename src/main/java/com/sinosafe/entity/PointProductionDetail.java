package com.sinosafe.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by wudaibo
 * Date:2016/4/22.
 * 用户积分生成表
 */
public class PointProductionDetail {
    Long id;
    /**
     * 第三方用户Id
     * */
    String userId;
    /**
     * 积分类型：LOGIN 登录/签到 ORDER:下单 REGIST：注册 升级APP:DOWNLOAD_APP
     */

    String pointType;
    /**
     * 积分来源 B2C WEIXIN YDZ
     */
    String pointSource;
    /**
     * 积分
     */
    Integer points;


    /**
     * 机构
     */
    String subMechanism;
    /**
     * 机构
     */
    String mechanism;
    /**
     *产品代码
     */
    String productCode;
    /**
     * 投保单号
     */
    String proposalNo;
    /**
     * 订单号
     */
    String orderNo;
    /**
     * 保费
     */
    Double fee;
    /**
     * 积分状态
     * （1 有效，0  未生效，2  已过期 ，3 已冻结，4 已消费）
     */
    Integer status;
    /**
     * 积分冻结时间
     * 下单成功的时间开始 + 下一年的最后一天
     */
    Date deadLine;
    /**
     * 创建时间
     */
    Timestamp createdDate;
    /**
     * 创建者
     */
    String createdBy;
    /**
     * 更新时间
     */
    Timestamp updateDate;
    /**
     * 操作者
     */
    String updateBy;


    Long cmpMasterId;

    public Long LongId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Integer getPoints() {
        return points;
    }

    public Integer setPoints(Integer points) {
        this.points = points;
        return  points;
    }

    public String getMechanism() {
        return mechanism;
    }

    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProposalNo() {
        return proposalNo;
    }

    public void setProposalNo(String proposalNo) {
        this.proposalNo = proposalNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getId() {
        return id;
    }

    public Long getCmpMasterId() {
        return cmpMasterId;
    }

    public void setCmpMasterId(Long cmpMasterId) {
        this.cmpMasterId = cmpMasterId;
    }

    public String getSubMechanism() {
        return subMechanism;
    }

    public void setSubMechanism(String subMechanism) {
        this.subMechanism = subMechanism;
    }

    @Override
    public String toString() {
        return "PointProductionDetail{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", pointType='" + pointType + '\'' +
                ", pointSource='" + pointSource + '\'' +
                ", points=" + points +
                ", subMechanism='" + subMechanism + '\'' +
                ", mechanism='" + mechanism + '\'' +
                ", productCode='" + productCode + '\'' +
                ", proposalNo='" + proposalNo + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", fee=" + fee +
                ", status=" + status +
                ", deadLine=" + deadLine +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", updateDate=" + updateDate +
                ", updateBy='" + updateBy + '\'' +
                ", cmpMasterId=" + cmpMasterId +
                '}';
    }
}

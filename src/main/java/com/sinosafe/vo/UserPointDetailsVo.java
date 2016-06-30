package com.sinosafe.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lenovo on 2016/4/23.
 */
public class UserPointDetailsVo {
    /**
     * 总积分
     */
    Integer totalPoints;

    /**
     * 积分生成时间
     */
    Date processTime;
    /**
     * 积分类型
     */
    String pointType;


    /**
     * 积分值
     */

    Integer point;
    /**
     * 渠道
     */
    String source;
    /**
     * 状态
     */
    Integer status;

    /**
     * 描述
     * @return
     */
    String remark;

    /**
     * 状态翻译
     */
    String statusName;
    /**
     *  官网订单号，微信支付号
     */
    String orderNo;

    public static SimpleDateFormat formatter =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getProcessTime() {
        return formatter.format(processTime);
    }

    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}

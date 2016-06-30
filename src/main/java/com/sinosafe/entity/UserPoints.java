package com.sinosafe.entity;

import com.sinosafe.vo.CommonVO;

import java.io.Serializable;


/**
 * Created by wudaibo on 2016/4/20.
 * 用户积分信息表（table:user_points）
 * 用户历史积分信息表(table:user_points_history)
 * 共同使用
 */
public class UserPoints extends CommonVO implements Serializable{
    /**
     * 主键id
     * */

     private Long id;
    /**
     * 网销用户id
     */
    private String userId;
    /**
     * 用户总积分
     * */
    private Double totalPoints;
    /**
     * 用户累计总积分
      */
    private Double sumPoints;

    /**
     * 积分状态（1 有效，0 冻结，2 失效）
     * 用户积分信息表（table:user_points），默认传1
     * 用户历史积分信息表(table:user_points_history) 默认传0
     */
    private Integer status;

    private Long masterId;

    public Long getId() {
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

    public Double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getSumPoints() {
        return sumPoints;
    }

    public void setSumPoints(Double sumPoints) {
        this.sumPoints = sumPoints;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    @Override
    public String toString() {
        return "UserPoints{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", totalPoints=" + totalPoints +
                ", sumPoints=" + sumPoints +
                ", status=" + status +
                ", masterId=" + masterId +
                '}';
    }
}

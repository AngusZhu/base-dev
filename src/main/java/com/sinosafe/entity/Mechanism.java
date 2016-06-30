package com.sinosafe.entity;

/**
 * Created with base.
 * User: wangjian
 * Date: Apr,19 2016
 * Time: 4:33 PM
 * description:机构
 */
public class Mechanism {
    private int id;
    private String mechanism;
    private String mechanismName;
    private String parentMechanism;
    private int status;
    private String createdBy;
    private String updatedBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMechanism() {
        return mechanism;
    }

    public void setMechanism(String mechanism) {
        this.mechanism = mechanism;
    }

    public String getParentMechanism() {
        return parentMechanism;
    }

    public void setParentMechanism(String parentMechanism) {
        this.parentMechanism = parentMechanism;
    }

    public String getMechanismName() {
        return mechanismName;
    }

    public void setMechanismName(String mechanismName) {
        this.mechanismName = mechanismName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        return "Mechanism{" +
                "id=" + id +
                ", mechanism='" + mechanism + '\'' +
                ", mechanismName='" + mechanismName + '\'' +
                ", parentMechanism='" + parentMechanism + '\'' +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                '}';
    }
}

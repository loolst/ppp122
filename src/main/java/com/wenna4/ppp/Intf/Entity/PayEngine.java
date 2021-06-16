package com.wenna4.ppp.Intf.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayEngine {
    private int id;
    private int orderId;
    private int mainEngineId;
    private int userId;
    private int engineType;
    private BigDecimal amount;
    private int status;
    private String desc;
    private Date addTime;
    private Date updateTime;
    private String userIp;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getMainEngineId() {
        return this.mainEngineId;
    }

    public void setMainEngineId(int mainEngineId) {
        this.mainEngineId = mainEngineId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEngineType() {
        return this.engineType;
    }

    public void setEngineType(int engineType) {
        this.engineType = engineType;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getAddTime() {
        return this.addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    @Override
    public String toString() {
        return "PayEngine [id=" + id + ", orderId=" + orderId
                + ", mainEngineId=" + mainEngineId + ", userId=" + userId
                + ", engineType=" + engineType + ", amount=" + amount
                + ", status=" + status + ", desc=" + desc + ", addTime="
                + addTime + ", updateTime=" + updateTime + ", userIp=" + userIp
                + "]";
    }
}

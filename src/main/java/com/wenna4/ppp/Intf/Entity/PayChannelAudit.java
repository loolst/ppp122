package com.wenna4.ppp.Intf.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class PayChannelAudit {
    private int id;
    private int userId;
    private int orderId;
    private int engineId;
    private int payChannel;//渠道id
    private int payMethod;
    private BigDecimal payAmount;
    private int status;
    private String tradeNo;
    private String outTradeNo;
    private int source;
    private String msg;
    private Date addTime;
    private Date updateTime;
    private String userIp;
    private String partnerId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getUpdatetimeTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    @Override
    public String toString() {
        return "PayChannelAudit [id=" + id + ", userId=" + userId
                + ", orderId=" + orderId + ", engineId=" + engineId
                + ", payChannel=" + payChannel + ", payMethod=" + payMethod
                + ", payAmount=" + payAmount + ", status=" + status
                + ", tradeNo=" + tradeNo + ", outTradeNo=" + outTradeNo
                + ", source=" + source + ", msg=" + msg + ", addTime="
                + addTime + ", updateTime=" + updateTime + ", userIp=" + userIp
                + ", partnerId=" + partnerId + "]";
    }

}

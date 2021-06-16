package com.wenna4.ppp.Intf.DO;

import java.math.BigDecimal;

public class PayUnipayDO {
    private int userId;
    private int orderId;
    private int payChannel;
    private int payMethod;
    private int payPlatform;
    private BigDecimal amount;
    private String couponNum;

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

    public int getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(int payMethod) {
        this.payMethod = payMethod;
    }

    public int getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(int payPlatform) {
        this.payPlatform = payPlatform;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(String couponNum) {
        this.couponNum = couponNum;
    }

    @Override
    public String toString() {
        return "PayUnipayDO{" +
                "userId=" + userId +
                ", orderId=" + orderId +
                ", payChannel=" + payChannel +
                ", payMethod=" + payMethod +
                ", payPlatform=" + payPlatform +
                ", amount=" + amount +
                ", couponNum='" + couponNum + '\'' +
                '}';
    }
}

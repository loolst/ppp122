package com.wenna4.ppp.Intf.DO;

import java.math.BigDecimal;

public class PayDO {
    private int orderId;
    private int orderStatus;
    private BigDecimal amount;
    private String msg;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "PayDO [orderId=" + orderId + ", orderStatus=" + orderStatus
                + ", amount=" + amount + ", msg=" + msg + "]";
    }

}

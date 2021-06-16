package com.wenna4.ppp.Intf.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@ToString
public class PayUnipay {
    private int id;
    private int userId;
    private int orderId;
    private BigDecimal amount;
    private int couponId;
    private int payChannel;
    private int payMethod;
    private int status;
    private Date addTime;
    private Date updateTime;

}

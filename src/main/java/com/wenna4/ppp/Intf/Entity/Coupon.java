package com.wenna4.ppp.Intf.Entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@ToString
public class Coupon {
    private int id;
    private String name;
    private BigDecimal userAmountLimit;//抵用门槛
    private BigDecimal amount; //券金额
    private int status;
    private int bizId;
    private int validDay;
    private Date addTime;
    private Date updateTime;

}

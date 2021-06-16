package com.wenna4.ppp.Intf.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class PayOrder {
    private int id;
    private int userId;
    private int bizOrderId;
    private int bizId;
    private BigDecimal amount;
    private int status;
    private String msg;
    private int platformId;
    private String callbackUrl;
    private String returnUrl;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String userIp;
}

package com.wenna4.ppp.Intf.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class CouponUser {
    private int id;
    private String couponNum;
    private int couponId;
    private int userId;
    private int status;
    private Date validTime;
    private Date addTime;
    private Date updateTime;
}

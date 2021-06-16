package com.wenna4.ppp.Intf.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@ToString
public class VipOrder {
    private int id;
    private int userId;
    private int status;
    private BigDecimal amount;
    private Date addTime;
    private Date updateTime;

}

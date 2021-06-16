package com.wenna4.ppp.Intf.DO;

import com.wenna4.ppp.Intf.Entity.CouponUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CouponUserDO extends CouponUser {

    //  private CouponUser couponUser;
    private String name;
    private BigDecimal userAmountLimit;//抵用门槛
    private BigDecimal amount; //券金额
    private int bizId;


}

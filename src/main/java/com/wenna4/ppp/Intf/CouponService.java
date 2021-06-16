package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.Entity.Coupon;

public interface CouponService {
    Coupon loadById(int id);

    int update(Coupon coupon);
}

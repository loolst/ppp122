package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.CouponService;
import com.wenna4.ppp.Intf.Entity.Coupon;
import com.wenna4.ppp.Intf.Mapper.CouponMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;

@Service
public class CouponServiceImpl implements CouponService {

    @Resource
    private CouponMapper couponMapper;


    @Override
    public Coupon loadById(int id) {
        return couponMapper.loadById(id);
    }

    @Override
    public int update(Coupon coupon) {
        return couponMapper.update(coupon);
    }
}

package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.CouponUserService;
import com.wenna4.ppp.Intf.DO.CouponUserDO;
import com.wenna4.ppp.Intf.Entity.CouponUser;
import com.wenna4.ppp.Intf.Mapper.CouponUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class CouponUserServiceImpl implements CouponUserService {

    @Resource
    private CouponUserMapper couponUserMapper;


    @Override
    public CouponUser loadById(int id) {
        return null;
    }

    @Override
    public int update(CouponUser couponUser) {
        return 0;
    }

    @Override
    public List<CouponUserDO> loadListByUserIdAndBizId(int userId, int bizId, BigDecimal amountLimit, int pageId, int size) {
        // 1 if bizId >0 ,那么先mapper.loadListByUserIdAndBizId;
        //bizid != 0 ; 所有券一次查出
        //1.1 如果查出来 的数据<10， 那么还得再查size-len的 bizID = 0的数据。
        // 2 if bizId = 0,直接分页查询

        List<CouponUserDO> couponUserDOList = null;
        if (bizId != 0) {
            if (pageId == 1) {
                couponUserDOList = couponUserMapper.loadListByUserIdAndBizId(userId, bizId, amountLimit, 0, size);
            }
            if (couponUserDOList == null || couponUserDOList.size() < size) {
                List<CouponUserDO> couponUserDOList1 = couponUserMapper.loadListByUserIdAndBizId(userId, 0, amountLimit, (pageId - 1) * size, size);
                couponUserDOList.addAll(couponUserDOList1);
            }
        } else {
            couponUserDOList = couponUserMapper.loadListByUserIdAndBizId(userId, bizId, amountLimit, (pageId - 1) * size, size);
        }


        return couponUserDOList;
    }

    @Override
    public CouponUser loadByCouponNum(String couponNum) {
        return couponUserMapper.loadByCouponNum(couponNum);
    }
}

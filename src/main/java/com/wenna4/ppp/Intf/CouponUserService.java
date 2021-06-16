package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.DO.CouponUserDO;
import com.wenna4.ppp.Intf.Entity.CouponUser;

import java.math.BigDecimal;
import java.util.List;

public interface CouponUserService {
    CouponUser loadById(int id);

    int update(CouponUser couponUser);

    List<CouponUserDO> loadListByUserIdAndBizId(int userId, int bizId, BigDecimal amountLimit, int pageId, int size);

    CouponUser loadByCouponNum(String couponNum);
}

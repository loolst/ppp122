package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.DO.CouponUserDO;
import com.wenna4.ppp.Intf.Entity.Coupon;
import com.wenna4.ppp.Intf.Entity.CouponUser;
import com.wenna4.ppp.Intf.Entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CouponUserMapper {
    @Select("select * from yike_coupon_user where id = #{id}")
    User loadById(int id);

    @Update({"update yike_coupon_user set Status = #{status}, UpdateTime=now() where id = #{id}"})
    public int update(CouponUser couponUser);

    @Select("select * from yike_coupon_user where couponNum = #{couponNum} and status != 0 ")
    CouponUser loadByCouponNum(String couponNum);

    @Select("SELECT  a.couponNum, a.validTime, b.`Name`, b.`UseAmountLimit`, b.`Amount`, b.`BizId` FROM `yike_coupon_user` a  JOIN `yike_coupon` b ON a.`CouponId` = b.`id` WHERE a.`UserId` = #{userId} and b.BizId = #{bizId} and b.status != 0 and a.status != 0 ORDER BY b.`Amount` DESC, a.`ValidTime` ASC limit #{offset} , #{size}")
    List<CouponUserDO> loadListByUserIdAndBizId(int userId, int bizId, BigDecimal amountLimit, int offset, int size);
}

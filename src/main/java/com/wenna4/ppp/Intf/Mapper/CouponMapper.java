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
public interface CouponMapper {

    @Select("select * from yike_coupon where id = #{id}")
    Coupon loadById(int id);

    @Update({"update yike_coupon set Status = #{status}, UpdateTime=now() where id = #{id}"})
    public int update(Coupon coupon);

}

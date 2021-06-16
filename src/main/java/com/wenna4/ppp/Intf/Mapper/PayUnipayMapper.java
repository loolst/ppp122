package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.PayUnipay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayUnipayMapper {

    @Select("select * from yike_pay_unipay where id=#{id}")
    public PayUnipay loadById(int id);

    @Insert("insert into yike_pay_unipay(UserId,OrderId,Amount,CouponId,PayChannel, PayMethod,Status,AddTime,UpdateTime) values(#{userId},#{orderId},#{amount},#{couponId},#{payChannel},#{payMethod},1,now(),now())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(PayUnipay payUnipay);


}

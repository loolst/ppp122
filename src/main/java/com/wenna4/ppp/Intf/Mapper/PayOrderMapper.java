package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.PayOrder;
import com.wenna4.ppp.Intf.Enum.BizEnum;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PayOrderMapper {

    @Select("select * from yike_pay_order where id=#{id}")
    public PayOrder loadById(int id);

    @Insert("insert into yike_pay_order(UserId,BizOrderId,BizId,Amount,Status,PlatformId,CallbackUrl,ReturnUrl" +
            ",AddTime,UpdateTime,UserIp,Title,Msg) values(#{userId},#{bizOrderId},#{bizId},\n" +
            "#{amount},#{status},#{platformId},#{callbackUrl},\n" +
            "#{returnUrl},now(),now(),#{userIp},#{title},#{msg})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(PayOrder order);

    @Update("update yike_pay_order set Amount = #{amount}, Status = #{status}, UpdateTime=now() where id = #{id}")
    public int update(PayOrder order);

    @Select("select * from yike_pay_order where BizId=#{bizId} and BizOrderId=#{bizOrderId} limit 1")
    PayOrder loadByBizIdAndBizOrderId(int bizId, int bizOrderId);


}

package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.PayChannelAudit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayChannelMapper {

    @Select("select * from yike_pay_channel_audit where id=#{id}")
    public PayChannelAudit loadById(int id);

    @Insert("insert into yike_pay_channel_audit(UserId,OrderId,EngineId,PayChannel,PayMethod,PayAmount,Status,TradeNo,OutTradeNo,Source,Msg,AddTime,UpdateTime,UserIp,PartnerId) " +
            "values(#{userId},#{orderId},1,#{payChannel},\n" +
            "\t\t#{payMethod},#{payAmount},1,#{tradeNo},\n" +
            "\t\t#{outTradeNo},#{source},#{msg},now(),now(),#{userIp},\n" +
            "\t\t#{partnerId})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(PayChannelAudit channelAudit);


}

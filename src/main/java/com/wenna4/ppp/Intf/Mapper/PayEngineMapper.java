package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.PayEngine;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface PayEngineMapper {
    @Select("select * from yike_pay_engine where id=#{id}")
    public PayEngine loadById(int id);

    @Insert("insert into yike_pay_engine(UserId,OrderId, MainEngineId, EngineType, Amount, ADesc, Status, AddTime, UpdateTime, UserIp) " +
            "values(#{userId},#{orderId},#{mainEngineId},#{engineType},#{amount},\n" +
            "\t\t   #{desc}, #{status},now(), now(),#{userIp})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(PayEngine payEngine);
    
    @Select("select * from yike_pay_engine where MainEngineId = #{mainEngineId}")
    public List<PayEngine> getSubEngineList(int mainEngineId);
}

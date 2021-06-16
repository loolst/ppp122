package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.VipOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface VipOrderMapper {

    @Select("select * from yike_vip_order where id=#{id}")
    public VipOrder loadById(int id);

    @Insert("insert into yike_vip_order(UserId,Status,Amount,AddTime, UpdateTime) values(#{userId},#{status},#{amount},now(),now())")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(VipOrder order);

    //   public List<ClassOrder> loadByUserIdAndClassId(int userId, int classId, int status);

}

package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into yike_user_new(id,UserName,Password,AddTime, UpdateTime) " +
            "values(null,#{userName},#{password},now(), now()")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(User user);

    @Select("select * from yike_user_new where id = #{id}")
    public User loadById(int id);

    @Select("select * from yike_user_new where UserName = #{userName}")
    public User loadByUserName(String userName);

//

}
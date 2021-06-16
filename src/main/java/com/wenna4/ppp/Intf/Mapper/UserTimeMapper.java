package com.wenna4.ppp.Intf.Mapper;

import com.wenna4.ppp.Intf.Entity.UserTime;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserTimeMapper {
    @Insert("insert into yike_user_time(id,UserId,Name,AddTime) " +
            "values(null,#{userId},#{name},now()")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    public int insert(UserTime usertime);

    @Select("select * from yike_user_time where id = #{id}")
    public UserTime loadById(int id);

    @Select("select * from yike_user_time where UserId = #{userId}")
    public List<UserTime> loadByUserId(int userId);


}

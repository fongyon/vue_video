package com.example.vue_video.mapper;

import com.example.vue_video.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from tb_user where username=#{username}")
    User findByUserName(String username);
    @Insert("insert into tb_user(username,password,salt,email,create_time,update_time)"
            + " values(#{username},#{psw},#{salt},#{email},now(),now())")
    void adduser(String username, String psw, String salt, String email);



}

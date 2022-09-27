package com.xiong.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiong.system.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author xsy
 * @date 2022/9/24
 */

public interface UserMapper extends BaseMapper<User> {

//    @Select("select * from sys_user")
//    List<User> selectAll();
//
//
//    @Insert("INSERT INTO sys_user(username,password,nickname,email,phone,address) " +
//            "VALUES(#{userName},#{password},#{nickName},#{email},#{phone},#{address})")
//    Integer insertUser(User user);
//
//    Integer updateUser(User user);
//
//    @Delete("DELETE FROM sys_user where id=#{id}")
//    Integer delete(Integer id);
//
//    List<User> selectPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize,@Param("userName") String userName,@Param("email") String email,@Param("address") String address);
//
//
//    Integer selectTotal(String userName, String email, String address);
}

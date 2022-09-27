package com.xiong.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xsy
 * @date 2022/9/24
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("sys_user")
public class User {
    @TableId(type = IdType.AUTO )
    private Integer id;
    @TableField("username")
    private String userName;
    @JsonIgnore()
    private String password;
    @TableField("nickname")
    private String nickName;
    private String email;
    private String phone;
    private String address;
    @TableField(value = "avatar_url")  // 指定数据库的字段名称
    private String avatar;
}

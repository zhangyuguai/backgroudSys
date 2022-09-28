package com.xiong.system.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.system.DTO.UserDTO;
import com.xiong.system.common.Result;
import com.xiong.system.entity.User;
import com.xiong.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author xsy
 * @date 2022/9/24
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public Result save() {
        List<User> userList = userService.selectAll();

        return Result.success(userList);
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        if ( userService.saveUser(user)) {
            return Result.success(user);
        }
        return Result.error("保存失败");
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO){
        if(userService.login(userDTO)){
            return  Result.success(userDTO);
        }
        return Result.error("登陆失败");
    }

    @DeleteMapping("/{id}")
    public Result deleteUser(@PathVariable("id") Integer id) {
        if (userService.deleteUser(id)){
            return Result.success(id);
        }
        return Result.error("删除失败");
    }

    @GetMapping("/page")
    public Result selectPage(@RequestParam Integer pageNum
            , @RequestParam Integer pageSize,
                                 @RequestParam(required = false) String userName,
                                 @RequestParam(required = false) String email,
                                 @RequestParam(required = false) String address) {
        Page<User> pageInfo = userService.getPageInfo(pageNum, pageSize, userName, email, address);


        return Result.success(pageInfo);
    }

    @DeleteMapping("/del/batch")
    public Result delBatch(@RequestBody List<Integer> ids) {
        if(userService.delBatch(ids)){
            return Result.success(ids);
        }
        return Result.error("删除失败");
    }
    @GetMapping("/export")
    public Result export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<User> list = userService.selectAll();
        // 通过工具类创建writer 写出到磁盘路径
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用户信息.xlsx");
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名
//        writer.addHeaderAlias("username", "用户名");
//        writer.addHeaderAlias("password", "密码");
//        writer.addHeaderAlias("nickname", "昵称");
//        writer.addHeaderAlias("email", "邮箱");
//        writer.addHeaderAlias("phone", "电话");
//        writer.addHeaderAlias("address", "地址");
//        writer.addHeaderAlias("createTime", "创建时间");
//        writer.addHeaderAlias("avatarUrl", "头像");

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
        return Result.ok();
    }
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 方式1：(推荐) 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
//        List<User> list = reader.readAll(User.class);

        // 方式2：忽略表头的中文，直接读取表的内容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row : list) {
            User user = new User();
            user.setUserName(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickName(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatar(row.get(6).toString());
            users.add(user);
        }

        if (userService.saveBatchUser(users)){
            return Result.success(users);
        }
        return Result.error("上传失败");
    }

}


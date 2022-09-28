package com.xiong.system.service.iml;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiong.system.DTO.UserDTO;
import com.xiong.system.common.Result;
import com.xiong.system.mapper.UserMapper;
import com.xiong.system.entity.User;
import com.xiong.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xsy
 * @date 2022/9/24
 */
@Service
public class UserServiceIml extends ServiceImpl<UserMapper,User> implements UserService{

    @Autowired
    private UserMapper userDao;


    @Override
    public Boolean saveUser(User user){

        return saveOrUpdate(user);
   }

    @Override
    public List<User> selectAll() {
        return list();
    }

    @Override
    public Boolean deleteUser(Integer id) {
        return removeById(id);
    }

    @Override
    public Page<User> getPageInfo(Integer pageNum, Integer pageSize, String userName, String email, String address) {
        Page<User> page = new Page<>(pageNum,pageSize);

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(userName),User::getUserName,userName)
                        .like(StringUtils.isNotBlank(email),User::getEmail,email)
                                .like(StringUtils.isNotBlank(address),User::getAddress,address)
                                        .orderByDesc(User::getId);
        page(page,queryWrapper);

        return page;
    }

    @Override
    public Boolean delBatch(List<Integer> ids) {
        return removeByIds(ids);
    }

    @Override
    public Boolean saveBatchUser(List<User> users) {
        return saveBatch(users);
    }

    @Override
    public Boolean login(UserDTO userDTO) {
        if (!StringUtils.isNotBlank(userDTO.getUserName())&&StringUtils.isNotBlank(userDTO.getPassword())){
            return false;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUserName())
                .eq("password",userDTO.getPassword());
        User user = getOne(queryWrapper);
        if (user==null){
            return false;
        }
        return true;
    }


}

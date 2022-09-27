package com.xiong.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiong.system.DTO.UserDTO;
import com.xiong.system.entity.User;

import java.util.List;

/**
 * @author xsy
 * @date 2022/9/24
 */
public interface UserService {
    /**
     * 增加或更新
     * @param user
     * @return
     */
    Boolean saveUser(User user);

    List<User> selectAll();

    Boolean deleteUser(Integer id);

    Page<User> getPageInfo(Integer num, Integer pageNum, String userName, String email, String pageSize);

    Boolean delBatch(List<Integer> ids);

    Boolean saveBatchUser(List<User> users);

    Boolean login(UserDTO userDTO);
}

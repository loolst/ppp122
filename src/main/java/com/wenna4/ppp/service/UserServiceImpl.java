package com.wenna4.ppp.service;

import com.wenna4.ppp.Intf.Entity.User;
import com.wenna4.ppp.Intf.Mapper.UserMapper;
import com.wenna4.ppp.Intf.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public User loadById(int id) {
        return userMapper.loadById(id);
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public User loadByName(String userName) {
        return userMapper.loadByUserName(userName);
    }
}

package com.wenna4.ppp.Intf;

import com.wenna4.ppp.Intf.Entity.User;

public interface UserService {

    public int insert(User user);

    public User loadById(int id);

    public int update(User user);

    public User loadByName(String userName);


}

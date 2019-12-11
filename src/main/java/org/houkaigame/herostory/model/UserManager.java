package org.houkaigame.herostory.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户管理器
 */
public final class UserManager {
    /**
     * 用户字典
     */
    static  public   final Map<Integer, User> _userMap=new HashMap<>();

    /**
     * 私有化默认构造器
     */
    private UserManager(){

    }

    /**
     * 添加用户
     * @param user
     */
    static  public void addUser(User user){
        if (null !=user) _userMap.put(user.userId, user);
    }
    /**
     *根据用户ID移除用户
     * @param userId
     * @return
     */
    static  public void removeUserById(int userId){
        _userMap.remove(userId);
    }

    /**
     * 用户列表
     * @return
     */
    static public Collection<User> listUser(){
        return _userMap.values();
    }
}

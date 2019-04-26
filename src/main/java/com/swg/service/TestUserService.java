package com.swg.service;

import com.swg.entity.TestUser;

/**
 * @Author swg.
 * @Date 2019/4/26 18:33
 * @CONTACT 317758022@qq.com
 * @DESC
 */
public interface TestUserService {

    /*根据id获取一个用户*/
    TestUser getUserById(Integer id);

    /*插入一位新用户*/
    void insertNewUser();

}

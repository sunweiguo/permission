package com.swg.dao;

import com.swg.entity.TestUser;

/**
 * @Author swg.
 * @Date 2019/4/26 18:27
 * @CONTACT 317758022@qq.com
 * @DESC
 */
public interface TestMapper {
    TestUser getUser(Integer id);

    void insertUser(TestUser user);
}

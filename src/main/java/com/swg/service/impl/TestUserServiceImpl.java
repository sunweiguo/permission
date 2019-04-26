package com.swg.service.impl;

import com.swg.dao.TestMapper;
import com.swg.entity.TestUser;
import com.swg.service.TestUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author swg.
 * @Date 2019/4/26 18:34
 * @CONTACT 317758022@qq.com
 * @DESC
 */
@Service
public class TestUserServiceImpl implements TestUserService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public TestUser getUserById(Integer id) {
        return testMapper.getUser(id);
    }

    @Override
    @Transactional
    public void insertNewUser() {
        //插入两条主键一样的数据，看数据能否回滚
        TestUser user1 = new TestUser();
        user1.setId(6);
        user1.setUsername("hello");
        user1.setTelephone("1111");
        user1.setMail("hello@aa.com");
        user1.setPassword("hello");
        user1.setDeptId(1);
        user1.setStatus(1);
        user1.setRemark("hello");
        user1.setOperator("hello");
        user1.setOperateTime(new Date());
        user1.setOperateIp("1111");
        testMapper.insertUser(user1);

        TestUser user2 = new TestUser();
        user2.setId(6);
        user2.setUsername("hello2");
        user2.setTelephone("2222");
        user2.setMail("hello2@aa.com");
        user2.setPassword("hello2");
        user2.setDeptId(2);
        user2.setStatus(2);
        user2.setRemark("hello2");
        user2.setOperator("hello2");
        user2.setOperateTime(new Date());
        user2.setOperateIp("22222");

        testMapper.insertUser(user2);
    }
}

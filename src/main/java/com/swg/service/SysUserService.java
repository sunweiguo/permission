package com.swg.service;

import com.swg.beans.PageQuery;
import com.swg.beans.PageResult;
import com.swg.entity.SysUser;
import com.swg.vo.UserVO;

/**
 * @Author swg.
 * @Date 2019/5/11 20:16
 * @CONTACT 317758022@qq.com
 * @DESC
 */
public interface SysUserService {
    /*新增用户*/
    void saveUser(UserVO userVO);

    /*更新用户*/
    void update(UserVO userVO);

    /*根据用户登陆的用户账号找用户*/
    SysUser findByKeyword(String keyword);

    PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery);
}

package com.swg.service;

import com.swg.entity.SysDept;
import com.swg.vo.DeptVO;

/**
 * @Author swg.
 * @Date 2019/5/4 14:40
 * @CONTACT 317758022@qq.com
 * @DESC 部门的service层接口
 */
public interface SysDeptService {
    /*新增一个部门*/
    void save(DeptVO deptVO);

    /*更新部门*/
    void update(DeptVO deptVO);

    /*更新所有子孙部门的层级信息*/
    void updateWithChild(SysDept before, SysDept after);
}

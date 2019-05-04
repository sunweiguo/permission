package com.swg.service;

import com.swg.dto.DeptLevelDTO;

import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/4 15:11
 * @CONTACT 317758022@qq.com
 * @DESC 关于树的操作都放在这个接口里面
 */
public interface SysTreeService {
    List<DeptLevelDTO> getDeptTree();
}

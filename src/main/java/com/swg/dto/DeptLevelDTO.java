package com.swg.dto;

import com.google.common.collect.Lists;
import com.swg.entity.SysDept;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/4 15:04
 * @CONTACT 317758022@qq.com
 * @DESC 生成部门树用的递归的数据结构
 */
@Data
public class DeptLevelDTO extends SysDept {
    private List<DeptLevelDTO> sysDeptList = Lists.newArrayList();

    public static DeptLevelDTO adapt(SysDept dept){
        DeptLevelDTO deptLevelDTO = new DeptLevelDTO();
        BeanUtils.copyProperties(dept,deptLevelDTO);
        return deptLevelDTO;
    }
}

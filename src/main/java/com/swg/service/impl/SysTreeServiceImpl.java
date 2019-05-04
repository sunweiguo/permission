package com.swg.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.swg.dao.SysDeptMapper;
import com.swg.dto.DeptLevelDTO;
import com.swg.entity.SysDept;
import com.swg.service.SysTreeService;
import com.swg.util.LevelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/4 15:12
 * @CONTACT 317758022@qq.com
 * @DESC 关于树的实现都在这里
 */
@Service
@Slf4j
public class SysTreeServiceImpl implements SysTreeService {

    @Autowired
    private SysDeptMapper sysDeptMapper;


    @Override
    public List<DeptLevelDTO> getDeptTree() {
        /*1.获取所有的部门*/
        List<SysDept> sysDeptList = sysDeptMapper.getAllDept();
        /*2.遍历所有的部门并构建deptDTO*/
        List<DeptLevelDTO> deptLevelDTOList = Lists.newArrayList();
        List<DeptLevelDTO> rootDTOList = Lists.newArrayList();

        if(CollectionUtils.isNotEmpty(sysDeptList)){
            for(SysDept dept : sysDeptList){
                //将所有的sysdept转换为dto
                DeptLevelDTO dto = DeptLevelDTO.adapt(dept);
                deptLevelDTOList.add(dto);
                //其中将特殊的根层级的所有部门先添加进来
                if(dept.getLevel().equals(LevelUtil.ROOT_LEVEL)){
                    rootDTOList.add(dto);
                }
            }
        }

        return deptListToTree(deptLevelDTOList,rootDTOList);
    }

    /*构建树*/
    private List<DeptLevelDTO> deptListToTree(List<DeptLevelDTO> deptLevelDTOList, List<DeptLevelDTO> rootDTOList) {
        if(CollectionUtils.isEmpty(deptLevelDTOList)){
            return Lists.newArrayList();
        }

        //1.用这个数据结构来辅助，形如<level，对应这个level下的左右的dto集合>
        Multimap<String,DeptLevelDTO> multiMap = ArrayListMultimap.create();
        for(DeptLevelDTO dto:deptLevelDTOList){
            multiMap.put(dto.getLevel(),dto);
        }

        //2.根据seq对这个rootDTOList进行由小到大排序
        Collections.sort(rootDTOList, new Comparator<DeptLevelDTO>() {
            @Override
            public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
                return o1.getSeq()-o2.getSeq();
            }
        });

        //3.递归生成树
        transformDeptTree(rootDTOList, LevelUtil.ROOT_LEVEL, multiMap);
        return rootDTOList;
    }

    //递归生成树
    private void transformDeptTree(List<DeptLevelDTO> rootDTOList, String level, Multimap<String, DeptLevelDTO> multiMap) {
        for(int i=0;i<rootDTOList.size();i++){
            //获取当前层的元素
            DeptLevelDTO dto = rootDTOList.get(i);
            //计算当前层下一层的level
            String nextLevel = LevelUtil.calculateLevel(dto.getLevel(),dto.getId());
            //根据level拿到下一层所有的部门
            List<DeptLevelDTO> nextDeptList = (List<DeptLevelDTO>) multiMap.get(nextLevel);
            if(CollectionUtils.isNotEmpty(nextDeptList)){
                //对这些部门根据seq进行排序
                Collections.sort(nextDeptList, new Comparator<DeptLevelDTO>() {
                    @Override
                    public int compare(DeptLevelDTO o1, DeptLevelDTO o2) {
                        return o1.getSeq()-o2.getSeq();
                    }
                });
                //将排完序的下一层所有部门塞进当前部门的sysDeptList字段中
                dto.setSysDeptList(nextDeptList);
                //继续递归，更新层级
                transformDeptTree(nextDeptList,nextLevel,multiMap);
            }
        }
    }
}

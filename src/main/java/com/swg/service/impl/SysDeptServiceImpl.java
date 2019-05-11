package com.swg.service.impl;

import com.swg.common.RequestHolder;
import com.swg.dao.SysDeptMapper;
import com.swg.entity.SysDept;
import com.swg.exception.ParamException;
import com.swg.service.SysDeptService;
import com.swg.util.BeanValidator;
import com.swg.util.IpUtil;
import com.swg.util.LevelUtil;
import com.swg.vo.DeptVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/4 14:40
 * @CONTACT 317758022@qq.com
 * @DESC 部门的service层实现
 */
@Service
@Slf4j
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Override
    public void save(DeptVO deptVO) {
        /*1.校验传入的参数是否符合要求*/
        BeanValidator.check(deptVO);

        /*2.校验部门名称是否重复*/
        if(checkExist(deptVO.getParentId(),deptVO.getName(),deptVO.getId())){
            log.error("parentId:{}下的{}已经存在",deptVO.getParentId(),deptVO.getName());
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        /*3.利用构建者模式去构建新的SysDept对象*/
        SysDept sysDept = SysDept.builder().name(deptVO.getName()).parentId(deptVO.getParentId())
                            .seq(deptVO.getSeq()).remark(deptVO.getRemark()).build();

        /*4.计算level，主要是根据父亲部门的level和id构建的，根部门层级为0，第一层子部门形如0.1 0.2*/
        sysDept.setLevel(LevelUtil.calculateLevel(getLevel(deptVO.getParentId()),deptVO.getParentId()));
        /*TODO:继续构建数据，这里先放默认的*/
        sysDept.setOperator("System");
        sysDept.setOperateIp("127.0.0.1");
        sysDept.setOperateTime(new Date());

        /*5.插入数据库*/
        log.info("【新增一条部门数据，部门名称是{}，部门的父亲部门id是{}】",deptVO.getName(),deptVO.getParentId());
        sysDeptMapper.insertSelective(sysDept);
    }

    @Override
    public void update(DeptVO deptVO) {
        /*1.校验参数*/
        BeanValidator.check(deptVO);
        /*2.根据id获取部门*/
        SysDept before = sysDeptMapper.selectByPrimaryKey(deptVO.getId());
        if(before == null){
            log.error("部门{}不存在",deptVO.getId());
            throw new ParamException("更新的部门不存在");
        }
        /*2.判断名称是否重复*/
        if(checkExist(deptVO.getParentId(),deptVO.getName(),deptVO.getId())){
            log.error("parentId:{}下的{}已经存在",deptVO.getParentId(),deptVO.getName());
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        /*3.拿到原来部门的level和现在的level进行对比，不一样的话则需要更新这个部门下的所有子部门的level*/
        SysDept after = SysDept.builder().id(deptVO.getId()).name(deptVO.getName()).parentId(deptVO.getParentId())
                .seq(deptVO.getSeq()).remark(deptVO.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(deptVO.getParentId()),deptVO.getParentId()));
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        updateWithChild(before,after);
    }

    /*如果更新了部门的上级部门的话，那么level就需要更新，那么它底下所有的子孙部门都需要更新level*/
    @Transactional
    @Override
    public void updateWithChild(SysDept before, SysDept after) {
        String beforeLevelPrefix = before.getLevel();
        String afterLevelPrefix = after.getLevel();
        if(beforeLevelPrefix != null && afterLevelPrefix != null && !beforeLevelPrefix.equals(afterLevelPrefix)){
            //此时就需要更新子部门的所有层级了,我们根据层级前缀来模糊查询到所有的子部门
            log.info("所要更新的子部门是{}",StringUtils.join(beforeLevelPrefix,LevelUtil.SEPARATOR,before.getId()));
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(StringUtils.join(beforeLevelPrefix,LevelUtil.SEPARATOR,before.getId()));
            if(CollectionUtils.isNotEmpty(deptList)){
                for(SysDept dept:deptList){
                    String level = dept.getLevel();
                    if(level.indexOf(beforeLevelPrefix) == 0){
                        //将原来的层级替换为现在新的层级，主要是替换前缀，后面的保持不变
                        level = afterLevelPrefix + level.substring(beforeLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                //批量更新
                log.info("【开始批量更新所有子部门的层级】");
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        //更新当前部门
        log.info("【开始更新当前部门的层级】");
        sysDeptMapper.updateByPrimaryKeySelective(after);
    }

    /*判断同一层下部门名称是否相等*/
    private boolean checkExist(Integer parentId,String deptName,Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /*拿到当前部门的父亲部门的level*/
    private String getLevel(Integer parentId){
        SysDept sysDept = sysDeptMapper.selectByPrimaryKey(parentId);
        if(sysDept == null){
            return null;
        }
        return sysDept.getLevel();
    }


}

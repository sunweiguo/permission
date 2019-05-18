package com.swg.dao;

import com.swg.beans.PageQuery;
import com.swg.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    SysUser findByKeyword(@Param("keyword") String keyword);

    int countByDeptId(@Param("deptId") Integer deptId);

    List<SysUser> getUserPageByDeptId(@Param("deptId") Integer deptId, @Param("pageQuery") PageQuery pageQuery);
}
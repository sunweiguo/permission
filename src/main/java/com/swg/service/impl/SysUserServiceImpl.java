package com.swg.service.impl;

import com.google.common.base.Preconditions;
import com.swg.beans.PageQuery;
import com.swg.beans.PageResult;
import com.swg.common.RequestHolder;
import com.swg.dao.SysUserMapper;
import com.swg.entity.SysUser;
import com.swg.exception.ParamException;
import com.swg.service.SysUserService;
import com.swg.util.*;
import com.swg.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/11 20:16
 * @CONTACT 317758022@qq.com
 * @DESC 关于用户管理的一些逻辑
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 注册用户
     * 密码显示固定的，如果后续有精力写一下注册页面，不过后台管理系统搞注册也是怪怪的，那就不搞了吧，这里我就用密码123来登陆即可
     * @param userVO
     */
    @Override
    public void saveUser(UserVO userVO) {
        checkUserVo(userVO);
        //4.密码随机生成并加密,这里为了方便，先用固定的
        String password = PasswordUtil.randomPassword();
        //发送邮件告诉用户密码
        String encryptedPassword = MD5Util.encrypt("123");
        //5.插入新用户
        SysUser user = SysUser.builder().username(userVO.getUsername()).telephone(userVO.getTelephone()).mail(userVO.getMail())
                .password(encryptedPassword).deptId(userVO.getDeptId()).status(userVO.getStatus()).remark(userVO.getRemark()).build();

        sysUserMapper.insertSelective(user);
    }

    private void checkUserVo(UserVO userVO){
        //1.验证参数是否合法
        BeanValidator.check(userVO);
        //2.校验电话是否已经存在
        if(checkTelephoneExist(userVO.getTelephone(), userVO.getId())) {
            throw new ParamException("电话已被占用");
        }
        //3.校验邮箱是否已经存在
        if(checkEmailExist(userVO.getMail(), userVO.getId())) {
            throw new ParamException("邮箱已被占用");
        }
    }

    @Override
    public void update(UserVO userVO) {
        checkUserVo(userVO);
        SysUser before = sysUserMapper.selectByPrimaryKey(userVO.getId());
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        SysUser after = SysUser.builder().id(userVO.getId()).username(userVO.getUsername()).telephone(userVO.getTelephone()).mail(userVO.getMail())
                .deptId(userVO.getDeptId()).status(userVO.getStatus()).remark(userVO.getRemark()).build();
        log.info("用户原来的信息为：{}",before);
        /*塞入更新者的用户名和ip*/
        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());
        log.info("用户更新后的信息为：{}",after);
        sysUserMapper.updateByPrimaryKeySelective(after);
    }

    @Override
    public SysUser findByKeyword(String keyword) {
        return sysUserMapper.findByKeyword(keyword);
    }

    private boolean checkEmailExist(String mail, Integer userId) {
        return sysUserMapper.countByMail(mail, userId) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer userId) {
        return sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    @Override
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery) {
        BeanValidator.check(pageQuery);
        //根据部门id获取这个部门下的所有用户
        int userListCount = sysUserMapper.countByDeptId(deptId);
        if(userListCount > 0){
            //根据部门id和前端的页面查询出指定的分页信息
            List<SysUser> userPage = sysUserMapper.getUserPageByDeptId(deptId,pageQuery);
            return PageResult.<SysUser>builder().total(userListCount).data(userPage).build();
        }
        return PageResult.<SysUser>builder().build();
    }
}

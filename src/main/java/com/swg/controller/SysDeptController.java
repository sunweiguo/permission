package com.swg.controller;

import com.swg.common.JsonData;
import com.swg.dto.DeptLevelDTO;
import com.swg.service.SysDeptService;
import com.swg.service.SysTreeService;
import com.swg.vo.DeptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/4 14:36
 * @CONTACT 317758022@qq.com
 * @DESC 关于部门的controller控制层
 */
@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysTreeService sysTreeService;

    /**
     * 进入部门管理的jsp页面的路径
     * @return
     */
    @RequestMapping("/dept.page")
    public ModelAndView page() {
        return new ModelAndView("dept");
    }

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptVO deptVO){
        sysDeptService.save(deptVO);
        return JsonData.success("新增部门成功");
    }

    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(DeptVO deptVO){
        List<DeptLevelDTO> deptLevelDTOList = sysTreeService.getDeptTree();
        return JsonData.success(deptLevelDTOList,"获取部门树成功");
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptVO deptVO){
        sysDeptService.update(deptVO);
        return JsonData.success("更新部门成功");
    }


}

package com.swg.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @Author swg.
 * @Date 2019/5/4 14:38
 * @CONTACT 317758022@qq.com
 * @DESC 新增或者更新部门接口时传递的对象
 */
@Data
@ToString
public class DeptVO {
    private Integer id;

    @NotBlank(message = "部门名称不可以为空")
    @Length(max = 15, min = 2, message = "部门名称长度需要在2-15个字之间")
    private String name;

    private Integer parentId = 0;//取一个默认值，防止空指针异常

    @NotNull(message = "展示顺序不可以为空")
    private Integer seq;

    @Length(max = 150, message = "备注的长度需要在150个字以内")
    private String remark;

}

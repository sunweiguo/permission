package com.swg.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/3 15:30
 * @CONTACT 317758022@qq.com
 * @DESC 用于测试校验是否正常
 */
@Data
public class TestVO {
    @NotNull(message = "年龄不能为空")
    @Min(value = 1,message = "年龄最小是1岁")
    @Max(value = 20,message = "年龄最大是20岁")
    private int age;

    @NotBlank(message = "名字不能为空")
    private String name;

    @NotEmpty(message = "兴趣不能为空，至少填一个")
    private List<String> intersts;
}

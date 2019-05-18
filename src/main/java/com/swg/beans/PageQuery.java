package com.swg.beans;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * @Author swg.
 * @Date 2019/5/18 14:27
 * @CONTACT 317758022@qq.com
 * @DESC 前端翻页所传递过来的信息
 */
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1,message = "当前页码不合法")
    private int pageNo = 1;

    @Getter
    @Setter
    @Min(value = 1,message = "每页展示数量不合法")
    private int pageSize = 10;

    @Setter
    private int offset;

    //偏移量是自己计算出来的
    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}

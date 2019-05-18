package com.swg.beans;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author swg.
 * @Date 2019/5/18 14:30
 * @CONTACT 317758022@qq.com
 * @DESC 列表返回的类，用于前端的列表展示
 */
@Data
@Builder
@ToString
public class PageResult<T> {
    private List<T> data = Lists.newArrayList();

    private int total = 0;
}

package com.swg.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author swg.
 * @Date 2019/5/4 14:50
 * @CONTACT 317758022@qq.com
 * @DESC 计算当前部门的层级即level
 */
public class LevelUtil {
    public static final String SEPARATOR = ".";
    public static final String ROOT_LEVEL = "0";

    public static String calculateLevel(String parentLevel, int parentId) {
        if(StringUtils.isBlank(parentLevel)){
            return ROOT_LEVEL;
        }
        return StringUtils.join(parentLevel,SEPARATOR,parentId);
    }
}

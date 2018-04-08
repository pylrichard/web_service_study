package com.bd.imooc.permission.rbac.util;

import org.apache.commons.lang3.StringUtils;

public class LevelUtil {
    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    /**
     * 得到的格式为
     * 0
     * 0.1
     * 0.1.4
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel, SEPARATOR, parentId);
        }
    }
}

package com.bd.imooc.permission.rbac.util;

import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

public class CollectionUtil {
    public static boolean isModified(List<Integer> originIdList, List<Integer> idList) {
        if (originIdList.size() == idList.size()) {
            Set<Integer> originIdSet = Sets.newHashSet(originIdList);
            Set<Integer> idSet = Sets.newHashSet(idList);
            originIdSet.removeAll(idSet);
            return !CollectionUtils.isEmpty(originIdSet);
        } else {
            return true;
        }
    }
}

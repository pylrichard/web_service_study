package com.bd.roncoo.eshop.cache.server.degrade;

/**
 * 手动降级标识
 */
public class IsDegrade {
    private static boolean degrade = false;

    public static boolean isDegrade() {
        return degrade;
    }

    public static void setDegrade(boolean degrade) {
        IsDegrade.degrade = degrade;
    }
}

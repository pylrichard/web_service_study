package com.bd.roncoo.book.shop.book.service.future;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Discount {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

    private final int percent;
}

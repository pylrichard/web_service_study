package com.bd.roncoo.book.shop.db.repository.support;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义转换逻辑
 */
public interface Domain2InfoConverter<T, I> extends Converter<T, I> {
}

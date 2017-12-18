package com.bd.roncoo.book.shop.db.repository.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

/**
 * PageImpl添加无参构造函数，Dubbo服务RPC调用需要
 * public PageImpl() {
 * this(new ArrayList<T>());
 * }
 */
public class QueryResultConverter {
    private static Logger logger = LoggerFactory.getLogger(QueryResultConverter.class);

    /**
     * 前2个convert()根据反射进行转换
     *
     * @param clazz 转换到的类的类型
     */
    public static <T, I> Page<I> convert(Page<T> pageData, Class<I> clazz, Pageable pageable) {
        List<T> contents = pageData.getContent();
        List<I> infos = convert(contents, clazz);

        return new PageImpl<I>(infos, pageable, pageData.getTotalElements());
    }

    public static <I, T> List<I> convert(List<T> contents, Class<I> clazz) {
        List<I> infos = new ArrayList<I>();

        for (T domain : contents) {
            I info = null;

            try {
                info = clazz.newInstance();
                /**
                 * 将相同字段的值进行复制
                 */
                BeanUtils.copyProperties(info, domain);
            } catch (Exception e) {
                logger.info("convert data failed", e);
                throw new RuntimeException("convert data failed");
            }

            if (info != null) {
                infos.add(info);
            }
        }

        return infos;
    }

    /**
     * 后2个convert()可以自定义转换逻辑
     */
    public static <T, I> Page<I> convert(Page<T> pageData, Pageable pageable, Domain2InfoConverter<T, I> converter) {
        List<T> contents = pageData.getContent();
        List<I> infos = convert(contents, converter);

        return new PageImpl<I>(infos, pageable, pageData.getTotalElements());
    }

    public static <I, T> List<I> convert(List<T> contents, Domain2InfoConverter<T, I> converter) {
        List<I> infos = new ArrayList<I>();

        for (T domain : contents) {
            infos.add(converter.convert(domain));
        }

        return infos;
    }
}

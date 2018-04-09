package com.bd.roncoo.spring.boot.primer.util.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Mapper的扫描包路径
 */
@Configuration
@MapperScan("com.bd.roncoo.spring.boot.mapper")
public class MybatisScanConfiguration {}

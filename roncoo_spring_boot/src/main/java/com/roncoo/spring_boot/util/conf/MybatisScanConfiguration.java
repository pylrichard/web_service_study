package com.roncoo.spring_boot.util.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 配置Mapper的扫描包路径
 */
@Configuration
@MapperScan("com.roncoo.spring_boot.mapper")
public class MybatisScanConfiguration {}

package com.poplar.config;

import com.poplar.database.DataSource;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Create BY poplar ON 2020/4/14
 * 封装其他数据信息
 */
@Data
public class Configuration {

    private DataSource dataSource;

    //还需要一个封装mapper文件中的sql语句的结构,mybatis是使用名称空间加id来定位一条sql语句的。
    private Map<String,MapperStatement> mapperStatementMap = new HashMap<>();
}

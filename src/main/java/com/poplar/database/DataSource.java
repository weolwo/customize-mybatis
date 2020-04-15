package com.poplar.database;

import lombok.Data;

/**
 * Create BY poplar ON 2020/4/14
 * 用来封装链接数据库的相关信息
 */
@Data
public class DataSource {

    private String driver;

    private String url;

    private String userName;

    private String passWord;
}

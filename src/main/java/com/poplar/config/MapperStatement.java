package com.poplar.config;

import lombok.Data;

/**
 * Create BY poplar ON 2020/4/14
 * 用来封装mapper.xml中的数据
 */
@Data
public class MapperStatement {

    private String nameSpace;

    private String sourceId;

    private String resultType;

    private String sql;
}

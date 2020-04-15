package com.poplar.executor;

/**
 * Create BY poplar ON 2020/4/14
 */

import com.poplar.config.MapperStatement;

import java.util.List;

/**
 * @author lison
 * MyBaits核心接口之一， 定义了数据库操作最基本的方法，Sq1Session的功能都是基于它来实现的
 */
public interface Executor {

    /**
     * 查询接口
     *
     * @param ms 封装sql语句的MapperStatement对象
     * @param parameter 传入sql的参数
     * @param <E>
     * @return 将数据转换成指定对象结果集返回
     */
    <E> List<E> query(MapperStatement ms, Object parameter);
}
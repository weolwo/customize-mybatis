package com.poplar.session;

import java.util.List;

/**
 * Create BY poplar ON 2020/4/14
 * mybatis暴露给外部的接口，实现增测改查的能力
 * 1.对外提供数据访间的api
 * 2.对内将请求转发给executor,体现了单一职责原则
 */
public interface SqlSession {

    /**
     * 根据传入的条件查询单-结果
     *
     * @ param statement 方法对 应的sq1语句，namespace + id
     * @ param parameter 要传入到sql语句中的查询参数
     * @ return返回指定的结果对象
     */
    <T> T selectOne(String statement, Object parameter);

    <E> List<E> selectList(String statement, Object parameter);

    <T> T getMapper(Class<T> type);

}

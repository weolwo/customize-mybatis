package com.poplar.session;

import com.poplar.config.Configuration;
import com.poplar.config.MapperStatement;
import com.poplar.executor.DefaultExecutor;
import com.poplar.executor.Executor;
import com.poplar.reflect.MapperProxy;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Create BY poplar ON 2020/4/14
 */
public class DefaultSqlSession implements SqlSession {

    private final Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = new DefaultExecutor(configuration);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<Object> selectList = this.selectList(statement, parameter);
        if (selectList == null || selectList.size() == 0) {
            return null;
        }
        if (selectList.size() == 1) {
            return (T) selectList.get(0);
        } else {
            throw new RuntimeException("Too Many Results!");
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MapperStatement mapperStatement = configuration.getMapperStatementMap().get(statement);
        return executor.query(mapperStatement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy mapperProxy = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mapperProxy);
    }
}

package com.poplar.executor;

import com.poplar.config.Configuration;
import com.poplar.config.MapperStatement;
import com.poplar.reflect.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create BY poplar ON 2020/4/14
 */

public class DefaultExecutor implements Executor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExecutor.class);

    private final Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> query(MapperStatement ms, Object parameter) {
        logger.info("SQL语句=>{}", ms.getSql());
        List<E> ret = new ArrayList<E>();//定文返回结果集

        try {
            Class.forName(conf.getDataSource().getDriver());//加载驱动程序
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement prepareStatement = null;
        ResultSet resultSet = null;
        try {
            //获取连接，从Mappedstatement中 获取数据库信息
            connection = DriverManager.getConnection(conf.getDataSource().getUrl(), conf.getDataSource().getUserName(), conf.getDataSource().getPassWord());
            //创建prepareStatement,从MapperStatement中 获取sgl语句
            logger.info("获取数据库链接{}", connection);
            prepareStatement = connection.prepareStatement(ms.getSql());
            //处理sq1语句中的占位符
            parameterize(prepareStatement, parameter);
            //执行查询操作获取resultSet
            resultSet = prepareStatement.executeQuery();
            //将结果集通过反射技术，填充到list中
            handlerResultSet(resultSet, ret, ms.getResultType());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                prepareStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return ret;
    }

    //对preparedStatement中的占位符进行处理
    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            preparedStatement.setInt(1, (int) parameter);
        } else if (parameter instanceof Long) {
            preparedStatement.setLong(1, (long) parameter);
        } else if (parameter instanceof String) {
            preparedStatement.setString(1, (String) parameter);
        }

    }

    //读取resultSet中的数据，并转换成目标对象
    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> clazz = null;
        try {
            //通过反射获取类对象
            clazz = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()) {
                //通过反射实例化对象
                Object entity = clazz.newInstance();
                //使用反射工具将resultset中的数据填充到entity中
                ReflectionUtil.setPropToBeanFromResultSet(entity, resultSet);
                //对象加入返回集合中
                ret.add((E) entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
package com.poplar.test;

import com.poplar.bean.Student;
import com.poplar.mapper.StudentMapper;
import com.poplar.session.SqlSession;
import com.poplar.session.SqlSessionFactory;

import java.util.List;

/**
 * Create BY poplar ON 2020/4/14
 */
public class ApplicationTest {

    public static void main(String[] args) {

        //1.实例化sqlSessionFactory,加载数据库配置文件以及mapper.xml文件到configuration对象
        SqlSessionFactory factory = new SqlSessionFactory();
        //2.获取sqlSession对象
        SqlSession session = factory.openSession();
        System.out.println(session);
        //3. 通过动态代理跨越面向接口编程和ibatis编程模型的鸿沟
        StudentMapper studentMapper = session.getMapper(StudentMapper.class);
        //4.遵街门jdbc规范，通过底层的四大对象的合作完成数据查湖和数据转化
        Student student = studentMapper.getOne(1L);
        System.out.println(student);
        System.out.println("-------------------");

        List<Student> list = studentMapper.list();
        list.forEach(System.out::println);

    }

}

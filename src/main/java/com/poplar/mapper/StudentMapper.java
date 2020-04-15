package com.poplar.mapper;

import com.poplar.bean.Student;

import java.util.List;

/**
 * Create BY poplar ON 2020/4/14
 */
public interface StudentMapper {

    Student getOne(Long id);

    List<Student> list();
}

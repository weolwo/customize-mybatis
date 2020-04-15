package com.poplar.reflect;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create BY poplar ON 2020/4/15
 */
public class ReflectionUtil {

    //为指定的bean的propName属性的值没为value
    public static void setPropToBean(Object bean, String propName, Object value) {
        Field f;
        try {
            f = bean.getClass().getDeclaredField(propName);//获得对象指定的属性
            f.setAccessible(true);//将字段设置为可通过反射进行访问
            f.set(bean, value);//为属性设值
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static void setPropToBeanFromResultSet(Object entity, ResultSet resultSet) throws SQLException {
        Field[] declareDFields = entity.getClass().getDeclaredFields();//通过反射获取对象的所有字段
        for (int i = 0; i < declareDFields.length; i++) {//遍历所有的字段，从resultSet中读取相应的数据，并填充至对象的属性中
            if (declareDFields[i].getType().getSimpleName().equals("String")) {//如果是字符申类型数据
                setPropToBean(entity, declareDFields[i].getName(), resultSet.getString(declareDFields[i].getName()));
            } else if (declareDFields[i].getType().getSimpleName().equals(" Integer")) {//如果是int类型数据
                setPropToBean(entity, declareDFields[i].getName(), resultSet.getInt(declareDFields[i].getName()));
            } else if (declareDFields[i].getType().getSimpleName().equals("Long")) {//如果是1ong类型数据
                setPropToBean(entity, declareDFields[i].getName(), resultSet.getLong(declareDFields[i].getName()));
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("com.poplar.bean.Role");
        Object user = clazz.newInstance();
        ReflectionUtil.setPropToBean(user, "roleName", "admin");
        System.out.println(user);

       /* Field[] declaredFields = user.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
            System.out.println(field.getType().getSimpleName());

        }*/
    }
}
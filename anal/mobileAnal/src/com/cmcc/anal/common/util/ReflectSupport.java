package com.cmcc.anal.common.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 反射支持类
 * 
 * @author jiangfeng
 *
 */
public class ReflectSupport {
    /**
     * newObject
     * 
     * @param className
     * @param loader
     * @return
     */
    public static Object newObject(String className, ClassLoader loader) {
        try {
            if ("".equals(className) || null == className) {
                return null;
            }
            Class cls = Class.forName(className, true, loader);
            return cls.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param className
     * @return
     */
    public static Object newObject(String className) {
        ClassLoader loader = ReflectSupport.class.getClassLoader();
        return newObject(className, loader);
    }

    /**
     * 
     * @param className
     * @param args
     * @param loader
     * @return
     */
    public static Object newObject(String className, Object[] args,
            ClassLoader loader) {
        try {
            Class cls = Class.forName(className, true, loader);
            return ConstructorUtils.invokeConstructor(cls, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param cls
     * @param args
     * @return
     */
    public static Object newObject(Class cls, Object[] args) {
        try {
            return ConstructorUtils.invokeConstructor(cls, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 
     * @param className
     * @param args
     * @return
     */
    public static Object newObject(String className, Object[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return newObject(className, args, loader);
    }

    /**
     * 
     * @param target
     * @param property
     * @return
     */
    public static Object getProperty(Object target, String property) {
        try {
            return PropertyUtils.getProperty(target, property);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param target
     * @param property
     * @param value
     */
    public static void setProperty(Object target, String property, Object value) {
        try {
            BeanUtils.setProperty(target, property, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 方法调用
     * 
     * @param target
     * @param methodName
     * @param args
     * @return @throws
     *         Exception
     */
    public static Object invokeMethod(Object target, String methodName,
            Object[] args) throws Exception {
        return MethodUtils.invokeMethod(target, methodName, args);
    }

    /**
     * 时候有指定的方法
     * 
     * @param target
     * @param methodName
     * @param argTypes
     * @return
     */
    public static boolean exist(Object target, String methodName,
            Class[] argTypes) {
        return MethodUtils.getAccessibleMethod(target.getClass(), methodName,
                argTypes) != null;
    }

    /**
     * 
     * @param target
     * @param methodName
     * @param argTypes
     * @return
     */
    public static boolean exist(Class target, String methodName,
            Class[] argTypes) {
        return MethodUtils.getAccessibleMethod(target, methodName, argTypes) != null;
    }

    /**
     * 
     * @param clsName
     * @return
     */
    public static Class forClassName(String clsName) {
        try {
            return Class.forName(clsName);
        } catch (Throwable thr) {
        }
        return null;
    }
}

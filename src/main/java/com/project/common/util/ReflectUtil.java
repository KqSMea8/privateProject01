package com.project.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 反射工具类
 */
@SuppressWarnings("unchecked")
public final class ReflectUtil {
	private ReflectUtil() {
	}

	/**
	 * 根据类完整名称获得类对象
	 * 
	 * @param className
	 *            类名称
	 * @return 类对象
	 * @throws ClassNotFoundException
	 */
	public static Class<?> classForName(String className) throws ClassNotFoundException {
		try {
			ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
			if (contextClassLoader != null) {
				return contextClassLoader.loadClass(className);
			}
		} catch (Throwable ignore) {
		}
		return Class.forName(className);
	}

	/**
	 * 根据对象获得对象的类对象
	 * 
	 * @param obj
	 *            对象
	 * @return Class对象信息
	 */
	public static Class<?> getClassByObject(Object obj) {
		Class<?> cls = obj.getClass();
		return cls;

	}

	/**
	 * 检查类的成员是否为公共的
	 * 
	 * @param clazz
	 *            类对象
	 * @param member
	 *            成员
	 * @return boolean是否为公共
	 */
	public static boolean isPublic(Class<?> clazz, Member member) {
		return Modifier.isPublic(member.getModifiers()) && Modifier.isPublic(clazz.getModifiers());
	}

	/**
	 * 检查类的成员是否为公共的
	 * 
	 * @param member
	 *            成员
	 * @return boolean是否为公共
	 */
	public static boolean isPublic(Member member) {
		return isPublic(member.getDeclaringClass(), member);
	}

	/**
	 * 获得所有类的属性字段对象
	 * 
	 * @param cls
	 *            类对象
	 * @return List<Field> 字段对象集合
	 */
	private static List<Object> getFields(Class<?> cls) {
		List<Object> list = new ArrayList<Object>();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			f.setAccessible(true);
			list.add(f);
		}
		return list;
	}

	/**
	 * 获得类中属性名字字符串
	 * 
	 * @param cls
	 *            类对象
	 * @return List<String> 名字字符串集合
	 */
	public static List<String> getFieldName(Class<?> cls) {
		List<String> list = new ArrayList<String>();
		Iterator<Object> it = null;
		Field f = null;
		for (it = (getFields(cls).iterator()); it.hasNext();) {
			f = (Field) it.next();
			list.add(f.getName());
		}
		return list;
	}

	/**
	 * 判断类中是否包含指定字段
	 * 
	 * @param cls
	 *            类
	 * @param fieldName
	 *            字段名称
	 * @return boolean是否存在
	 */
	public static boolean isExistField(Class<?> cls, String fieldName) {
		List<String> list = getFieldName(cls);
		return list.contains(fieldName);
	}

	/**
	 * 获得类的所有方法
	 * 
	 * @param cls
	 *            类对象
	 * @return List<Object>
	 */
	private static List<Object> getMethods(Class<?> cls) {
		List<Object> list = new ArrayList<Object>();
		Method[] m = cls.getDeclaredMethods();
		for (Method f : m) {
			f.setAccessible(true);
			list.add(f);
		}
		return list;
	}

	/**
	 * 获得类的所有方法名称集合
	 * 
	 * @param cls
	 *            类对象
	 * @return List<String>
	 */
	public static List<String> getMethodName(Class<?> cls) {
		List<String> list = new ArrayList<String>();
		Iterator<Object> it = null;
		Method f = null;
		for (it = (getMethods(cls).iterator()); it.hasNext();) {
			f = (Method) it.next();
			list.add(f.getName());
		}
		return list;
	}

	/**
	 * 判断类中是否包含指定方法
	 * 
	 * @param cls
	 *            类
	 * @param mname
	 *            方法名称
	 * @return boolean是否存在
	 */
	public static boolean isExistMethod(Class<?> cls, String mname) {
		List<String> list = getMethodName(cls);
		return list.contains(mname);
	}

	/**
	 * CLASSPATH 类加载器
	 * 
	 * @return ClassLoader
	 */
	public static ClassLoader getSystemClassLoader() {
		return ClassLoader.getSystemClassLoader();
	}

	/**
	 * 根据类的方法名称和方法参数类型获得方法对象
	 * 
	 * @param cls
	 *            类对象
	 * @param methodName
	 *            方法名字
	 * @param paramType
	 *            方法参数类型
	 * @return Method
	 */
	public static Method getMethod(Class<?> cls, String methodName, Class<?>[] paramType) {
		Method method = null;
		try {
			method = cls.getMethod(methodName.trim(), paramType);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return method;
	}

	/**
	 * 反射执行指定的方法
	 * 
	 * @param obj
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param paramValue
	 *            参数值
	 * @param paramType
	 *            方法参数类型
	 * @return 方法执行后返回结果
	 */
	public static Object invokeMethod(Object obj, String methodName, Object[] paramValue, Class<?>[] paramType) {
		Object result = null;
		Method method = null;
		try {
			method = getClassByObject(obj).getMethod(methodName.trim(), paramType);
			result = method.invoke(obj, paramValue);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 执行指定对象的getter方法并返回对应的值
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeGetMethod(Object obj, String fieldName) {
		Class cls = getClassByObject(obj);
		String getMethodName = "get" + StringUtil.firstLetterUpper(fieldName);
		if (!isExistField(cls, fieldName)) {
			throw new IllegalArgumentException(fieldName + " attribute is not exist");
		}
		if (!isExistMethod(cls, getMethodName)) {
			throw new IllegalArgumentException(getMethodName + " method is not exist");
		}
		try {
			Method method = cls.getMethod(getMethodName);
			return method.invoke(obj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	/**
	 * 获得指定方法的参数类型
	 * 
	 * @param method
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class[] getMethodTypes(Method method) {

		return method.getParameterTypes();
	}

	/**
	 * 根据指定的Class对象获得实例对象
	 * 
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getInstanceByClass(Class cls) {
		Object obj = null;

		try {
			obj = cls.newInstance();
		} catch (InstantiationException e) {

			e.printStackTrace();
		} catch (IllegalAccessException e) {

			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 获得指定属性字段类型
	 * 
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Class getFieldType(Object obj, String fieldName) {
		Class cls = getClassByObject(obj);
		if (!isExistField(cls, fieldName)) {
			throw new IllegalArgumentException(fieldName + " attribute is not exist");
		}
		try {
			Field f = cls.getDeclaredField(fieldName);
			return f.getType();
		} catch (Exception ex) {

		}
		return null;
	}

	/**
	 * 根据类名获得类的实例
	 * 
	 * @param className
	 * @return 类的实例
	 * @throws ClassNotFoundException
	 */
	public static Object getInstance(String className) throws ClassNotFoundException {
		return classForName(className);
	}

	/**
	 * 根据对象获得完整类名
	 * 
	 * @param obj
	 * @return 类名
	 */
	public static String getClassName(Object obj) {
		if (obj == null) {
			throw new IllegalArgumentException("obj is null");
		}
		return obj.getClass().getName();
	}
}

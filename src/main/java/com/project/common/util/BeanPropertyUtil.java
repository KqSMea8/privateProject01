package com.project.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class BeanPropertyUtil {
	private static Logger log = Logger.getLogger(BeanPropertyUtil.class);

	public static String[] getBeanAllPropertyName(Object bean) {
		List<String> resultList = new ArrayList<String>();
		try {
			// Class cls = Class.forName(bean.class.getName());
			Class<?> cls = bean.getClass();
			for (; !cls.equals(Object.class); cls = cls.getSuperclass()) {
				Field[] field = cls.getDeclaredFields();
				for (Field f : field) {
					// SconsoleLog.println(f.getName());
					resultList.add(f.getName());
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return (String[]) resultList.toArray(new String[resultList.size()]);
	}

	@SuppressWarnings("rawtypes")
	public static PropertyDescriptor[] getAvailablePropertyDescriptors(Object bean) {
		try { // 从 Bean 中解析属性信息并查找相关的 write 方法
			BeanInfo info = Introspector.getBeanInfo(bean.getClass());
			if (info != null) {
				PropertyDescriptor pd[] = info.getPropertyDescriptors();
				Vector columns = new Vector();
				for (int i = 0; i < pd.length; i++) {
					String fieldName = pd[i].getName();
					if (fieldName != null && !fieldName.equals("class")) {
						columns.add(pd[i]);
					}
				}
				PropertyDescriptor[] arrays = new PropertyDescriptor[columns.size()];
				for (int j = 0; j < columns.size(); j++) {
					arrays[j] = (PropertyDescriptor) columns.get(j);
				}
				return arrays;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	public static boolean isExistProperty(Object bean, String propertyName) {
		PropertyDescriptor[] pdArray = getAvailablePropertyDescriptors(bean);
		for (PropertyDescriptor pd : pdArray) {
			if (pd.getName().equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

	public static Object getPropertyValue(Object bean, String propertyName) {
		try {
			if (ReflectUtil.getFieldType(bean, propertyName) == Date.class) {
				return (Date) ReflectUtil.invokeGetMethod(bean, propertyName);
			}
			if (ReflectUtil.getFieldType(bean, propertyName) == BigDecimal.class) {
				return (BigDecimal) ReflectUtil.invokeGetMethod(bean, propertyName);
			} else {

				return BeanUtils.getProperty(bean, propertyName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isNullProperty(Object bean, String propertyName) {
		boolean isNullProperty = false;
		try {
			Object obj = BeanUtils.getProperty(bean, propertyName);
			if (obj != null) {
				isNullProperty = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isNullProperty;
	}

	@SuppressWarnings("rawtypes")
	public static Object convertMapToBean(Object targetEntity, Map formMap) {
		String[] beanProperties = getBeanAllPropertyName(targetEntity);
		Map<String, String> beanPropClassMap = getBeanPropClassMap(targetEntity);
		for (String property : beanProperties) {
			if ((formMap.containsKey(property))) {
				try {
					BeanUtils.setProperty(targetEntity, property, classConvert(formMap.get(property), beanPropClassMap.get(property)));

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return targetEntity;
	}

	@SuppressWarnings("rawtypes")
	public static Object convertMapToBean(Object targetEntity, Map formMap, String[] param, boolean flag) {
		String[] beanProperties = getBeanAllPropertyName(targetEntity);
		Map<String, String> beanPropClassMap = getBeanPropClassMap(targetEntity);
		for (String property : beanProperties) {
			if ((formMap.containsKey(property))) {
				try {
					if (contains(param, property) == flag) {
						BeanUtils.setProperty(targetEntity, property, classConvert(formMap.get(property), beanPropClassMap.get(property)));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return targetEntity;
	}

	@SuppressWarnings("rawtypes")
	public static Object convertMapToBean(Object targetEntity, Map formMap, String[] param) {
		return convertMapToBean(targetEntity, formMap, param, true);
	}

	public static boolean contains(String[] arr, String source) {
		List<String> tempList = Arrays.asList(arr);

		if (tempList.contains(source.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	private static Object classConvert(Object o, String className) throws Exception {
		Object returnObj = null;
		if (className.indexOf("[") == -1) { // 非数组类型
			if (className.equals("java.lang.String")) {
				return o;
			} else if (className.equals("java.lang.Integer") || className.equals("int")) {
				if (StringUtil.isEmpty(o.toString())) {
					o = new Integer(0);
				}
				return new Integer(o.toString());
			} else if (className.equals("java.lang.Float") || className.equals("float")) {
				if (StringUtil.isEmpty(o.toString())) {
					o = new Float(0);
				}
				return new Float(o.toString());
			} else if (className.equals("java.lang.Long") || className.equals("long")) {
				if (StringUtil.isEmpty(o.toString())) {
					o = new Long(0);
				}
				return Double.valueOf(o.toString()).longValue();
			} else if (className.equals("java.lang.Double") || className.equals("double")) {
				if (StringUtil.isEmpty(o.toString())) {
					o = new Double(0);
				}
				return new Double(String.valueOf(o));
			} else if (className.equals("java.math.BigDecimal")) {
				if (StringUtil.isEmpty(o.toString())) {
					o = new BigDecimal(0);
				}
				return new java.math.BigDecimal(new java.math.BigDecimal(o.toString()).toPlainString());
			} else if (className.equals("java.lang.Byte")) {
				return new Byte(o.toString());
			} else if (className.equals("java.util.Date")) {
				String strDateTime = o.toString();
				java.util.Date returnValue = null;
				if (strDateTime.length() != 0) {
					if (strDateTime.length() == 10) {
						try {
							returnValue = DateUtil.stringToDate(strDateTime, "yyyy-MM-dd");
						} catch (Exception e) {
							// date format error
						}
					} else if (strDateTime.length() == 16) {
						try {
							returnValue = DateUtil.stringToDate(strDateTime, "yyyy-MM-dd HH:mm");
						} catch (Exception e) {
							// date format error
						}
					} else if (strDateTime.length() == 19) {
						try {
							returnValue = DateUtil.stringToDate(strDateTime, "yyyy-MM-dd HH:mm:ss");
						} catch (Exception e) {
							// date format error
						}
					} else if (strDateTime.length() == 23) {
						try {
							returnValue = DateUtil.stringToDate(strDateTime, "yyyy-MM-dd HH:mm:ss:SSS");
						} catch (Exception e) {
							// date format error
						}
					}
				}
				return returnValue;
			} else if (className.equals("java.util.Map")) {
				return (Map<?, Object>) o;
			} else if (className.equals("java.util.List")) {
				return (List<Object>) o;
			}
		} else { // 数组类型
			String[] oArray = (String[]) o;
			if (className.equals("[java.lang.String;")) { // String 数组
				return oArray;
			} else if (className.equals("[java.lang.Integer;")) { // Integer 数组
				Integer[] temp = new Integer[oArray.length];
				for (int i = 0; i < oArray.length; i++) {
					temp[i] = new Integer(oArray[i]);
				}
				return temp;
			} else if (className.equals("[java.lang.Long;")) { // Long 数组
				Long[] temp = new Long[oArray.length];
				for (int i = 0; i < oArray.length; i++) {
					temp[i] = new Long(oArray[i]);
				}
				return temp;
			} else if (className.equals("[java.lang.Float;")) { // Float 数组
				Float[] temp = new Float[oArray.length];
				for (int i = 0; i < oArray.length; i++) {
					temp[i] = new Float(oArray[i]);
				}
				return temp;
			} else if (className.equals("[java.lang.Double;")) { // Double 数组
				Double[] temp = new Double[oArray.length];
				for (int i = 0; i < oArray.length; i++) {
					temp[i] = new Double(oArray[i]);
				}
				return temp;
			} else if (className.equals("[java.lang.BigDecimal;")) { // BigDecimal 数组
				BigDecimal[] temp = new BigDecimal[oArray.length];
				for (int i = 0; i < oArray.length; i++) {
					temp[i] = new BigDecimal(oArray[i]);
				}
				return temp;
			}
		}

		return returnObj;

	}

	@SuppressWarnings("rawtypes")
	public static Map getBeanPropClassMap(Object bean) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			BeanInfo info = Introspector.getBeanInfo(bean.getClass());
			if (info != null) {
				PropertyDescriptor pd[] = info.getPropertyDescriptors();
				for (int i = 0; i < pd.length; i++) {
					String fieldName = pd[i].getName();
					if (fieldName != null && !fieldName.equals("class")) {
						resultMap.put(fieldName, pd[i].getPropertyType().getName());
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("error occured in getBeanPropClassMap:", ex);
		}
		return resultMap;
	}

	@SuppressWarnings("rawtypes")
	public static Map convertBeanToMap(Object bean) throws Exception {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					if (result instanceof java.util.Date) {
						returnMap.put(propertyName, DateUtil.dateToString((java.util.Date) result, "yyyy-MM-dd HH:mm:ss"));
					} else {
						returnMap.put(propertyName, result);
					}
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

	/**
	 * 将一个 Map 对象转化为一个 JavaBean
	 * 
	 * @param type
	 *            要转化的类型
	 * @param map
	 *            包含属性值的 map
	 * @return 转化出来的 JavaBean 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InstantiationException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */

	@SuppressWarnings("rawtypes")
	public static Object formMapToBean(Class type, Map map) throws IntrospectionException, IllegalAccessException, InstantiationException,
			InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		Object obj = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();

			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				Object value = map.get(propertyName);

				Object[] args = new Object[1];
				args[0] = value;

				descriptor.getWriteMethod().invoke(obj, args);
			}
		}
		return obj;
	}

	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * 
	 * @param bean
	 *            要转化的JavaBean 对象
	 * @return 转化出来的 Map 对象
	 * @throws IntrospectionException
	 *             如果分析类属性失败
	 * @throws IllegalAccessException
	 *             如果实例化 JavaBean 失败
	 * @throws InvocationTargetException
	 *             如果调用属性的 setter 方法失败
	 */
	@SuppressWarnings("rawtypes")
	public static Map fromBeanToMap(Object bean) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

}

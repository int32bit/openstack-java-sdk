package bupt.openstack.common.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import bupt.openstack.common.model.AbstractEntity;

public class Reflector {
	private static final Logger logger = LogManager.getFormatterLogger(Reflector.class);
	/**
	 * 返回一个对象实例的所有属性
	 * @param obj 对象实例
	 * @return 对象实例属性的Set集合
	 */
	public static Set<Field> getFields(Object obj) {
		Set<Field> fields = new HashSet<>();
		Class<?> cl = obj.getClass();
		String objName = Object.class.getName();
		while (!cl.getName().equals(objName)) {
			fields.addAll(Arrays.asList(cl.getDeclaredFields()));
			cl = cl.getSuperclass();
		}
		return fields;
	}
	/**
	 * 返回一个对象实例的所有方法
	 * @param obj 对象实例
	 * @return 对象实例方法的Set集合
	 */
	public static Set<Method> getMethods(Object obj) {
		Set<Method> methods = new HashSet<>();
		methods.addAll(Arrays.asList(obj.getClass().getDeclaredMethods()));
		return methods;
	}
	/**
	 * 检测某个对象实例是否存在某方法
	 * @param obj 需要检测的实例对象
	 * @param name 需要检测的方法名
	 * @return 存在返回true，不存在返回false
	 */
	public static boolean hasMethod(Object obj, String name) {
		boolean ret = true;
		try {
			obj.getClass().getMethod(name, new Class[0]);
		} catch (NoSuchMethodException | SecurityException e) {
			ret = false;
		}
		return ret;
	}
	/**
	 * 返回实例对象指定属性名称的值
	 * @param obj 实例对象
	 * @param name 属性名
	 * @return 值
	 */
	public static Object getValue(Object obj, String name) {
		Object ret = null;
		if (!hasMethod(obj, name))
			return null;
		try {
			Method method = obj.getClass().getMethod(name, new Class[0]);
			ret = method.invoke(obj, new Object[0]);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return "NOT_EXIST_VALUE";
		}
		return ret;
	}
	/**
	 * 返回实例对象的所有属性名和属性值
	 * @param obj 实例对象
	 * @return 实例对象的属性名和属性值的Map
	 */
	public static Map<String, Object> getValues(Object obj) {
		Set<Field> fields = getFields(obj);
		Map<String, Object> values = new HashMap<>();
		for (Field field : fields) {
			String name = field.getName();
			field.setAccessible(true);
			try {
				Object value = field.get(obj);
				values.put(name, value);
			} catch (IllegalArgumentException | IllegalAccessException localIllegalArgumentException) {
			}
		}
		return values;
	}
	/**
	 * 利用发射机制，给某个实例的指定属性赋值
	 * @param obj 实例对象
	 * @param field 属性
	 * @param value 值
	 * @return 成功返回true，失败返回false
	 */
	public static boolean setValue(Object obj, Field field, Object value) {
		field.setAccessible(true);
		try {
			if (JSONObject.NULL.equals(value))
				field.set(obj, null);
			else {
				field.set(obj, value);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return false;
		}
		return true;
	}
	/**
	 * 根据类类型和json数据作为参数，生成一个新的Entity实例
	 * @param cl 类类型, 必须实现以json作为参数的构造器方法
	 * @param json 参数数据
	 * @return 生成的Entity实例
	 */
	public static <T extends AbstractEntity> T newEntity(Class<T> cl, JSONObject json) {
		T entity = null;
		//System.out.println("json=" + json);
		try {
			Constructor<T> constructor = cl.getConstructor(JSONObject.class);
			entity = constructor.newInstance(json);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
	public static Object newInstance(Class<?> cl, Object... arg) {
		Object obj = null;
		Constructor<?>[] constructors = cl.getConstructors();
		for (Constructor<?> constructor : constructors) {
			try {
				constructor.setAccessible(true);
				obj = constructor.newInstance(arg);
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (Exception e) {
				logger.error("Failed to create new Instance:%s\n",e.getLocalizedMessage());
			}
		}

		if (obj == null) {
			logger.warn("Failed to Create instance '" + cl.getSimpleName() + "'\n");
		}
		//logger.debug("Create a new Instance " + obj.getClass().getName());
		return obj;
	}
	/**
	 * 判断成类型c1是否类型c2的基类
	 * @param c1 类型1
	 * @param c2 类型2
	 * @return 如果c1是c2的基类，返回true，否则返回false.
	 */
	public static boolean isFather(Class<? extends Object> c1,
			Class<? extends Object> c2) {
		Class<? extends Object> c = c1;
		String objName = Object.class.getName();

		while ((c != null) && (!c.getName().equals(objName))) {
			if (c.getName().equals(c2.getName()))
				return true;
			c = c.getSuperclass();
		}
		return false;
	}
	/**
	 * 如果某个属性是数组，返回该数组的元素的类型
	 * @param field 属性
	 * @return 数组元素的类型, 如果获取失败，返回null
	 */
	public static Class<?> getGenericType(Field field) {
		Type type = field.getGenericType();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			Type[] actualTypes = paramType.getActualTypeArguments();
			if (actualTypes.length > 0)
				return (Class<?>)actualTypes[0];
		}
		return null;
	}
}

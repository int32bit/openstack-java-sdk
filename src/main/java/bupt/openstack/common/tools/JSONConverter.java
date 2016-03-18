package bupt.openstack.common.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bupt.openstack.common.annotation.AnnotationProcessor;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;

public class JSONConverter {
	/**
	 * 把一个AbstractEntity（它的之类）实例转化成JSONObject表示形式
	 * @param entity 需要转化的对象
	 * @param colon 属性名包含冒号时，是否分隔，true为分隔，false不分隔
	 * @return 该对象的JSONObject表示
	 */
	public static JSONObject EntityToJSON(AbstractEntity entity, boolean colon) {
		JSONObject json = new JSONObject();
		/* 获取该对象的所有属性 */
		Set<Field> fields = Reflector.getFields(entity);
		for (Field field : fields) {
			field.setAccessible(true);
			/* 获取该属性对应的名称，由Property注解设置 */
			String name = AnnotationProcessor.getFieldName(field);
			/* name为null，说明该属性不是Property，即没有被Property注解，直接忽略 */
			if (name == null)
				continue;
			/* 处理属性名为 DDD:ddd的情况 */
			if (name.indexOf(':') > 0 && colon) {
				name = name.split(":")[1];
			}
			Object value = null;
			/* 获取对象该属性的值 */
			try {
				value = field.get(entity);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			/* 处理value 为null的情况 */
			if (value == null && !json.has(name)) {
				json.put(name, (Object)null);
				continue;
			}
			/* 处理value是集合的情况 */
			if (value instanceof Collection) {
				json.put(name, new JSONArray(value.toString()));
				continue;
			}
			/* 处理value是AbstractEntity的情况，递归处理 */
			if (value instanceof AbstractEntity) {
				json.put(name, new JSONObject(value.toString()));
				continue;
			}
			json.put(name, value);
		}
		return json;
	}
	/**
	 * 把一个JSONObject对象转化成一个java对象
	 * @param json 需要转化的json对象
	 * @param cl 转化目标对象的类型。
	 * @return 转化后的java对象
	 */
	public static <T extends AbstractEntity> T jSONToEntity(JSONObject json, Class<T> cl) {
		return (T)Reflector.newEntity(cl, json);
	}
	/**
	 * 把从服务端请求过来的数据转化为对应的Entity实例对象
	 * @param body 接收请求的内容, 格式为{"what" : {"name": "value", "age", 20}}
	 * @param what 对象名字，如果为null，则使用默认的Entity注解的值
	 * @param objClass 对象类型
	 * @param region 对象所属的region
	 * @return 返回转化后的对象实例
	 */
	public static <T extends AbstractEntity> T responseToEntity(String body, String what, Class<T> objClass, String region) {
		//System.out.println(body);
		/* 如果what未设置，使用注解设置的值 */
		if (what == null) {
			what = AnnotationProcessor.getEntityName(objClass);
		}
		T e = null;
		/** 如果名称为""，则表示没有名称，比如{"name":"Mary", "age": 20} */
		if (what.equals("")) {
			e = jSONToEntity(new JSONObject(body), objClass);
		} else {
			e = jSONToEntity(new JSONObject(body).getJSONObject(what), objClass);
		}
		/* region需要单独设置 */
		e.setRegion(region);
		return e;
	}
	/**
	 * 把从服务端请求过来的数据转化为对应的Entity实例对象,并使用默认注解作为对象名称，相当于what为null
	 * @param body 接收请求的内容, 格式为{"what" : {"name": "value", "age", 20}}
	 * @param objClass 对象类型
	 * @param region 对象所属的region
	 * @return 返回转化后的对象实例
	 */
	public static <T extends AbstractEntity> T responseToEntity(String body, Class<T> objClass, String region) {
		return responseToEntity(body, null, objClass, region);
	}
	/**
	 * 把一个对象实例转化成JSONObject表示形式
	 * @param obj 需要转化的对象
	 * @return 该对象的JSONObject表示
	 */
	public static JSONObject objectToJSON(Object obj) {
		Set<Field> fields = Reflector.getFields(obj);
		JSONObject json = new JSONObject();

		for (Field field : fields) {
			/* 如果没有被标记为Property，则直接忽略*/
			if (!field.isAnnotationPresent(Property.class)) {
				continue;
			}
			field.setAccessible(true);
			String name = field.getName();
			if (name == null)
				continue;
			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
			/** value 为null的情况 **/
			if (value == null && !json.has(name)) {
				json.put(name, (Object)null);
				continue;
			}
			/* value 为集合 */
			if (value instanceof Collection) {
				json.put(name, new JSONArray(value.toString()));
				continue;
			}
			/* value 为Entity实例 */
			if (value instanceof AbstractEntity) {
				json.put(name, new JSONObject(value.toString()));
				continue;
			}
			json.put(name, value);
		}
		return json;
	}
	/**
	 * 把一个Map对象实例转化成json对象
	 * @param map 需要转化的对象
	 * @return 转化后的json对象
	 */
	public static JSONObject mapToJSON(Map<?, ?> map) {
		return new JSONObject(map);
	}
	/**
	 * 把json转化为map对象
	 * @param json 需要转化的对象
	 * @return 转化后的map对象
	 */
	@SuppressWarnings("unchecked")
	public static <V> Map<String, V> jsonToMap(JSONObject json) {
		Map<String, V> map = new HashMap<>();
		Iterator<?> iter = json.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			map.put(key.toString(), (V) json.opt(key.toString()));
		}
		return map;
	}
	/**
	 * 把从服务端请求过来的数据转化为对应的Entity列表
	 * @param body 接收请求的内容, 格式为{"objs" : [{"name": "value1", "age", 20}, {"name":"value2", "age":"18"}]}
	 * @param what 对象名字，如果为null，则使用默认的Entity注解的值的复数形式，比如Flavor -> flavors
	 * @param objClass 对象元素类型，比如Flavor.class
	 * @param region 对象所属的region, 比如regionOne
	 * @return 返回转化后的对象实例,比如List<Flavor>类型
	 */
	public static <T extends AbstractEntity> List<T> responseToEntityList(String body, String what, Class<T> objClass, String region) {
		if (what == null) {
			what = AnnotationProcessor.getEntityName(objClass) + 's';
		}
		JSONArray objs = new JSONObject(body).getJSONArray(what);
		List<T> entities = JSONArrayToEntityList(objs, objClass);
		for (T entity : entities) {
			entity.setRegion(region);
		}
		return entities;
	}
	/**
	 * 把从服务端请求过来的数据转化为对应的Entity列表， 对象集名称为Entity注解的复数形式
	 * @param body 接收请求的内容, 格式为{"objs" : [{"name": "value1", "age", 20}, {"name":"value2", "age":"18"}]}
	 * @param objClass 对象元素类型，比如Flavor.class
	 * @param region 对象所属的region, 比如regionOne
	 * @return 返回转化后的对象实例,比如List<Flavor>类型
	 */
	public static <T extends AbstractEntity> List<T> responseToEntityList(String body, Class<T>objClass, String region) {
		return responseToEntityList(body, null, objClass, region);
	}
	/**
	 * 把JSONArray数据转化为对应的Entity列表
	 * @param objs 待转化的数据, 格式为[{"name": "value1", "age", 20}, {"name":"value2", "age":"18"}]
	 * @param objClass 对象元素类型，比如Flavor.class
	 * @return 返回转化后的对象实例,比如List<Flavor>类型
	 */
	public static <T extends AbstractEntity> List<T> JSONArrayToEntityList(JSONArray objs, Class<T> objClass) {
		List<T> list = new ArrayList<>();
		if (objs == null)
			return list;
		for (int i = 0; i < objs.length(); ++i) {
			JSONObject item = objs.getJSONObject(i);
			String name = AnnotationProcessor.getEntityName(objClass);
			/* 除去实体名字，比如keypair，传过来是{"keypair":{"name":"lsdf", ...}}形式 */
			if (item.has(name)) {
				item = item.getJSONObject(name);
			}
			T e = Reflector.newEntity(objClass, item);
			list.add(e);
		}
		return list;
	}
}
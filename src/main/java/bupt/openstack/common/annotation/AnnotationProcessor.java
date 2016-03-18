package bupt.openstack.common.annotation;

import java.lang.reflect.Field;
public class AnnotationProcessor {
	public static String getEntityName(Class<?> cl) {
		if (cl.isAnnotationPresent(Entity.class)) {
			Entity entity = cl.getAnnotation(Entity.class);
			String value = entity.value();
			if (value.length() > 0) {
				return value;
			}
		}
		return cl.getSimpleName().toLowerCase();
	}
	public static String getFieldName(Field field) {
		if (field.isAnnotationPresent(Property.class)) {
			Property property = field.getAnnotation(Property.class);
			String value = property.value();
			if (value.length() > 0)
				return value;
			else
				return field.getName();
		}
		return null;
	}
	public static String getFieldName(Class<?>cl, String name) {
		assert(cl != null && name != null);
		Field field = null;
		Class<?> cur = cl;
		while (!cur.getName().equals(Object.class.getName())) {
			try {
				field = cur.getDeclaredField(name);
				break;
			} catch (NoSuchFieldException | SecurityException e) {
				cur = cur.getSuperclass();
			}
		}
		if (field == null)
			return null;
		return getFieldName(field);
	}
}

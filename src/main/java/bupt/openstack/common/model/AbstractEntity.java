package bupt.openstack.common.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bupt.openstack.common.annotation.AnnotationProcessor;
import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.tools.JSONConverter;
import bupt.openstack.common.tools.Reflector;
@Entity("entity")
public abstract class AbstractEntity implements Serializable,JSONAble {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4986537114902803512L;
	@Property
	protected String id;
	@Property
	protected String name;
	private String region;
	public AbstractEntity() {
		super();
	}
	public AbstractEntity(JSONObject entity) {
		Set<Field> fields = Reflector.getFields(this);
		for (Field field: fields) {
			field.setAccessible(true);
			String name = AnnotationProcessor.getFieldName(field);
			if (name == null || name.length() == 0 || !entity.has(name))
				continue;
			Object value = entity.get(name);
			Class<?> c = field.getType();
			boolean isEntity = Reflector.isFather(c, AbstractEntity.class);
			if (isEntity) {
				Object obj = Reflector.newInstance(c, value);
				if (obj != null) {
					Reflector.setValue(this, field, obj);
				}
				continue;
			}
			boolean isArray = entity.optJSONArray(name) != null;
			if (isArray) {
				Class<?> item = Reflector.getGenericType(field);
				assert(item != null);
				JSONArray array = entity.getJSONArray(name);
				List<Object> values = new ArrayList<>();
				for (int i = 0; i < array.length(); ++i) {
					try {
						Object newItem = item.getConstructor(String.class).newInstance(array.get(i).toString());
						values.add(newItem);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				Reflector.setValue(this, field, values);
				continue;
			}
			Reflector.setValue(this, field, value);
		}
	}

	public AbstractEntity(Object obj) {
		this(new JSONObject(obj.toString()));
	}
	public AbstractEntity(String s) {
		this(new JSONObject(s));
	}
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public JSONObject toIntervalJSONObject() {
		return JSONConverter.objectToJSON(this);
	}
	/*public String toIntervalString() {
		return toIntervalJSONObject().toString();
	}
	public String toIntervalString(int indent) {
		return toIntervalJSONObject().toString(4);
	}*/
	public JSONObject toJSONObject(boolean colonSeparate) {
		JSONObject meta = JSONConverter.EntityToJSON(this,colonSeparate);
		if (getClass().isMemberClass()) {
			return meta;
		}
		JSONObject entity = new JSONObject();
		String name = AnnotationProcessor.getEntityName(getClass());
		if (name == null || name.length() == 0) {
			name = getClass().getSimpleName().toLowerCase();
		}
		entity.put(name, meta);
		return entity;
	}
	@Override
	public JSONObject toJSONObject() {
		return toJSONObject(true);
	}
	@Override
	public String toString() {
		return toJSONObject().toString();
	}
	public String toString(int indent) {
		return toJSONObject().toString(4);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof AbstractEntity))
			return false;
		AbstractEntity o = (AbstractEntity) other;
		return this.id.equals(o.getId());
	}

	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		return super.hashCode();
	}

	public abstract boolean isValid();

	public static String toString(Collection<? extends AbstractEntity> entities,
			int indent) {
		JSONArray ans = new JSONArray();
		for (AbstractEntity e : entities) {
			ans.put(e.toJSONObject());
		}
		return ans.toString(indent);
	}
}

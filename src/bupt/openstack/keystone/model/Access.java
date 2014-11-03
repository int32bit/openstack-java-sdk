package bupt.openstack.keystone.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bupt.openstack.authentication.Authenticated;
import bupt.openstack.common.annotation.Entity;
import bupt.openstack.common.annotation.Property;
import bupt.openstack.common.model.AbstractEntity;
import bupt.openstack.common.tools.StringUtils;
@Entity("access")
public class Access extends AbstractEntity implements Authenticated {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5310000130056732695L;
	@Property
	private Token token;
	@Property("serviceCatalog")
	private Map<String, Catalog> catalogs = new HashMap<>();
	@Property
	private User user;
	@Property
	private List<String> roles;
	@Property
	private JSONObject metadata;
	private String workRegion;
	public Access(JSONObject json) {
		/** Process token **/
		if (!json.has("token")) {
			throw new RuntimeException("Failed to get token from access!");
		}
		this.token = new Token(json.getJSONObject("token"));
		/** process user **/
		JSONObject _user = json.getJSONObject("user");
		user = new User();
		user.setId(_user.getString("id"));
		user.setName(_user.getString("username"));
		/** process service Catalog **/
		JSONArray cs = json.getJSONArray("serviceCatalog");
		if (cs == null) {
			throw new RuntimeException("Failed to get service catalog from access");
		}
		for (int i = 0; i < cs.length(); ++i) {
			JSONObject c = cs.getJSONObject(i);
			String type = c.getString("type");
			JSONArray es = c.getJSONArray("endpoints");
			if (es != null) {
				for (int j = 0; j < es.length(); ++j) {
					JSONObject e = es.getJSONObject(j);
					String region = e.getString("region");
					String url = e.getString("adminURL");
					Catalog catalog = catalogs.get(region);
					if (catalog == null) {
						catalog = new Catalog(region);
						catalogs.put(region, catalog);
						}
					CatalogType catalogType = CatalogType.of(type);
					catalog.addEndpoint(catalogType, url);
				}
			}		
		}
		/** Process Role **/
		JSONArray _roles = json.getJSONObject("metadata").getJSONArray("roles");
		roles = new ArrayList<>();
		for (int i = 0; i < _roles.length(); ++i) {
			roles.add(_roles.getString(i));
		}
		/** Process metadata **/
		this.metadata = json.getJSONObject("metadata");
	}
	@Override
	public String getTokenId() {
		return token.getId();
	}
	@Override
	public User getUser() {
		return user;
	}
	@Override
	public Tenant getTenant() {
		return token.getTenant();
	}
	@Override
	public String getEndpoint(CatalogType type) {
		String region = getWorkRegion();
		return catalogs.get(region).getURL(type);
	}
	@Override
	public String getWorkRegion() {
		if (workRegion == null && catalogs.size() > 0) {
			workRegion = catalogs.keySet().toArray()[0].toString();
		}
		return workRegion;
	}
	@Override
	public void setWorkRegion(String region) {
		if (!catalogs.containsKey(region)) {
			throw new RuntimeException("The target region '" + region + "' Not Found!");
		}
		this.workRegion = region;
	}
	@Override
	public Set<String> getRegions() {
		return catalogs.keySet();
	}
	public JSONObject getMetadata() {
		return this.metadata;
	}
	@Override
	public boolean isValid() {
		boolean hasToken = (token != null && StringUtils.isNotBlank(token.getId()));
		boolean hasRegion = (getWorkRegion() != null);
		boolean hasCatalog = (catalogs != null && catalogs.size() > 0);
		return hasToken && hasRegion && hasCatalog;
	}
}

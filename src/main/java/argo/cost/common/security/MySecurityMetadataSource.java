package argo.cost.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import argo.cost.common.dao.ResourcesDao;
import argo.cost.common.model.entity.Resources;

/**
 * <p>
 * 資源と権限の対応関係。
 * </p>
 * 
 * @author COST argo Corporation.
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	@Autowired
	private ResourcesDao resourcesDao;

	public MySecurityMetadataSource(ResourcesDao resourcesDao) {
		this.resourcesDao = resourcesDao;
		loadResourceDefine();
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	// 全ての権限を注入する
	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resources> resources = this.resourcesDao.findAll();
			for (Resources resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
				ConfigAttribute configAttribute = new SecurityConfig(
						resource.getName());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}

		Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap
				.entrySet();
		Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet
				.iterator();

	}

	// 求めるの資源は必要の権限を
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);
		if (resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}

}
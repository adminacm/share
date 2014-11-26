package argo.cost.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.ResourcesDao;
import argo.cost.common.entity.Resources;
import argo.cost.common.entity.Roles;

/**
 * <p>
 * 資源と権限の対応関係。
 * </p>
 * 
 * @author COST argo Corporation.
 */
public class MySecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	/**
	 * 単一テーブル操作DAO
	 */
	private BaseDao baseDao;
	private ResourcesDao resourcesDao;

	public MySecurityMetadataSource(ResourcesDao resourcesDao, BaseDao baseDao) {
		this.resourcesDao = resourcesDao;
		this.baseDao = baseDao;
		loadResourceDefine();
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private AntPathMatcher urlMatcher = new AntPathMatcher();

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		System.out.println("MySecurityMetadataSource.supports()---------------------");
		return true;
	}

	/**
	 * 全ての権限を注入する
	 * 
	 */
	private void loadResourceDefine() {
		resourceMap = new HashMap<String,Collection<ConfigAttribute>>();
		System.out.println("MySecurityMetadataSource.loadResourcesDefine()--------------リソースデータロード開始--------");
		List<Roles> roles = baseDao.findAll(Roles.class);
		for (Roles role : roles) {
			// 権限名よりリソース情報を取得
			List<Resources> resources = resourcesDao.findByName(role.getName());
			for (Resources resource : resources) {
				Collection<ConfigAttribute> configAttributes = null;
				ConfigAttribute configAttribute = new SecurityConfig(role.getName());
				if(resourceMap.containsKey(resource.getUrl())){
					configAttributes = resourceMap.get(resource.getUrl());
					configAttributes.add(configAttribute);
				}else{
					configAttributes = new ArrayList<ConfigAttribute>() ;
					configAttributes.add(configAttribute);
					resourceMap.put(resource.getUrl(), configAttributes);
				}
			}
		}
	}

	/**
	 * 求めるの資源は必要の権限
	 * 
	 * @param object
	 * 			オブジェクト情報
	 * 
	 * @throw IllegalArgumentException
	 */
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {

		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		System.out.println("requestUrl is " + requestUrl);
		Iterator<String> it = resourceMap.keySet().iterator();
		while (it.hasNext()) {
			String _url = it.next();
			if (_url.indexOf("?")!=-1) {
				_url = _url.substring(0, _url.indexOf("?"));
			}
			if(urlMatcher.match(requestUrl, _url))
				return resourceMap.get(_url);
		}
		return null;
	}

}
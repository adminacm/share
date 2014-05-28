package argo.cost.common.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.BaseCondition;
import argo.cost.common.dao.BaseDao;
import argo.cost.common.dao.UsersDao;
import argo.cost.common.entity.Roles;
import argo.cost.common.entity.Users;
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {  
    
	// ############
	// ### 定数 ###
	// ############
	/**
	 * 検索条件：名
	 */
	private static final String LOGIN_MAIL = "loginMailAddress";
	
	/**
	 * 検索条件：ID
	 */
	private static final String ID = "id";
	
	/**
	 * 単一テーブル操作DAO
	 */
	@Autowired
	private BaseDao baseDao;
	/**
	 * ユーザー情報DAO
	 */
	@Autowired
    private UsersDao usersDao;

	public UserDetails loadUserByUsername(String loginMail) throws UsernameNotFoundException {  
        System.out.println("username is " + loginMail);  

        // 共通の検索条件クラス
        BaseCondition condition = new BaseCondition();
        
        condition.addConditionEqual(LOGIN_MAIL, loginMail);
        
        // ユーザ名より、ユーザ情報を取得する。
        Users users = baseDao.findSingleResult(condition, Users.class);
        if(users == null) {
            throw new UsernameNotFoundException("ユーザが存在しない");  
        }  
        Set<GrantedAuthority> authSets = new HashSet<GrantedAuthority>();
        // 共通の検索条件クラス
        condition = new BaseCondition();
        
        condition.addConditionEqual(ID, users.getId());
        
        // ユーザIDより、権限情報を取得する。
        List<Roles> roles = usersDao.findRoles(loginMail);
        for (Roles role : roles) {
        	GrantedAuthority ga = new SimpleGrantedAuthority(role.getName());
        	authSets.add(ga);	
        }
        
        boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;  
        
        User userdetail = new User(users.getLoginMailAddress(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, authSets);
        return userdetail;  
    }  
      
}  

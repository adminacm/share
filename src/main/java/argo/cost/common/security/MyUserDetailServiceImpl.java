package argo.cost.common.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import argo.cost.common.dao.ResourcesDao;
import argo.cost.common.dao.UsersDao;
import argo.cost.common.model.entity.Resources;
import argo.cost.common.model.entity.Roles;
import argo.cost.common.model.entity.Users;
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {  
    
	@Autowired
    private UsersDao usersDao;

	@Autowired
	private ResourcesDao resourcesDao;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {  
        System.out.println("username is " + username);  
        Users users = this.usersDao.findByName(username);  
        if(users == null) {  
            throw new UsernameNotFoundException("ユーザが存在しない");  
        }  
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);  
          
        boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;  
        
        User userdetail = new User(users.getName(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
        return userdetail;  
    }  
      
    // ユーザの権限を取得する
    private Set<GrantedAuthority> obtionGrantedAuthorities(Users user) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
        Set<Roles> roles = this.usersDao.findRoles(user.getId());
          
        for(Roles role : roles) {  
            Set<Resources> tempRes = this.resourcesDao.findById(role.getId());
            for(Resources res : tempRes) {  
                authSet.add(new GrantedAuthorityImpl(res.getName()));  
           }  
        }  
        return authSet;  
    }  
}  

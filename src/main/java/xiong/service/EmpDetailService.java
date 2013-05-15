package xiong.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import xiong.dao.IEmployeeDao;
import xiong.domain.Authority;
import xiong.domain.Employee;
import xiong.domain.Role;

@Service
public class EmpDetailService implements UserDetailsService {
	@Resource
	private IEmployeeDao employeeDaoImpl;

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Employee employee = employeeDaoImpl.findByName(username);
		UserDetails user = null;
		if (employee != null) {
			user = new User(username, employee.getPassword(), true, true, true,
					true, findUserAuthorities(employee));
		}

		return user;
	}

	/**
	 * 获取用户的权限
	 * 
	 * @param user
	 * @return
	 */
	public Collection<SimpleGrantedAuthority> findUserAuthorities(
			Employee employee) {
		List<SimpleGrantedAuthority> autthorities = new ArrayList<SimpleGrantedAuthority>();
		for (Role role : employee.getRoles()) {
			for (Authority authority : role.getAuths()) {
				autthorities.add(new SimpleGrantedAuthority(authority
						.getAuthName()));
			}
		}
		return autthorities;
	}
}

package havespring;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import xiong.domain.Authority;
import xiong.domain.Role;
import xiong.service.IAuthorityService;
import xiong.service.IRoleService;
import xiong.util.PageBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class RoleTest {

	
	@BeforeClass
	public static void init() {
		PropertyConfigurator
				.configure("src/main/resources/log4j/log4j.properties");
	}
	@Resource
	private IAuthorityService authorityServiceImpl;
	
	@Resource
	private  IRoleService roleServiceImpl ;

	@Test
	public void run() {
	}

	@Test
	public void add() {
		Role entity = new Role("COMM");
		roleServiceImpl.add(entity);
	}
	@Test
	public void rmove() {
		Long id = roleServiceImpl.getByRoleName("COMM").getId();
		roleServiceImpl.delById(id) ;
	}
	
	@Test
	public void addAll() {
		Set<Authority> entitys = new HashSet<Authority>();
		for (int i = 0; i < 40; i++) {
			Authority entity = new Authority();
			entitys.add(entity);
		}
		authorityServiceImpl.addAll(entitys);
	}

	@Test
	public void list() {
		PageBean<Authority> result = authorityServiceImpl.listByStart(-1, 10,
				null, null);
		System.out.println(result.getTotal());
	}

	@Test
	public void setAuth() {
		Role role = roleServiceImpl.getByRoleName("COMM") ;
		Authority auth=authorityServiceImpl.getAuthByAuthName("COMM");
		role.setAuth(auth) ;
	}
	@Test
	public void size() {
		Role role = roleServiceImpl.getByRoleName("COMM") ;
		System.out.println(role.getAuths().size());
	}
	
	@Test
	public void listAll(){
		List<Role> roles = roleServiceImpl.list() ;
		System.out.println(roles.size());
	}

	
}

package havespring;

import java.util.HashSet;
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
public class AuthorityTest {
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
		Role r = roleServiceImpl.getByRoleName("COMM")  ;
		Authority entity = new Authority("COMM");
		r.setAuth(entity) ;
		System.out.println(authorityServiceImpl.add(entity));;
	}
	@Test
	public void del() {
		Integer id=1;
		authorityServiceImpl.delById(id) ;
	}
	
	@Test
	public void addAll() {
		Set<Authority> entitys = new HashSet<Authority>();
		for (int i = 0; i < 40; i++) {
			Authority entity = new Authority();
			entitys.add(entity);
		}
		authorityServiceImpl.addAll(entitys) ;
	}

	@Test
	public void list() {
		PageBean<Authority> result = authorityServiceImpl.listByStart(-1, 10,
				null, null);
		System.out.println(result.getTotal());
		System.out.println(result.getData().get(0).getId());
	}

	@Test
	public void findByName() {
	}

}

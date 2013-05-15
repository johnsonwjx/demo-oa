package havespring;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
import xiong.domain.Employee;
import xiong.domain.Role;
import xiong.service.IAuthorityService;
import xiong.service.IEmployeeService;
import xiong.service.IRoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class Innit {
	@BeforeClass
	public static void init() {
		PropertyConfigurator
				.configure("src/main/resources/log4j/log4j.properties");
	}

	@Resource
	private IAuthorityService authorityServiceImpl;
	@Resource
	private IRoleService roleServiceImpl;

	@Resource
	private IEmployeeService employeeServiceImpl ;
	
	@Test
	public void run() {
		this.add();
	}
	@Test
	public void createTable() {
		System.out.println(employeeServiceImpl);
	}
	public void add() {
		Map<String, String> auths = new LinkedHashMap<String, String>();
		auths.put("ROLE_OPER", "对角色的管理权限");
		auths.put("EMP_OPER","对员工的管理权限");
		auths.put("COMM", "普通用户权限");
		auths.put("DEPT_OPER", "部门管理权限");
		Set<Authority> entitys = new LinkedHashSet<Authority>();
		for (Map.Entry<String, String> a : auths.entrySet()) {
			Authority entity = new Authority(a.getKey());
			entity.setAuthDesc(a.getValue());
			entitys.add(entity);
		}
		authorityServiceImpl.addAll(entitys);
		
		Role role = new Role("系统管理员") ;
		role.setAuths(new HashSet<Authority>(authorityServiceImpl.list()));
		roleServiceImpl.add(role);
		
		Employee emp=new Employee() ;
		emp.setRole(roleServiceImpl.getByRoleName("系统管理员"));
		emp.setUsername("admin");
		emp.setPassword("admin");
		employeeServiceImpl.add(emp);
	}

	public void del() {
		Integer id = 1;
		authorityServiceImpl.delById(id);
	}

	public void clearAll() {
		authorityServiceImpl.clearAll();
	}

}

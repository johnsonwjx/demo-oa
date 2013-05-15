package havespring;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import xiong.dao.IEmployeeDao;
import xiong.domain.Employee;
import xiong.domain.Gender;
import xiong.domain.Role;
import xiong.service.IEmployeeService;
import xiong.service.IRoleService;
import xiong.util.PageBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class UserTest {
	@BeforeClass
	public static void init() {
		PropertyConfigurator
				.configure("src/main/resources/log4j/log4j.properties");
	}

	@Resource
	private IEmployeeService employeeServiceImpl;
	@Resource
	private  IRoleService roleServiceImpl ;
	@Resource
	private IEmployeeDao employeeDaoImpl;

	@Test
	public void run() {
	}

	@Test
	public void findExit() {
		System.out.println(employeeServiceImpl.findExit("aa"));
		System.out.println(employeeServiceImpl.findExit("admin"));
	}

	@Test
	public void add() {
		Employee entity = new Employee();
		entity.setUsername("xiong");
		entity.setPassword("xiong");
		entity.setGender(Gender.Female);
		employeeServiceImpl.add(entity);
	}

	@Test
	public void addAll() {
		List<Employee> entitys = new ArrayList<Employee>();
		for (int i = 0; i < 40; i++) {
			Employee entity = new Employee();
			entity.setUsername("xiong");
			entity.setPassword("1234");
			entity.setGender(Gender.Female);
			entitys.add(entity);
		}
		employeeServiceImpl.addAll(entitys);
	}

	@Test
	public void list() {
		PageBean<Employee> result = employeeServiceImpl.listByStart(-1, 10,
				null, null);
		System.out.println(result.getTotal());
	}

	@Test
	public void addRole() {
		Employee employee = employeeDaoImpl.findByName("xiong");
		Role role=roleServiceImpl.getByRoleName("COMM");
		employee.setRole(role);
	}


	@Test
	public void findByName() {
		Employee employee = employeeDaoImpl.findByName("admin");
		System.out.println(employee.isDie());
	}
	@Test
	public void query() {
		PageBean<Employee> result = employeeServiceImpl.listByStart(-1, 10,
				Restrictions.eq("department.id", 6), null);
		System.out.println(result.getTotal());
	}
}

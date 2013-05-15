package xiong.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.PropertyConfigurator;
import org.hibernate.criterion.Criterion;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import xiong.domain.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-base.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class DepartmentServiceImplTest {
	@Resource
	private IDepartmentService departmentServiceImpl;

	@BeforeClass
	public static void init() {
		PropertyConfigurator
				.configure("src/main/resources/log4j/log4j.properties");
	}

	@Test
	public void test() {
		// Department parent = new Department("dfd", "dfd");
		Department parent = departmentServiceImpl.getById(1);
		Department c1 = new Department("12121", "121211");
		parent.setChild(c1);
		departmentServiceImpl.update(parent);
	}

	@Test
	public void listTopTest() {
		System.out.println(	departmentServiceImpl.listAllTopNode(null, null).size());
	}

	@Test
	public void deleteTest() {
		departmentServiceImpl.delById(20);
	}
	@Test
	public void findByExample() {
		Department emample=new Department("3",null);
		System.out.println(departmentServiceImpl.queryByExample(emample, null));
	}
}

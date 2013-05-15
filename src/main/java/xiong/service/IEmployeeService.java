package xiong.service;

import org.hibernate.criterion.Order;

import xiong.domain.Employee;
import xiong.util.PageBean;

public interface IEmployeeService extends IDefaultService<Employee> {
	boolean findExit(String username);

	public PageBean<Employee> query(int start, int pageSize, String username,
			String realname, Float gtSalary, Integer gtAge, Long deptId,
			Boolean die, Order order);
}

package xiong.dao;

import xiong.domain.Employee;

public interface IEmployeeDao extends IBaseDao<Employee> {
	Employee findByNameAndPwd(String username, String password);

	Employee findByName(String username);
}

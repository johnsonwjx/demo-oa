package xiong.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import xiong.domain.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDaoImpl<Employee> implements
		IEmployeeDao {

	public Employee findByName(String username) {
		return findEntityCriterions(Restrictions.eq("username", username));
	}

	public Employee findByNameAndPwd(String username, String password) {
		Map<String, String> propertyNameValues = new HashMap<String, String>();
		propertyNameValues.put("username", username);
		propertyNameValues.put("password", password);
		return findEntityCriterions(Restrictions.allEq(propertyNameValues));
	}

}

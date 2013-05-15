package xiong.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import xiong.dao.IEmployeeDao;
import xiong.domain.Employee;
import xiong.util.PageBean;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	@Resource
	private IEmployeeDao employeeDaoImpl;

	public Serializable add(Employee entity) {
		return employeeDaoImpl.add(entity);
	}

	public void update(Employee entity) {
		employeeDaoImpl.update(entity);
	}

	public void delById(Serializable id) {
		employeeDaoImpl.delById(id);
	}

	public Employee getById(Serializable id) {
		return employeeDaoImpl.getById(id);
	}

	public void addAll(Collection<Employee> entitys) {
		employeeDaoImpl.addAll(entitys);
	}

	public void updateAll(Collection<Employee> entitys) {
		employeeDaoImpl.updateAll(entitys);
	}

	public PageBean<Employee> listByStart(int start, int pageSize,
			Criterion criterion, Order order) {
		return employeeDaoImpl.listByStart(start, pageSize, criterion, order);
	}

	public void delAll(Serializable[] ids) {
		employeeDaoImpl.delAll(ids);
	}

	public void saveOrUpdateAll(Collection<Employee> entitys) {
		employeeDaoImpl.saveOrUpdateAll(entitys);
	}

	public boolean findExit(String username) {
		return employeeDaoImpl.findByName(username) != null;
	}

	public List<Employee> queryByExample(Employee emample, String[] ingorePros) {
		return employeeDaoImpl.queryByExample(emample, ingorePros);
	}

	// 'username':username,
	// 'realname':realname,
	// 'gtSalary':gtSalary,
	// 'die':die,
	// 'gtAge':gtAge,
	// 'deptId':deptId
	public PageBean<Employee> query(int start, int pageSize, String username,
			String realname, Float gtSalary, Integer gtAge, Long deptId,
			Boolean die, Order order) {
		Conjunction c = new Conjunction();
		if (username != null)
			c.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
		if (deptId != null && deptId > 0)
			c.add(Restrictions.eq("department.id", deptId));
		if (gtAge != null && gtAge > 0 && gtAge < 100)
			c.add(Restrictions.gt("age", gtAge));
		if (gtSalary != null && gtSalary > 1000)
			c.add(Restrictions.gt("salary", gtSalary));
		if(die!=null)
			c.add(Restrictions.eq("die", die));
		return employeeDaoImpl.listByStart(start, pageSize, c, order);
	}

}

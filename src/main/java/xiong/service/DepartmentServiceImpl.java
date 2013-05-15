package xiong.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import xiong.dao.IDepartmentDao;
import xiong.domain.Department;
import xiong.util.PageBean;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	@Resource
	private IDepartmentDao departmentDaoImpl;

	public Serializable add(Department entity) {
		return departmentDaoImpl.add(entity);
	}

	public void update(Department entity) {
		departmentDaoImpl.update(entity);
	}

	public void delById(Serializable id) {
		departmentDaoImpl.delById(id);
	}

	public Department getById(Serializable id) {
		return departmentDaoImpl.getById(id);
	}

	public void addAll(Collection<Department> entitys) {
		departmentDaoImpl.addAll(entitys);
	}

	public void updateAll(Collection<Department> entitys) {
		departmentDaoImpl.updateAll(entitys);
	}

	public PageBean<Department> listByStart(int start, int pageSize,
			Criterion criterion, Order order) {
		return departmentDaoImpl.listByStart(start, pageSize, criterion, order);
	}

	public void delAll(Serializable[] ids) {
		departmentDaoImpl.delAll(ids);
	}

	public void saveOrUpdateAll(Collection<Department> entitys) {
		departmentDaoImpl.saveOrUpdateAll(entitys);
	}

	public List<Department> listAllTopNode(Criterion criterion, Order order) {
		return departmentDaoImpl.list(Restrictions.isNull("parent"), order);
	}

	public List<Department> queryByExample(Department emample,
			String[] ingorePros) {
		return departmentDaoImpl.queryByExample(emample, ingorePros);
	}

}

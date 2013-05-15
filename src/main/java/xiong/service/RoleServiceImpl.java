package xiong.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import xiong.dao.IRoleDao;
import xiong.domain.Role;
import xiong.util.PageBean;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-4
 */
@Service
public class RoleServiceImpl implements IRoleService {
	@Resource
	private IRoleDao roleDaoImpl;

	public Serializable add(Role entity) {
		return roleDaoImpl.add(entity);
	}

	public void addAll(Collection<Role> entitys) {
		roleDaoImpl.addAll(entitys);
	}

	public void update(Role entity) {
		roleDaoImpl.update(entity);
	}

	public void updateAll(Collection<Role> entitys) {
		roleDaoImpl.updateAll(entitys);
	}

	public void delById(Serializable id) {
		roleDaoImpl.delById(id);
	}

	public void delAll(Serializable[] ids) {
		roleDaoImpl.delAll(ids);
	}

	public void saveOrUpdateAll(Collection<Role> entitys) {
		roleDaoImpl.saveOrUpdateAll(entitys);
	}

	public Role getById(Serializable id) {
		return roleDaoImpl.getById(id);
	}

	public PageBean<Role> listByStart(int start, int pageSize,
			Criterion criterion, Order order) {
		return roleDaoImpl.listByStart(start, pageSize, criterion, order);
	}

	public Role getByRoleName(String roleName) {
		return roleDaoImpl.findEntityCriterions(Restrictions.eq("roleName", roleName));
	}

	public List<Role> list() {
		return roleDaoImpl.list(null, null);
	}

	public List<Role> queryByExample(Role emample, String[] ingorePros) {
		return roleDaoImpl.queryByExample(emample, ingorePros);
	}

}

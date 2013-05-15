package xiong.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import xiong.dao.IAuthorityDao;
import xiong.domain.Authority;
import xiong.util.PageBean;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-4
 */
@Service
public class AuthorityServiceImpl implements IAuthorityService {
	@Resource
	private IAuthorityDao authorityDaoImpl;

	public Serializable add(Authority entity) {
		return authorityDaoImpl.add(entity);
	}

	public void addAll(Collection<Authority> entitys) {
		authorityDaoImpl.addAll(entitys);
	}

	public void update(Authority entity) {
		authorityDaoImpl.update(entity);
	}

	public void updateAll(Collection<Authority> entitys) {
		authorityDaoImpl.updateAll(entitys);
	}

	public void delById(Serializable id) {
		authorityDaoImpl.delById(id);
	}

	public void delAll(Serializable[] ids) {
		authorityDaoImpl.delAll(ids);
	}

	public void saveOrUpdateAll(Collection<Authority> entitys) {
		authorityDaoImpl.saveOrUpdateAll(entitys);
	}

	public Authority getById(Serializable id) {
		return authorityDaoImpl.getById(id);
	}

	public PageBean<Authority> listByStart(int start, int pageSize,
			Criterion criterion, Order order) {
		return authorityDaoImpl.listByStart(start, pageSize, criterion, order);
	}

	public Authority getAuthByAuthName(String authName) {
		return authorityDaoImpl.findEntityCriterions(Restrictions.eq(
				"authName", authName));
	}

	public void clearAll() {
		authorityDaoImpl.clearAll();
	}

	public List<Authority> list() {
		return authorityDaoImpl.list(null, null);
	}

	public List<Authority> queryByExample(Authority emample, String[] ingorePros) {
		return authorityDaoImpl.queryByExample(emample, ingorePros);
	}

}

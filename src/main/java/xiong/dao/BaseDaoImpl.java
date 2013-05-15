package xiong.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

import xiong.domain.Domain;
import xiong.util.PageBean;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-2
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<M extends Domain<Serializable>> implements IBaseDao<M> {
	protected final Class<M> entityClass;
	@Resource
	private SessionFactory sessionFactory;

	public BaseDaoImpl() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).//
				getActualTypeArguments()[0];
	}

	public Serializable add(M entity) {
		return getSession().save(entity);
	}

	public void addAll(Collection<M> entitys) {
		Session session = getSession();
		Iterator<M> iter = null;
		int index = 1;
		for (iter = entitys.iterator(); iter.hasNext(); index++) {
			add(iter.next());
			if (index % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	/**
	 * 调用链，不用了，hql就用 添加查询条件，排序
	 * 
	 * @param criteria
	 * @param criterions
	 * @param orders
	 */
	// private void buildCriteria(final Criteria criteria,
	// final List<Criterion> criterions, final List<Order> orders) {
	// if (criterions != null && criterions.size() > 0)
	// for (Criterion criterion : criterions)
	// criteria.add(criterion);
	// if (orders != null && orders.size() > 0)
	// for (Order order : orders)
	// criteria.addOrder(order);
	//
	// }

	public void del(final M entity) {
		getSession().delete(entity);
	}

	public void delAll(final Serializable[] ids) {
		Session session = getSession();
		for (int i = 0; i < ids.length; i++) {
			delById(ids[i]);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void delById(final Serializable id) {
		Session session = getSession();
		session.delete(session.load(entityClass, id));
	}

	public M getById(final Serializable id) {
		return (M) getSession().get(entityClass, id);
	}

	public int getCount(final Criterion criterion) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (criterion != null)
			criteria.add(criterion);
		// buildCriteria(criteria, criterions, null);
		return ((Long) criteria.//
				setProjection(Projections.rowCount()).uniqueResult())
				.intValue();
	}

	protected Session getSession() {
		// 事务必须是开启的，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	public List<M> list(final Criterion criterion, final Order order) {
		Criteria criteria = getSession().createCriteria(entityClass)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		if (criterion != null)
			criteria.add(criterion);
		if (order != null)
			criteria.addOrder(order);
		return criteria.list();
	}

	public List<M> queryByExample(M emample, String[] ingorePros) {
		Criteria criteria = getSession().createCriteria(entityClass)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		Example eObj = Example.create(emample).excludeZeroes()
				.enableLike(MatchMode.ANYWHERE);
		if (ingorePros != null && ingorePros.length > 0) {
			for (String p : ingorePros) {
				eObj.excludeProperty(p);
			}
		}
		return criteria.add(eObj).list();
	}

	public PageBean<M> listByStart(int start, int pageSize,
			final Criterion criterion, final Order order) {
		int total = getCount(criterion);
		PageBean<M> pageBean = new PageBean<M>(total, start, pageSize);
		if (pageBean.getTotal() > 0) {
			Criteria criteria = getSession().createCriteria(entityClass)
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if (criterion != null)
				criteria.add(criterion);
			if (order != null)
				criteria.addOrder(order);
			List<M> data = criteria.setFirstResult(start)
					.setMaxResults(pageSize).list();
			pageBean.setData(data);
		}
		return pageBean;
	}

	public void update(final M entity) {
		getSession().update(entity);
	}

	public void updateAll(final Collection<M> entitys) {
		Session session = getSession();
		Iterator<M> iter = null;
		int index = 1;
		for (iter = entitys.iterator(); iter.hasNext(); index++) {
			update(iter.next());
			if (index % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public void saveOrUpdate(final M entity) {
		getSession().saveOrUpdate(entity);
	}

	public void saveOrUpdateAll(final Collection<M> entitys) {
		Session session = getSession();
		Iterator<M> iter = null;
		int index = 1;
		for (iter = entitys.iterator(); iter.hasNext(); index++) {
			saveOrUpdate(iter.next());
			if (index % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	public M findEntityCriterions(final Criterion criterion) {
		List<M> entitys = list(criterion, null);
		return entitys.size() > 0 ? entitys.get(0) : null;
	}

	private String getTableName() {
		String tableName = null;
		Table table = this.entityClass.getAnnotation(Table.class);
		if (table != null && table.name() != null) {
			tableName = table.name();
		} else
			tableName = this.entityClass.getSimpleName();
		return tableName;
	}

	public void clearAll() {
		Session session = getSession();
		String sql = "DELETE FROM  " + getTableName() + " WHERE 1=1 ";
		System.out.println(sql);
		session.createSQLQuery(sql).executeUpdate();
	}

}

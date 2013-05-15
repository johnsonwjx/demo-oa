package xiong.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import xiong.domain.Domain;
import xiong.util.PageBean;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-2
 */
public interface IBaseDao<M extends Domain<Serializable>> {
	int getCount(final Criterion criterion);

	Serializable add(final M entity);

	void update(final M entity);

	void del(final M entity);

	void delById(final Serializable id);

	void delAll(final Serializable[] ids);

	M getById(final Serializable id);

	//因为Criterion，order为调用链，所以不用list了，直接来
//	M findEntityCriterions(final List<Criterion> criterions);
	M findEntityCriterions(final Criterion criterion) ;
	void addAll(final Collection<M> entitys);

	void updateAll(final Collection<M> entitys);

	List<M> list(Criterion criterion, final Order order);

	void saveOrUpdateAll(final Collection<M> entitys);

	void saveOrUpdate(final M entity);

	void clearAll();

	List<M> queryByExample(M emample, String[] ingorePros);

	PageBean<M> listByStart(int start, int pageSize,
			final Criterion criterion, final Order order);
}

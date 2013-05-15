package xiong.service;

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
 * @time 2013 2013-5-4
 * @param <M>
 */
public interface IDefaultService<M extends Domain<Serializable>> {
	Serializable add(final M entity);

	void addAll(final Collection<M> entitys);

	void update(final M entity);

	void updateAll(final Collection<M> entitys);

	void delById(final Serializable id);

	void delAll(final Serializable[] ids);

	void saveOrUpdateAll(final Collection<M> entitys);

	M getById(final Serializable id);

	/**
	 * 自定义查询
	 * 
	 * @param emample
	 * @param ingorePros
	 * @return
	 */
	List<M> queryByExample(M emample, String[] ingorePros);

	PageBean<M> listByStart(int start, int pageSize, final Criterion criterion,
			final Order order);
}

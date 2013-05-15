package xiong.web.action;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Domain;
import xiong.domain.web.WebColumn;
import xiong.service.IDefaultService;
import xiong.util.EntityUtil;
import xiong.util.PageBean;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-7
 * @param <M>
 */
@SuppressWarnings("unchecked")
public abstract class AbsBaseAction<M extends Domain<Serializable>> implements
		IBaseAction<M> {
	protected static final Logger LOGGER = Logger
			.getLogger(AbsBaseAction.class);
	protected final Class<M> entityClass;

	public AbsBaseAction() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).//
				getActualTypeArguments()[0];
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public PageBean<M> list(@RequestParam int start, @RequestParam int limit)
			throws ServletException {
		return getServiceHandler().listByStart(start, limit, null, null);
	}
	

	public abstract IDefaultService<M> getServiceHandler();

	@RequestMapping(value = "/getGridColumns", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<WebColumn> getGridColumns() throws ServletException {
		return EntityUtil.getGridColumn(entityClass);

	}

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public M get(@PathVariable Long id) throws ServletException {
		if (id == null)
			return null;
		return getServiceHandler().getById(id);
	}

	@RequestMapping(value = "/manager/update", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@RequestBody Collection<M> entitys)
			throws ServletException {
		getServiceHandler().updateAll(entitys);
		return "更新成功";
	}

	@RequestMapping(value = "/manager/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody Collection<M> entitys)
			throws ServletException {
		getServiceHandler().addAll(entitys);
		return "添加成功";
	}

	@RequestMapping(value = "/manager/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@RequestBody Long[] ids)
			throws ServletException {
		if (ids == null || ids.length <= 0)
			return "删除失败";
		getServiceHandler().delAll(ids);
		return "删除成功";
	}

	@ExceptionHandler({ Exception.class })
	public @ResponseBody
	String exception(Exception e) {
		System.out.println("========= 错误日志记录  =========");
		System.out.println("Time:" + new Date().toString());
		System.out.println("Message:" + e.getMessage());
		e.printStackTrace();
		throw new RuntimeException(e);
	}

	@RequestMapping(value = "/manager/saveorupdate", method = RequestMethod.POST)
	public @ResponseBody
	String saveOrUpdate(@RequestBody Collection<M> entitys)
			throws ServletException {
		getServiceHandler().saveOrUpdateAll(entitys);

		return "添加成功";
	}

	/**
	 * 不更改数据库中的某些属性
	 * 
	 * @param entitys
	 * @param ignorePros
	 */
	protected void ingoreChangePros(Collection<M> entitys, String[] ignorePros) {
		Iterator<M> iter = null;
		for (iter = entitys.iterator(); iter.hasNext();) {
			M remote = iter.next();
			if (remote.getId() != null) {
				M local = getServiceHandler().getById(remote.getId());
				BeanUtils.copyProperties(remote, local, ignorePros);
				entitys.remove(remote);
				entitys.add(local);
			}
		}
	}

}

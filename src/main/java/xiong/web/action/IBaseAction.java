package xiong.web.action;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Domain;
import xiong.domain.web.WebColumn;
import xiong.util.PageBean;

public interface IBaseAction<M extends Domain<Serializable>> {
	@RequestMapping(value = "/getGridColumns", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<WebColumn> getGridColumns() throws ServletException;

	@RequestMapping(value = "/list", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public PageBean<M> list(@RequestParam int start, @RequestParam int limit )
			throws ServletException;

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public M get(@PathVariable Long id) throws ServletException;

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@RequestBody Collection<M> entitys)
			throws ServletException;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestBody Collection<M> entitys)
			throws ServletException;

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@RequestBody Long[] ids) throws ServletException;

	@RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
	public @ResponseBody
	String saveOrUpdate(Collection<M> entitys) throws ServletException;

}

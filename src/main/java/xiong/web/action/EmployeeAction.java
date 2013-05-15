package xiong.web.action;

import java.util.Collection;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Employee;
import xiong.service.IDefaultService;
import xiong.service.IEmployeeService;
import xiong.util.PageBean;

@JsonIgnoreProperties(ignoreUnknown=true)
@Controller
@RequestMapping("/employee")
public class EmployeeAction extends AbsBaseAction<Employee> {
	@Resource
	private IEmployeeService employeeServiceImpl;

	@Override
	public IDefaultService<Employee> getServiceHandler() {
		return employeeServiceImpl;
	}
	@Override
	@RequestMapping(value = "/manager/saveorupdate", method = RequestMethod.POST)
	public @ResponseBody
	String saveOrUpdate(@RequestBody Collection<Employee> entitys)
			throws ServletException {
		ingoreChangePros(entitys, new String[] { "roles" });
		return super.saveOrUpdate(entitys);
	}

	@Override
	@RequestMapping(value = "/manager/update", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@RequestBody Collection<Employee> entitys)
			throws ServletException {
		ingoreChangePros(entitys, new String[] { "roles" });
		return super.update(entitys);
	}

	// private void forbidChangeRoles(Collection<Employee> entitys) {
	// Iterator<Employee> iter = null;
	// for (iter = entitys.iterator(); iter.hasNext();) {
	// Employee remote = iter.next();
	// if (remote.getId() != null && remote.getId() > 0) {
	// Employee local = employeeServiceImpl.getById(remote.getId());
	// BeanUtils.copyProperties(remote, local,
	// new String[] { "roles" });
	// entitys.remove(remote);
	// entitys.add(local);
	// }
	// }
	// }

	@RequestMapping(value = "/checkName/{username}", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String checkName(@PathVariable String username)
			throws ServletException {
		if (username == null || username.length() <= 5)
			return "登录名长度必须大于5个字符";
		return employeeServiceImpl.findExit(username) ? "登录名已存在" : null;
	}
//	'username':username,
//	'realname':realname,
//	'gtSalary':gtSalary,
//	'die':die,
//	'gtAge':gtAge,
//	'deptId':deptId 
	@RequestMapping(value = "/query", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public PageBean<Employee> query(@RequestParam int start,@RequestParam int limit, @RequestParam String username,@RequestParam String realname,//
			@RequestParam Float gtSalary, @RequestParam Boolean die ,@RequestParam Integer gtAge,@RequestParam Long deptId)
			throws ServletException {
		return employeeServiceImpl.query(start, limit, username, realname, gtSalary, gtAge, deptId, die, null);
	}

}

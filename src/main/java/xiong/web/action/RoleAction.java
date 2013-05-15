package xiong.web.action;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Authority;
import xiong.domain.Employee;
import xiong.domain.Role;
import xiong.service.IAuthorityService;
import xiong.service.IDefaultService;
import xiong.service.IEmployeeService;
import xiong.service.IRoleService;

@Controller
@RequestMapping("/role")
public class RoleAction extends AbsBaseAction<Role> {
	@Resource
	private IRoleService roleServiceImpl;
	@Resource
	private IAuthorityService authorityServiceImpl;

	@Resource
	private IEmployeeService employeeServiceImpl;

	@Override
	public IDefaultService<Role> getServiceHandler() {
		return roleServiceImpl;
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Role> listAll() throws ServletException {
		return roleServiceImpl.list();
	}

	@RequestMapping(value = "/setAuths/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody
	String setAuths(@PathVariable Long id, @RequestBody Long[] authIds)
			throws ServletException {
		Role role = roleServiceImpl.getById(id);
		role.setAuths(new LinkedHashSet<Authority>());
		for (Serializable aid : authIds) {
			authorityServiceImpl.getById(aid);
			role.setAuth(authorityServiceImpl.getById(aid));
		}
		roleServiceImpl.update(role);
		return "修改成功";
	}

	@RequestMapping(value = "/setRoles/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
	public @ResponseBody
	String setRoles(@PathVariable Long id, @RequestBody Long[] roleIds)
			throws ServletException {
		Employee employee = employeeServiceImpl.getById(id);
		employee.setRoles(new LinkedHashSet<Role>());
		for (Serializable rid : roleIds) {
			employee.setRole(roleServiceImpl.getById(rid));
		}
		employeeServiceImpl.update(employee);
		return "修改成功";
	}
}

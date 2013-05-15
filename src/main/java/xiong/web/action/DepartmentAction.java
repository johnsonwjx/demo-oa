package xiong.web.action;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Department;
import xiong.service.IDefaultService;
import xiong.service.IDepartmentService;

@RequestMapping("/department")
@Controller
public class DepartmentAction extends AbsBaseAction<Department> {
	@Resource
	private IDepartmentService departmentServiceImpl;

	@Override
	public IDefaultService<Department> getServiceHandler() {
		return departmentServiceImpl;
	}

	@RequestMapping("/listAll")
	public @ResponseBody
	List<Department> listAll() {
		return departmentServiceImpl.listAllTopNode(null, null);
	}

	@Override
	@RequestMapping(value = "/manager/saveorupdate", method = RequestMethod.POST)
	public @ResponseBody
	String saveOrUpdate(@RequestBody Collection<Department> entitys)
			throws ServletException {
		ingoreChangePros(entitys, new String[] { "parent", "employees" });
		return super.saveOrUpdate(entitys);
	}

	@Override
	@RequestMapping(value = "/manager/update", method = RequestMethod.PUT)
	@ResponseBody
	public String update(Collection<Department> entitys)
			throws ServletException {
		ingoreChangePros(entitys, new String[] { "parent", "employees" });
		return super.update(entitys);
	}

}

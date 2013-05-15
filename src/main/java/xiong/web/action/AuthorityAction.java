package xiong.web.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xiong.domain.Authority;
import xiong.domain.web.WebColumn;
import xiong.service.IAuthorityService;
import xiong.util.EntityUtil;
import xiong.util.PageBean;

@Controller
@RequestMapping("/authority")
public class AuthorityAction {
	@Resource
	private IAuthorityService authorityServiceImpl;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody
	PageBean<Authority> list(@RequestParam int start) {
		List<Authority> data = authorityServiceImpl.list();
		PageBean<Authority> auts = new PageBean<Authority>(data.size(), start,
				40);
		auts.setData(data);
		return auts;
	}

	@RequestMapping(value = "/getGridColumns", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<WebColumn> getGridColumns() throws ServletException {
		return EntityUtil.getGridColumn(Authority.class);

	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public List<Authority> listAll() throws ServletException {
		return authorityServiceImpl.list();

	}

}

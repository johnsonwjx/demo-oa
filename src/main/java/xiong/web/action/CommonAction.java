package xiong.web.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CommonAction {
	@RequestMapping("/powermiss")
	public @ResponseBody
	String powermiss() {
		return "你没有权限";
	}
}

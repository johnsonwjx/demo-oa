package xiong.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class VolidateAuthCodeUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		System.out.println("=========  操作日志记录  =========");
		System.out.println("登录系统操作:");
		System.out.println(request.getParameter("j_username"));
		// 这里可以进行验证验证码的操作
		return super.attemptAuthentication(request, response);
	}
}

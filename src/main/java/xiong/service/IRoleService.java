package xiong.service;

import java.util.List;

import xiong.domain.Role;

public interface IRoleService extends IDefaultService<Role> {
	Role getByRoleName(String roleName);
	List<Role> list();
}

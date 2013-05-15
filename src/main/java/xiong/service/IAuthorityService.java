package xiong.service;

import java.util.List;

import xiong.domain.Authority;

public interface IAuthorityService extends IDefaultService<Authority>{
	Authority getAuthByAuthName(String authName) ;
	void clearAll() ;
	List<Authority> list();
}

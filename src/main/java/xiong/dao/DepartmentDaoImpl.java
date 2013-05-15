package xiong.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import xiong.domain.Department;

@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements
		IDepartmentDao {
	@Override
	public void delById(Serializable id) {
		Session session = getSession();
		Department entity = (Department) session.load(entityClass, id);
		Department parent = entity.getParent();
		if (parent != null) {
			parent.removeChild(entity);
		}
		session.delete(entity);
	}
}

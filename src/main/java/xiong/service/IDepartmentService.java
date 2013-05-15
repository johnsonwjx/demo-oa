package xiong.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import xiong.domain.Department;

public interface IDepartmentService extends
		IDefaultService<Department> {
	List<Department> listAllTopNode(Criterion criterion ,
			Order orders);
}

package nospring;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

import xiong.domain.Department;
import xiong.domain.Role;

public class EntityUtilTest {
	@Test
	public void Test() {
		Department d1=new Department("d1", "");
		Department parent=new Department("p1", "p1");
		d1.setParent(parent);
		
		Department d2=new Department("d1", "");
		Department parent2=new Department("p2", "p2");
		d2.setParent(parent2);
		
		BeanUtils.copyProperties(d2, d1, new String[] { "parent" });
		
		System.out.println(parent.getName());
		System.out.println(d1.getParent().getName());
	}

	public void testGetColumns() {
		Role r1 = new Role();
		Role r2 = new Role("adf");
		r2.setId(12L);
		String[] a = { "id" };
		BeanUtils.copyProperties(r2, r1, a);
		System.out.println(r1.getId());
		System.out.println(r1.getRoleName());
	}

	public void BeanCopy() {
		Role r1 = new Role();
		Role r2 = new Role("adf");
		r2.setId(12L);
		String[] a = { "id" };
		BeanUtils.copyProperties(r2, r1, a);
		System.out.println(r1.getId());
		System.out.println(r1.getRoleName());
		/*
		 * try { List<GridColumn> columns = EntityUtil.getColumns(User.class);
		 * for (GridColumn column : columns) { System.out.println("text:" +
		 * column.getText() + ",dataIndex:" + column.getDataIndex()// +
		 * ",hidden:" + column.isHidden()+",nullable:"+column.isAllowBlank()+//
		 * ",type:"+column.getType()); } } catch (ClassNotFoundException e) { //
		 * TODO Auto-generated catch block }
		 */
	}

}

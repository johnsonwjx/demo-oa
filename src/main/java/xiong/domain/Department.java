package xiong.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;

import xiong.domain.web.ColumnDesc;

/**
 * demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-6
 */
@Entity
@Table(name = "DEPT")
public class Department extends Domain<Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;
	@ColumnDesc(value = "部门ID", xtype = "treecolumn",width=300)
	@Id
	@GeneratedValue
	private Long id;
	@ColumnDesc(value = "部门名称", width = 150)
	@Column(length = 40)
	private String name;
	@ColumnDesc(value = "部门描述", width = 300)
	private String info;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "parent")
	private Set<Department> children = new HashSet<Department>();

	@Transient
	public boolean isExpanded() {
		return true;
	}

	@Transient
	public boolean isLeaf() {
		return this.children.size() <= 0;
	}

	@Transient
	public String getText() {
		return this.name;
	}

	public Department(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "parent_id", insertable = true, updatable = true, referencedColumnName = "id")
	private Department parent;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST }, mappedBy = "department")
	@JsonIgnore
	private Set<Employee> employees = new HashSet<Employee>();

	public Department() {
	}

	public Department(String name, String info) {
		this.name = name;
		this.info = info;
	}

	public Set<Department> getChildren() {
		return children;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public String getInfo() {
		return info;
	}

	public String getName() {
		return name;
	}

	public Department getParent() {
		return parent;
	}

	public void setChildren(Set<Department> children) {
		Iterator<Department> iter = null;
		for (iter = children.iterator(); iter.hasNext();) {
			Department child = iter.next();
			child.setParent(this);
		}
		this.children = children;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	@Transient
	public void setChild(Department child) {
		child.setParent(this);
		this.children.add(child);
	}

	@Transient
	public void removeChild(Department child) {
		child.setParent(null);
		this.children.remove(child);
	}
}

package xiong.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import xiong.domain.web.ColumnDesc;

/**
 * demo-oa 员工
 * 
 * @author xiong
 * @time 2013 2013-5-6
 */
@Entity
@Table(name = "EMPS")
public class Employee extends Domain<Serializable> implements Serializable {
	public Employee(Long id) {
		super();
		this.id = id;
	}

	private static final long serialVersionUID = 1L;
	@ColumnDesc(value = "员工ID")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ColumnDesc(value = "登录名")
	@Column(length = 40, nullable = false)
	private String username;
	@ColumnDesc(value = "真实姓名")
	@Column(length = 40)
	private String realname;
	@ColumnDesc("年龄")
	private int age;
	@ColumnDesc("电邮")
	@Column(length = 40)
	private String email;
	@ColumnDesc(value = "性别", renderer = true)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ColumnDesc("电话")
	@Column(length = 20)
	private String phone;
	@ColumnDesc("地址")
	@Column(length = 40)
	private String address;
	@ColumnDesc(value = "身份证", width = 100)
	@Column(length = 40)
	private String idCard;
	@ColumnDesc(value = "密码")
	@Column(length = 40, nullable = false)
	private String password;
	@ColumnDesc("薪金")
	private float salary;
	@ColumnDesc(value = "出生日期", width = 100, xtype = "datecolumn", format = "Y-m-d")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	@ColumnDesc(value = "入职日期", width = 100, xtype = "datecolumn", format = "Y-m-d")
	@Temporal(TemporalType.DATE)
	private Date hiredDay=new Date();
	@ColumnDesc(value = "是否停职", width = 100, xtype = "booleancolumn")
	private boolean die=false;

	@ColumnDesc(value = "设置角色", width = 130, xtype = "actioncolumn", useField = false)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "EMPS_ROLES", joinColumns = { @JoinColumn(name = "EMP_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private Set<Role> roles = new LinkedHashSet<Role>();

	@ColumnDesc(value = "设置部门", xtype = "actioncolumn")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_Id", referencedColumnName = "id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Department department;

	/*
	 * private List<Integer> rolesId = new ArrayList<Integer>();
	 * 
	 * @Transient public List<Integer> getRolesId() { Iterator<Role> iter =
	 * null; List<Integer> rolesId = new ArrayList<Integer>(); for (iter =
	 * roles.iterator(); iter.hasNext();) { rolesId.add(iter.next().getId()); }
	 * return rolesId; }
	 * 
	 * public void setRolesId(List<Integer> rolesId) { this.rolesId = rolesId; }
	 */
	public Employee() {
		this.hiredDay=new Date();
		this.die=false;
	}

	public String getAddress() {
		return address;
	}

	public int getAge() {
		return age;
	}

	public Date getBirthday() {
		return birthday;
	}

	public Department getDepartment() {
		return department;
	}

	public String getEmail() {
		return email;
	}


	public Gender getGender() {
		return gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getRealname() {
		return realname;
	}

	@Transient
	public List<Long> getRoleIds() throws Exception {
		Set<Role> roles = getRoles();
		List<Long> roleIds = new ArrayList<Long>();
		if (roles.size() > 0) {
			Iterator<Role> iter = null;
			for (iter = roles.iterator(); iter.hasNext();) {
				Role r = iter.next();
				roleIds.add(r.getId());
			}
		}
		return roleIds;
	}

	@Transient
	@JsonIgnore
	public String getRoleNames() throws Exception {
		Set<Role> roles = getRoles();
		if (roles.size() > 0) {
			StringBuilder roleNames = new StringBuilder();
			Iterator<Role> iter = null;
			for (iter = roles.iterator(); iter.hasNext();) {
				Role r = iter.next();
				roleNames.append(r.getRoleName()).append(",");
			}
			roleNames.deleteCharAt(roleNames.length() - 1);
			return roleNames.toString();
		}
		return "";
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public float getSalary() {
		return salary;
	}


	public String getUsername() {
		return username;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Transient
	public void setRole(Role role) {
		this.roles.add(role);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}


	public Date getHiredDay() {
		return hiredDay;
	}

	public void setHiredDay(Date hiredDay) {
		this.hiredDay = hiredDay;
	}

	public boolean isDie() {
		return die;
	}

	public void setDie(boolean die) {
		this.die = die;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

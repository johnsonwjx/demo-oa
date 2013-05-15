package xiong.domain;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import xiong.domain.web.ColumnDesc;

/**
 * 角色类 demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-3
 */
@Entity
@Table(name = "ROLES")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role extends Domain<Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	@ColumnDesc(value="角色ID",width=150)
	@Id
	@GeneratedValue
	private Long id;

	@ColumnDesc(value="角色名称",width=300)
	@Column(length = 40)
	private String roleName;

	@ColumnDesc(value="设置权限",width=130,xtype="actioncolumn")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "ROLES_AUTHORITIES", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "AUTHORITY_ID") })
	@OrderBy("id")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Authority> auths = new LinkedHashSet<Authority>();

	public Role(Long id) {
		this.id = id;
	}

	public Role() {
	}

	public Role(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		return true;
	}

	public Set<Authority> getAuths() {
		return auths;
	}

	public Long getId() {
		return id;
	}

	public String getRoleName() {
		return roleName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
		return result;
	}

	@Transient
	public void setAuth(Authority auth) {
		this.auths.add(auth);
	}

	public void setAuths(Set<Authority> auths) {
		this.auths = auths;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}

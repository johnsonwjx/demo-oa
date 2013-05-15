package xiong.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import xiong.domain.web.ColumnDesc;

/**
 * 权限 demo-oa
 * 
 * @author xiong
 * @time 2013 2013-5-3
 */
@Entity
@Table(name = "AUTHORITIES")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Authority extends Domain<Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	@ColumnDesc(value = "权限ID", width = 200)
	@Id
	@GeneratedValue
	private Long id;

	@ColumnDesc(value = "权限名称", width = 200)
	@Column(length = 40, nullable = false)
	private String authName;

	@ColumnDesc(value = "权限描述", width = 300)
	@Column(length = 60)
	private String authDesc;

	public Authority() {
	}

	public Authority(String authName) {
		this.authName = authName;
	}

	public Authority(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Authority other = (Authority) obj;
		if (authName == null) {
			if (other.authName != null)
				return false;
		} else if (!authName.equals(other.authName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAuthDesc() {
		return authDesc;
	}

	public String getAuthName() {
		return authName;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authName == null) ? 0 : authName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public void setId(Long id) {
		this.id = id;
	}

}

/*
 * 文件名： PersistentObject.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.model;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.cmcc.anal.common.exception.base.ApplicationBaseException;
import com.cmcc.anal.common.util.CloneUtil;


/**
 * 持久对象的基类,所有的pojo对象都需要继承
 * 
 * 各模块自主实现主键、equals、toString、hashcode
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
@MappedSuperclass
public abstract class PersistentObject implements Serializable {

	private static final long serialVersionUID = -1684484162554667533L;

	@Transient
	public abstract Object getIdentifier();

	public void setData(Object vo) throws ApplicationBaseException {
		try {
			CloneUtil.copyProperties(this, vo);
		}		catch (IllegalAccessException e) {
//			String msg = ExceptionMessage
//					.getString("Class.Common.CloneUtil.CopyProperties.Exception");
//			msg = msg + "from Obj " + this.getClass().getName();
//			msg = msg + "to Obj " + vo.getClass().getName();
//			throw new ApplicationBaseException(msg);
		}		catch (InvocationTargetException e) {
//			String msg = ExceptionMessage
//					.getString("Class.Common.CloneUtil.CopyProperties.Exception");
//			msg = msg + "from Obj " + this.getClass().getName();
//			msg = msg + "to Obj " + vo.getClass().getName();
//			throw new ApplicationBaseException(msg);
		}
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PersistentObject)) {
			return false;
		}
		EqualsBuilder eq = new EqualsBuilder();
		if (this.getIdentifier() != null) {
			return eq.append(this.getIdentifier(),
					((PersistentObject) o).getIdentifier()).isEquals();
		}		else {
			return (((PersistentObject) o).getIdentifier() == null);
		}
	}

	public String toString() {
		ToStringBuilder strBuilder = new ToStringBuilder(this);
		if (this.getIdentifier() != null) {
			strBuilder.append("identifier", this.getIdentifier());
		}
		return strBuilder.toString();
	}

	public int hashCode() {
		return (this.getIdentifier() != null ? new HashCodeBuilder(17, 37)
				.append(this.getIdentifier().toString()).toHashCode() : 0);
	}
}

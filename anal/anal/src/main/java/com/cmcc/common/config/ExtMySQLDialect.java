package com.cmcc.common.config;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class ExtMySQLDialect extends MySQLDialect {
	
	public ExtMySQLDialect() {
		 super();
         registerHibernateType(-1,Hibernate.STRING.getName());
         registerHibernateType(-4,Hibernate.BLOB.getName());
	}
}

package com.cmcc.anal.persistence;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cmcc.anal.TestBase;

public class TestWebAdminDAO extends TestBase {
	private static Logger logger = LoggerFactory.getLogger(TestWebAdminDAO.class);

	@Test
	public void assertObjectNotNull() {
		Object webAdminDAO = getBean("webAdminDAO");
		Assert.assertNotNull(webAdminDAO);
	}
}

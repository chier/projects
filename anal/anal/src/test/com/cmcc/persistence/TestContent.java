package com.cmcc.persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.business.interfaces.content.IContentAttachManager;
import com.cmcc.framework.business.interfaces.content.IContentBodyManager;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.persistence.interfaces.content.IContentAttachDAO;

public class TestContent extends TestBase {
	static IContentAttachDAO contentAttachDAO = null;

	static IContentInfoManager contentInfoManager = null;

	static IContentAttachManager contentAttachManager = null;
	
	static IContentBodyManager contentBodyManager = null;
	@BeforeClass
	public static void initBeforeClass() {
		contentAttachDAO = (IContentAttachDAO) getBean("contentAttachDAO");
		contentInfoManager = (IContentInfoManager) getBean("contentInfoManager");
		contentAttachManager = (IContentAttachManager) getBean("contentAttachManager");
		contentBodyManager = (IContentBodyManager) getBean("contentBodyManager");
	}

	@Test
	public void getByContent() {
		contentAttachDAO.getByContent(1);
	}
	
	@Test
	public void infoManagerGet(){
		this.contentInfoManager.get(1);
	}
	
	@Test
	public void ContentAttachGet(){
		this.contentAttachManager.get(1);
	}
	
	@Test
	public void contentBodyManagerGet(){
		this.contentBodyManager.get(1);
	}
}

package com.ibeetl.bbs.model;
import java.util.Date;
import java.util.List;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.engine.PageQuery;

/*
* 
* gen by beetlsql 2016-06-13
*/
public class BbsPost  extends TailBean{
	private Integer id ;
	private Integer hasReply ;
	private Integer topicId ;
	private Integer userId ;
	private String content ;
	private Date createTime ;
	private Date updateTime ;
	
	
	private List<BbsReply> replys ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHasReply() {
		return hasReply;
	}

	public void setHasReply(Integer hasReply) {
		this.hasReply = hasReply;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



	public List<BbsReply> getReplys() {
		return replys;
	}

	public void setReplys(List<BbsReply> replys) {
		this.replys = replys;
	}

	
}

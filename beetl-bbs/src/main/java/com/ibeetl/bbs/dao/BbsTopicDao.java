package com.ibeetl.bbs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.annotatoin.SqlStatementType;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.util.lucene.entity.IndexObject;

public interface BbsTopicDao extends BaseMapper<BbsTopic> {
	void queryTopic(PageQuery query);
	@SqlStatement(params="userId")
	List<BbsTopic> queryMyMessageTopic(int userId);
	
	@SqlStatement(params="userId")
	Integer queryMyMessageTopicCount(int userId);
	
	@SqlStatement(params="topicId",returnType=Integer.class)
	List<Integer> getParticipantUserId(Integer topicId);
	
	/**
	   * 新增索引
	   * @param fileupdateDate 索引文件最后更新时间
	   * @param lastupdateDate 最后提交时间
	   * @return
	   */
	    @SqlStatement(type=SqlStatementType.SELECT,params="fileupdateDate,lastupdateDate",returnType = IndexObject.class )
		List<IndexObject> getBbsTopicListByDate(Date fileupdateDate,Date lastupdateDate);

}

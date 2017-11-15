package com.ibeetl.bbs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.annotatoin.SqlStatementType;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;

import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.util.lucene.entity.IndexObject;

public interface BbsPostDao extends BaseMapper<BbsPost> {
	@SqlStatement(type=SqlStatementType.SELECT)
    void getPosts(PageQuery query);
    @SqlStatement(params="topicId")
    void deleteByTopicId(int topicId);
    @Sql(value="select max(create_time) from bbs_post where user_id=? order by id desc ",returnType=Date.class)
    Date getLatestPostDate(int userId);
    /**
     * 获取主题和回复最后的提交时间
     * @return
     */
    @SqlStatement(type=SqlStatementType.SELECT)
    Map<String,Date>  getLastPostDate();
  /**
   * 新增索引
   * @param fileupdateDate 索引文件最后更新时间
   * @param lastupdateDate 最后提交时间
   * @return
   */
    @SqlStatement(type=SqlStatementType.SELECT,params="fileupdateDate,lastupdateDate",returnType = IndexObject.class )
	List<IndexObject> getBbsPostListByDate(Date fileupdateDate,Date lastupdateDate);

}

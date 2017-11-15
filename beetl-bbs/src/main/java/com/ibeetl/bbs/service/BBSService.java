package com.ibeetl.bbs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.engine.PageQuery;

import com.ibeetl.bbs.model.BbsMessage;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.util.lucene.LuceneUtil;
import com.ibeetl.bbs.util.lucene.entity.IndexObject;

public interface BBSService {
	BbsTopic getTopic(int id);
	
	PageQuery getTopics(PageQuery query);
	
	List<BbsTopic> getMyTopics(int userId);
	
	Integer getMyTopicsCount(int userId);
	
	public void updateMyTopic(int msgId,int status);
	
	public BbsMessage makeOneBbsMessage(int userId,int topicId,int statu);
	
	
	public void notifyParticipant(int topicId,int ownerId);
	
	void getHotTopics(PageQuery query);

	void getNiceTopics(PageQuery query);

	void getPosts(PageQuery query);

	void saveUser(BbsUser user);

	BbsUser login(BbsUser user);

	void saveTopic(BbsTopic topic, BbsPost post, BbsUser user);

	void savePost(BbsPost post, BbsUser user);


	void saveReply(BbsReply reply);

	void deleteTopic(int id);

	void deletePost(int id);
	
	void updateTopic(BbsTopic topic);
	
	Date getLatestPost(int userId);
	
	/**
	 * 获取索引数据
	 * 	1.当无索引文件夹时获取  第一条数据 到 最新提交时间（主题贴和回复贴）  的前一天的所有数据
	 * 	2.当有索引文件夹时获取  上次索引文件夹修改日期 到 最新提交时间（主题贴和回复贴）  的前一天 的所有数据
	 * @param fileupdateDate
	 * @return
	 * @throws Exception
	 */
	List<IndexObject> getBbsTopicPostList(Date fileupdateDate);
	
	/**
	 * 创建所有并返回搜索结果
	* @param keyword
	* @param p	当前第几页
	* @return
	* PageQuery<IndexObject>
	* @author ykb yang.kb@topcheer.com   
	* @date 2017年5月19日 下午4:54:46
	 */
	PageQuery<IndexObject> getQueryPage(String keyword,int p);
}

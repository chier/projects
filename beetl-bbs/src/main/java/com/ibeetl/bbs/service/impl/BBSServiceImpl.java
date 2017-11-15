package com.ibeetl.bbs.service.impl;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ibeetl.bbs.common.Const;
import com.ibeetl.bbs.dao.BbsModuleDao;
import com.ibeetl.bbs.dao.BbsPostDao;
import com.ibeetl.bbs.dao.BbsReplyDao;
import com.ibeetl.bbs.dao.BbsTopicDao;
import com.ibeetl.bbs.dao.BbsUserDao;
import com.ibeetl.bbs.model.BbsMessage;
import com.ibeetl.bbs.model.BbsPost;
import com.ibeetl.bbs.model.BbsReply;
import com.ibeetl.bbs.model.BbsTopic;
import com.ibeetl.bbs.model.BbsUser;
import com.ibeetl.bbs.service.BBSService;
import com.ibeetl.bbs.service.BbsUserService;
import com.ibeetl.bbs.util.lucene.LuceneUtil;
import com.ibeetl.bbs.util.lucene.entity.IndexObject;

@Service
public class BBSServiceImpl implements BBSService {
	@Autowired
	BbsTopicDao topicDao;
	@Autowired
	BbsPostDao postDao;
	@Autowired
	BbsUserDao userDao;
	@Autowired
	BbsModuleDao moduleDao;
	@Autowired
	BbsReplyDao replyDao;
	@Autowired
	SQLManager sql ;
	@Autowired
	LuceneUtil luceneUtil;
	
	@Autowired
	BbsUserService gitUserService;

	@Override
	public BbsTopic getTopic(int id) {
		return topicDao.unique(id);
	}
	
	

	@Override
	@Cacheable("TOPIC")
	public PageQuery getTopics(PageQuery query) {
		
		topicDao.queryTopic(query);
		return query;
	}
	@Override
//	@Cacheable("MY-MESSAGE")
	public List<BbsTopic> getMyTopics(int userId){
		return topicDao.queryMyMessageTopic(userId);
	}
	@Override
//	@Cacheable("MY-MESSAGE-COUNT")
	public Integer getMyTopicsCount(int userId){
		return topicDao.queryMyMessageTopicCount(userId);
	}
	
	@Override
	public void updateMyTopic(int msgId,int status){
		BbsMessage msg = new BbsMessage();
		msg.setStatus(status);
		msg.setId(msgId);
		sql.updateTemplateById(msg);
		
	}
	@Override
	public BbsMessage makeOneBbsMessage(int userId,int topicId,int status){
		BbsMessage msg = new BbsMessage();
		msg.setUserId(userId);
		msg.setTopicId(topicId);
		List<BbsMessage> list = sql.template(msg);
		if(list.isEmpty()){
			msg.setStatus(status);
			sql.insert(msg,true);
			return msg;
		}else{
			msg =  list.get(0);
			if(msg.getStatus()!=status){
				msg.setStatus(status);
				sql.updateById(msg);
			}
			return msg;
		}
			
	}
	
	@Override
//	@CacheEvict(cacheNames={"MY-MESSAGE","MY-MESSAGE-COUNT"}, allEntries=true)
	public void notifyParticipant(int topicId,int ownerId){
		List<Integer> userIds = topicDao.getParticipantUserId(topicId);
		for(Integer userId:userIds){
			if(userId==ownerId){
				continue;
			}
			//TODO,以后改成批处理,但存在insert&update问题
			makeOneBbsMessage(userId,topicId,1);
		}
	}

	@Override
	public void getHotTopics(PageQuery query) {
		Map paras = new HashMap();
		paras.put("type", "hot");
		query.setParas(paras);
		topicDao.queryTopic(query);
	}

	@Override
	public void getNiceTopics(PageQuery query) {
		Map paras = new HashMap();
		paras.put("type", "nice");
		query.setParas(paras);
		topicDao.queryTopic(query);
//		fillTopic(query);
	}

	@Override
	public void getPosts(PageQuery query) {
//		postDao.getPosts(query, topicId);
		postDao.getPosts(query);
		if(query.getList() != null){
			for (Object topicObj : query.getList()) {
				final BbsPost post = (BbsPost) topicObj;
				List<BbsReply> replys = replyDao.allReply(post.getId());
				post.setReplys (replys);
				
			}
		}
	}

	@Override
	public void saveUser(BbsUser user) {
		userDao.insert(user);
	}

	@Override
	public BbsUser login(BbsUser user) {
		List<BbsUser> users = sql.template(user);
		if(CollectionUtils.isEmpty(users)){
			return null;
		}
		return users.get(0);
	}

	@Override
	@CacheEvict(cacheNames="TOPIC", allEntries=true)
	public void saveTopic(BbsTopic topic, BbsPost post, BbsUser user) {
		topic.setUserId(user.getId());
		topic.setCreateTime(new Date());
		topicDao.insert(topic, true);
		post.setUserId(user.getId());
		post.setTopicId(topic.getId());
		post.setCreateTime(new Date());
		postDao.insert(post);
		gitUserService.addTopicScore(user.getId());
	}

	@Override
	@CacheEvict(cacheNames="TOPIC", allEntries=true)
	public void savePost(BbsPost post, BbsUser user) {
		post.setUserId(user.getId());
		postDao.insert(post);
		gitUserService.addPostScore(user.getId());
	}

	

	@Override
	public void saveReply(BbsReply reply) {
		replyDao.insert(reply,true);
		gitUserService.addReplayScore(reply.getUserId());
	}

	@Override
	@CacheEvict(cacheNames="TOPIC", allEntries=true)
	public void deleteTopic(int id) {
		sql.deleteById(BbsTopic.class, id);
		postDao.deleteByTopicId(id);
		replyDao.deleteByTopicId(id);
	}

	@Override
	@CacheEvict(cacheNames="TOPIC", allEntries=true)
	public void deletePost(int id) {
		sql.deleteById(BbsPost.class, id);
		replyDao.deleteByPostId(id);
	}

	@Override
	public Date getLatestPost(int userId) {
		return postDao.getLatestPostDate(userId);
	}

	@CacheEvict(cacheNames="TOPIC", allEntries=true)
	public void updateTopic(BbsTopic topic){
		sql.updateById(topic);
	}

	/**
	 * 比较时间，然后根据时间进行数据缓存
	 */
	@Override
	public List<IndexObject> getBbsTopicPostList(Date fileupdateDate){
		List<IndexObject>  indexObjectsList = new ArrayList<>();
		//获取主题和回复最后的提交时间
		List<IndexObject> bbsTopics = null;
		List<IndexObject> bbsPosts = null;
		try {
			Map<String,Date> lastPostDate = postDao.getLastPostDate();
			Date topiclastupdate = LuceneUtil.dateFormat.parse(LuceneUtil.dateFormat.format(lastPostDate.get("topiclastupdate")));
			Date postlastupdate = LuceneUtil.dateFormat.parse(LuceneUtil.dateFormat.format(lastPostDate.get("postlastupdate")));
			if(fileupdateDate != null)fileupdateDate =  LuceneUtil.dateFormat.parse(LuceneUtil.dateFormat.format(fileupdateDate));
			
			if(fileupdateDate == null || (topiclastupdate != null && 
					LuceneUtil.dateCompare(topiclastupdate,fileupdateDate))){
				bbsTopics = topicDao.getBbsTopicListByDate(fileupdateDate, topiclastupdate);
			}
			if(fileupdateDate == null || (postlastupdate != null &&
					LuceneUtil.dateCompare(postlastupdate,fileupdateDate))){
				bbsPosts = postDao.getBbsPostListByDate(fileupdateDate, postlastupdate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(bbsTopics!=null){
			indexObjectsList.addAll(bbsTopics);
		}
		if(bbsPosts!=null){
			indexObjectsList.addAll(bbsPosts);
		}
//		System.out.println("================");
//		System.out.println(JSONObject.toJSONString(indexObjectsList));
		return indexObjectsList;
	}



	@Override
	public PageQuery<IndexObject> getQueryPage(String keyword,int p) {
		//查看索引文件最后修改日期
    	File file = new File(luceneUtil.getIndexDer());
    	Date fileupdateDate = null;
    	if(file.exists() && file.listFiles().length  > 0 ){
    		fileupdateDate = new Date(file.lastModified());
    	}
		//获取索引的数据 ：主题和回复
    	List<IndexObject> bbsContentList = this.getBbsTopicPostList(fileupdateDate);
    	
    	//创建索引
    	luceneUtil.createDataIndexer(bbsContentList);
    	
    	return luceneUtil.searcherKeyword(keyword,Const.TOPIC_PAGE_SIZE, p);
	}
}

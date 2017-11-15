getPosts
===
    select
    @pageTag(){
    *
    @}
    from bbs_post where true
    @if(!isEmpty(topicId)){
    	 and `topic_id`=#topicId#
    @}
    @if(!isEmpty(isAdmin)){
         order by id desc
    @}
    @ orm.single({"userId":"id"},"BbsUser");
    @ orm.single({"topicId":"id"},"BbsTopic");

deleteByTopicId
===
    delete from bbs_post where `topic_id`=#topicId#

sample
===
* 注释

	select #use("cols")# from bbs_post where #use("condition")#

cols
===

	id,topic_id,user_id,content,create_time,has_reply,update_time

updateSample
===

	`id`=#id#,`topic_id`=#topicId#,`user_id`=#userId#,`content`=#content#,`create_time`=#createTime#,`has_reply`=#hasReply#,`update_time`=#updateTime#

condition
===

	1 = 1  
	@if(!isEmpty(topicId)){
	 and `topic_id`=#topicId#
	@}
	@if(!isEmpty(userId)){
	 and `user_id`=#userId#
	@}
	@if(!isEmpty(content)){
	 and `content`=#content#
	@}
	@if(!isEmpty(createTime)){
	 and `create_time`=#createTime#
	@}
	@if(!isEmpty(hasReply)){
	 and `has_reply`=#hasReply#
	@}
	@if(!isEmpty(updateTime)){
	 and `update_time`=#updateTime#
	@}

getLastPostDate
===

	SELECT max(t.create_time) topiclastupdate,max(p.create_time) postlastupdate 
	FROM bbs_topic t 	LEFT JOIN bbs_post p ON t.id = p.topic_id
	
getBbsPostListByDate
===

SELECT
	t.id topicId,
	t.user_id userId,
	t.is_nice isUp,
	t.is_up isNice,
	t.is_nice,
	(SELECT user_name FROM bbs_user WHERE id = t.user_id) userName,
	t.create_time createTime,
	t.post_count postCount,
	t.pv pv,
	t.module_id moduleId,
	(SELECT name FROM bbs_module WHERE id = t.module_id ) moduleName,
	p.topic_id topicId,
	IFNULL(p.content,'') postContent,
	'2' indexType,
	t.content topicContent
FROM
	bbs_post p
LEFT JOIN bbs_topic t ON p.topic_id = t.id
	WHERE p.create_time BETWEEN 
	@if(isEmpty(fileupdateDate)){
		''
	@}else{
		#fileupdateDate#
	@}
	AND #lastupdateDate# ORDER BY p.id DESC	

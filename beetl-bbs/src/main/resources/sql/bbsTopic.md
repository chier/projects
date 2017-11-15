queryTopic
===
	select  
	@pageTag(){
	t.*,m.name module_name,u.user_name user_name
	@}  
	FROM  bbs_topic t left join bbs_module m
	on t.module_id = m.id  left join bbs_user u on t.user_id=u.id
    where true
     @var type = type!"normal";
     @if(!isEmpty(moduleId)){
     	 and `module_id`=#moduleId#
     @}
     @if(!isEmpty(keyword)){
     	 and content like #'%'+keyword+'%'#
     @}
     @if("normal" == type){
         order by is_up desc,create_time desc
     @}
     @if("hot" == type){
          order by pv desc
     @}
     @if("nice" == type){
          and is_nice=true order by create_time desc
     @}

queryMyMessageTopic
===

	SELECT t.* FROM bbs_message  m left join bbs_topic t
	on m.topic_id= t.id where m.user_id=#userId# and status = 1
	
queryMyMessageTopicCount
===

	SELECT  count(1) from bbs_message  m 
	where m.user_id=#userId# and status = 1

queryHotTopic
===
    select  
    @pageTag(){
    *
    @}
    from bbs_topic order by pv desc

queryNiceTopic
===
    select 
    @pageTag(){
    *
    @}
    from bbs_topic where is_nice=true order by create_time desc

getTopicAndPostCount
===

* 根据id查找topic和拥有的post数量

	select t.*,(select count(1) from bbs_post where topic_id=#id#) post_count from bbs_topic t where t.id =#id#


getParticipantUserId
===

* 查询某个帖子的参与恩，包含post和reply俩种，用于群发通知

	select user_id from bbs_post where topic_id = #topicId#

	union 

	select  user_id from bbs_post where topic_id= #topicId#

getBbsTopicListByDate
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
	t.content topicContent,
	IFNULL((SELECT content FROM bbs_post WHERE topic_id = t.id ORDER BY id LIMIT 1),'') postContent ,
  	'1' indexType
	FROM
	bbs_topic t
	WHERE t.create_time BETWEEN  
	@if(isEmpty(fileupdateDate)){
		''
	@}else{
		#fileupdateDate#
	@}
	AND #lastupdateDate# ORDER BY t.id DESC


package com.cmcc.anal.common.util;

import com.cmcc.anal.common.vo.JsonResponseVo;




 /**
 *  Class Name: ReJsonResponseVo.java
 *  Function:
 *  	用于封装JSON返回结果
 *     Modifications:   
 *  
 *  @author zhijide@pica.com  DateTime 2012-11-14 上午10:46:17    
 *  @version 1.0
 */
public class ReJsonResponseVo {
	
	/**
	 *  Function:
	 *
	 *  @author zhijide@pica.com  DateTime 2012-10-7 下午01:34:22
	 *  @param b  服务是否变更成功 true 标识变更成功
	 *  @param desc 是返回信息
	 *  @return
	 */
	public static JsonResponseVo reJsonResponseVo(Boolean b,String desc,Object o){
		JsonResponseVo jsonResponseVo = new JsonResponseVo();
		if(desc ==null || desc.trim().length()==0)desc="";
		
		if(b){//服务状态审核通过返回值
			jsonResponseVo.setCode(0);
			jsonResponseVo.setMsg("SUCCESS"+"");
			jsonResponseVo.setData(o);
		}else{//服务状态审核拒绝返回值
			jsonResponseVo.setCode(1);
			jsonResponseVo.setMsg("FAILURE "+desc);
			jsonResponseVo.setData(o);
		}
		return jsonResponseVo;
	}
	
	
	/**
	 *  Function:
	 *
	 *  @author zhijide@pica.com  DateTime 2012-10-7 下午01:34:22
	 *  @param b  服务是否变更成功 true 标识变更成功
	 *  @param desc 是返回信息
	 *  @return
	 */
	public static JsonResponseVo reJsonResponseVo(Boolean b){
		JsonResponseVo jsonResponseVo = new JsonResponseVo();
		
		if(b){//服务状态审核通过返回值
			jsonResponseVo.setCode(0);
			jsonResponseVo.setMsg("SUCCESS");
			jsonResponseVo.setData(null);
		}else{//服务状态审核拒绝返回值
			jsonResponseVo.setCode(1);
			jsonResponseVo.setMsg("FAILURE");
			jsonResponseVo.setData(null);
		}
		return jsonResponseVo;
	}
	
	/**
	 *  Function:
	 *
	 *  @author zhijide@pica.com  DateTime 2012-10-7 下午01:34:22
	 *  @param b  服务是否变更成功 true 标识变更成功
	 *  @param desc 是返回信息
	 *  @return
	 */
	public static JsonResponseVo reJsonResponseVo(Boolean b,String desc){
		JsonResponseVo jsonResponseVo = new JsonResponseVo();
		if(desc ==null || desc.trim().length()==0)desc="";
		
		if(b){//服务状态审核通过返回值
			jsonResponseVo.setCode(0);
			jsonResponseVo.setMsg("SUCCESS");
			jsonResponseVo.setData(null);
		}else{//服务状态审核拒绝返回值
			jsonResponseVo.setCode(1);
			jsonResponseVo.setMsg(desc);
			jsonResponseVo.setData(null);
		}
		return jsonResponseVo;
	}
	
	/**
	 *  Function:
	 *
	 *  @author zhijide@pica.com  DateTime 2012-11-14 上午10:46:17
	 *  @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

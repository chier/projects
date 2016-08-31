/**
 * 
 */
package com.cmcc.anal.common.vo;

/**
 * @author tanyaowu
 *
 */
public class JsonRequestVo {
	
	/**
	 * 模块名称
	 */
	private String op;
	/**
	 * 源ID 登录0为名，其它都为用户id
	 */
	private String source_id;
	private String token;
	private Object data;

	/**
	 * 
	 */
	public JsonRequestVo() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	
}

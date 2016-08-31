/**
 * 
 */
package com.cmcc.anal.common.vo;

/**
 * @author tanyaowu
 * 
 */
public class JsonResponseVo {
	/**
	 * 模块名称
	 */
	private String op;

	/**
	 * 0表示成功, 1表示异常
	 */
	private int code;

	/**
	 * 消息串，当result为0时，resultdesc一般为空；当result为其它值时，resultdesc为错误信息。
	 */
	private String msg;

	/**
	 * 业务数据
	 */
	private Object data;

	/**
	 * 
	 */
	public JsonResponseVo() {

	}

	public JsonResponseVo(String op,int code,String msg,Object data) {
		this.op = op;
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public JsonResponseVo(int code,String msg,Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

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

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}

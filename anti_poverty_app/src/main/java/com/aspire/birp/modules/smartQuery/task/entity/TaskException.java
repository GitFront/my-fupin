package com.aspire.birp.modules.smartQuery.task.entity;

public class TaskException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Exception originalException;
	
	private String msg;
	

	public TaskException(Exception originalException,String msg) {
		this.originalException = originalException;
		this.msg = msg;
	}

	public Exception getOriginalException() {
		return originalException;
	}

	public String getMsg() {
		return msg;
	}
	
	
	@Override
	public String toString() {
		return "TaskException [e=" + originalException.toString() + ", msg=" + msg + "]";
	}



	//所有的自动任务异常
	public static final String CREATE_FILE_ERROR = "文件生成失败";
	
}

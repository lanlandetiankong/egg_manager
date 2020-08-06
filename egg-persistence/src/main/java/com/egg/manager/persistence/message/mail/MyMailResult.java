package com.egg.manager.persistence.message.mail;

import java.util.HashMap;
import java.util.Map;


public class MyMailResult extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public MyMailResult() {
		put("code", 0);
	}

	public static MyMailResult error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static MyMailResult error(String msg) {
		return error(500, msg);
	}

	public static MyMailResult error(int code, String msg) {
		MyMailResult r = new MyMailResult();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static MyMailResult ok(Object msg) {
		MyMailResult r = new MyMailResult();
		r.put("msg", msg);
		return r;
	}


	public static MyMailResult ok(Map<String, Object> map) {
		MyMailResult r = new MyMailResult();
		r.putAll(map);
		return r;
	}

	public static MyMailResult ok() {
		return new MyMailResult();
	}

	@Override
	public MyMailResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
package com.fenghuo.pojo;

import java.sql.Timestamp;

public class Reply {
	private int id;
	private int uid;
	private String content;
	private int zan;
	private Timestamp time;
	public Reply() {
		super();
	}
	public Reply(int uid, String content, int zan,Timestamp time) {
		super();
		this.setUid(uid);
		this.content = content;
		this.zan=zan;
		this.time = time;
	}
	public Reply(int id, int uid, String content, int zan, Timestamp time) {
		super();
		this.id = id;
		this.setUid(uid);		this.content = content;
		this.zan = zan;
		this.time = time;
	}
	@Override
	public String toString() {
		return "Reply [id=" + id + ",content=" + content
				+ ", time=" + time + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getZan() {
		return zan;
	}
	public void setZan(int zan) {
		this.zan = zan;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	

}

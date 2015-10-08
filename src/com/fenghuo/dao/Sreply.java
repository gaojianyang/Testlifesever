package com.fenghuo.dao;

import java.sql.Timestamp;

public class Sreply {
private int id;
private int uid;
private String content;
private Timestamp time;
public Sreply(int id, int uid, String content, Timestamp time) {
	super();
	this.id = id;
	this.uid = uid;
	this.content = content;
	this.time = time;
}
public Sreply(int uid, String content, Timestamp time) {
	super();
	this.uid = uid;
	this.content = content;
	this.time = time;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getUid() {
	return uid;
}
public void setUid(int uid) {
	this.uid = uid;
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





}

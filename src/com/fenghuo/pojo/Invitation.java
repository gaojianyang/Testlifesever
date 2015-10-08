package com.fenghuo.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Invitation entity. @author MyEclipse Persistence Tools
 */

public class Invitation implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private String title;
	private String content;
	private Timestamp time;
	private Timestamp createtime;
	private Short type;
private Set<User> user=new HashSet<User>();

	// Constructors

	/** default constructor */
	public Invitation() {
	}

	/** full constructor */
	public Invitation( Integer uid,String title, String content,
			Timestamp time,Timestamp createtime, Short type) {
		this.uid=uid;
		this.title = title;
		this.content = content;
		this.time = time;
		this.createtime = createtime;
		this.type = type;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

}
package com.fenghuo.pojo;

import java.sql.Timestamp;

/**
 * Image entity. @author MyEclipse Persistence Tools
 */

public class Image implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer invid;
	private Integer uid;
	private String title;
	private String url;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public Image() {
	}

	/** full constructor */
	public Image(Integer invid, Integer uid,String title, String url,Timestamp time) {
		this.invid = invid;
		this.uid = uid;
		this.url = url;
		this.time=time;
		this.setTitle(title);
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInvid() {
		return this.invid;
	}

	public void setInvid(Integer invid) {
		this.invid = invid;
	}

	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
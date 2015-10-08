package com.fenghuo.pojo;

import java.sql.Timestamp;

/**
 * Message entity. @author MyEclipse Persistence Tools
 */

public class Message implements java.io.Serializable {

	// Fields

	private Integer id;
	private String content;
	private Integer fromid;
	private Integer toid;
	private short type;
	private Integer invid;
	private Timestamp time;
	

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** full constructor */
	public Message(String content, Timestamp time) {
		this.content = content;

		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Message(String content, Integer fromid, Integer toid, short type,
			Integer invid, Timestamp time) {
		super();
		this.content = content;
		this.setFromid(fromid);
		this.setToid(toid);
		this.type = type;
		this.invid = invid;
		this.time = time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}


	

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public Integer getInvid() {
		return invid;
	}

	public void setInvid(Integer invid) {
		this.invid = invid;
	}

	public Integer getFromid() {
		return fromid;
	}

	public void setFromid(Integer fromid) {
		this.fromid = fromid;
	}

	public Integer getToid() {
		return toid;
	}

	public void setToid(Integer toid) {
		this.toid = toid;
	}

}
package com.fenghuo.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Club entity. @author MyEclipse Persistence Tools
 */

public class Club implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer uid;
	private String name;
	private String introduce;
	private Integer head;
	private Timestamp time;
	private Integer invid;
    private Set<User> clubuser=new HashSet<User>();
	// Constructors

	/** default constructor */
	public Club() {
	}

	public Club(String name, String introduce, Integer head,
			Timestamp time) {
		super();
		this.name = name;
		this.introduce = introduce;
		this.head = head;
		this.time = time;
		
	}

	/** minimal constructor */
	public Club(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Club(Integer uid,  String name,
			String introduce, Integer head, Timestamp time,Integer invid) {
		
		this.uid=uid;
		this.name = name;
		this.introduce = introduce;
		this.head = head;
		this.time = time;
		this.invid=invid;
	}

	public Club(Integer uid, String name, String introduce,
			Integer head, Timestamp time) {
		super();
		this.uid = uid;
		this.name = name;
		this.introduce = introduce;
		this.head = head;
		this.time = time;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Integer getHead() {
		return this.head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	
	public Set<User> getClubuser() {
		return clubuser;
	}

	public void setClubuser(Set<User> clubuser) {
		this.clubuser = clubuser;
	}

	public Integer getInvid() {
		return invid;
	}

	public void setInvid(Integer invid) {
		this.invid = invid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

}
package com.fenghuo.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


/**
 * Sport entity. @author MyEclipse Persistence Tools
 */

public class Sport  implements java.io.Serializable {


    // Fields    

     private Integer id;
     private Integer uid;
     private String title;
     private String content;
     private Timestamp time;
     public Sport(Integer uid, String title, String content, Timestamp time,
			Timestamp createtime, Integer needperson) {
		super();
		this.uid = uid;
		this.title = title;
		this.content = content;
		this.time = time;
		this.createtime = createtime;
		this.needperson = needperson;
	}

	private Timestamp createtime;
     private Integer needperson;
     private Set<User> user=new HashSet<User>();
     

    // Constructors

    /** default constructor */
    public Sport() {
    }

	/** minimal constructor */
    public Sport(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public Sport(Integer id, Integer uid, String title, String content, Timestamp time, Timestamp createtime, Integer needperson) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.time = time;
        this.createtime = createtime;
        this.needperson = needperson;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return this.uid;
    }
    
    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Timestamp getCreatetime() {
        return this.createtime;
    }
    
    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    public Integer getNeedperson() {
        return this.needperson;
    }
    
    public void setNeedperson(Integer needperson) {
        this.needperson = needperson;
    }

	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}

	








}
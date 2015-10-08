package com.fenghuo.pojo;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String pass;
	private String gender;
	private Integer head;
	private String college;
	private String personal;
	private String level;
	private Timestamp time;
	
	private Set<Club> clubuser=new HashSet<Club>();
    private Set<Invitation> userinv=new HashSet<Invitation>();
    private Set<Sport> userspo=new HashSet<Sport>();
	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String name, String pass, String gender, Integer head,
			String college) {
		this.name = name;
		this.pass = pass;
		this.gender = gender;
		this.head = head;
		this.college = college;
	}

	/** full constructor */
	public User(String name, String pass, String gender, Integer head,
			String college, String personal, String level, Timestamp time) {
		this.name = name;
		this.pass = pass;
		this.gender = gender;
		this.head = head;
		this.college = college;
		this.personal = personal;
		this.level = level;
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

	public String getPass() {
		return this.pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getHead() {
		return this.head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}

	public String getCollege() {
		return this.college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getPersonal() {
		return this.personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Timestamp getTime() {
		return this.time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public Set<Club> getClubuser() {
		return clubuser;
	}

	public void setClubuser(Set<Club> clubuser) {
		this.clubuser = clubuser;
	}

	public Set<Invitation> getUserinv() {
		return userinv;
	}

	public void setUserinv(Set<Invitation> userinv) {
		this.userinv = userinv;
	}

	public Set<Sport> getUserspo() {
		return userspo;
	}

	public void setUserspo(Set<Sport> userspo) {
		this.userspo = userspo;
	}

}
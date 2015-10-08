package com.fenghuo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;


import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.User;
import com.fenghuo.utils.DButils;

public class UserDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 添加用户解决用户名是否存在
	public boolean saveUser(User user) {
		System.out.println(user.getCollege()+"_---__da");
		@SuppressWarnings("unchecked")
		List<String> name = hibernateTemplate.find(
				"select u.name from User u where u.name=? ", user.getName());
		if (name.size() != 0)

		{
			return false;
		}

		else {
			Integer id = (Integer) hibernateTemplate.save(user);

			if (id > 0) {
				return true;
			}
			return false;
		}
	}

	// 修改用户
	public boolean updateUser(User user) {
		try {
			hibernateTemplate.update(user);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// 删除用户
	public boolean deleteUser(int id) {
		User user = new User();
		user.setId(id);
		try {
			hibernateTemplate.delete(user);

		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		return true;

	}

	// 用户登录
	public User userLogin(String name, String pass) {
		try {List<User> list = hibernateTemplate.find("from User u where u.name=? and u.pass=?", new String[] {name,pass});
		if (list.size() != 0) {
			return list.get(0);
			
		} }catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		return null;}
		
		
		return null;

	}
	//按姓名查找用户
	public User queryUser(String name){
		
		List<User> list = hibernateTemplate.find("from User u where u.name=?",name);
		if (list.size() != 0) {
			return list.get(0);
		}return null;
	
		
	}
	//根据id查找用户
	public User idUser(int id){
		
		@SuppressWarnings("unchecked")
		List<User> list = hibernateTemplate.find("from User u where u.id=?",id);
		if (list.size() != 0) {
			System.out.println(list.get(0).getName()+"-----------");
			return list.get(0);
			
		}return null;
	}
     public int headUser(int id){
		
		@SuppressWarnings("unchecked")
		List<Integer> list = hibernateTemplate.find("select u.head from User u where u.id=?",id);
		if (list.size() != 0) {
			return list.get(0);
		}return 0;
	}
     public String nameUser(int id){
 		
 		@SuppressWarnings("unchecked")
 		List<String> list = hibernateTemplate.find("select u.name from User u where u.id=?",id);
 		if (list.size() != 0) {
 			return list.get(0);
 		}return null;
 	}
     
     public boolean adminUser(int uid){
		   
		   Connection conn;
		try {
			conn =DButils.getInstance().getConnection(); 
			PreparedStatement ps = conn.prepareStatement("insert into adminuser(uid) values (?)");
			ps.setInt(1, uid);
			 boolean execute = ps.execute();
			
			conn.close();
	   	
	   	return execute;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			 return false; 
   	
		}
			
		 
		   
	   }
     
     public boolean orAdmin(int uid){
    	 Connection conn;
    	 conn =DButils.getInstance().getConnection(); 
    	 PreparedStatement pstmt;
		try {
			pstmt = conn
					.prepareStatement("select uid from adminuser where uid=?");
			pstmt.setInt(1, uid);
	 		ResultSet rs = pstmt.executeQuery();
	 		boolean ishave = rs.next();
	 		rs.close();
	 		conn.close();
	 		return ishave;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
 	
     }
     
     
     public boolean adminQuxiao(int uid){
		   
		   Connection conn;
		try {
			conn =DButils.getInstance().getConnection(); 
			PreparedStatement ps = conn.prepareStatement("delete from adminuser where uid=?");
			ps.setInt(1, uid);
			 boolean execute = ps.execute();
			
		conn.close();
	   	
	   	return execute;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
	
		   
	   }
	    
     



}

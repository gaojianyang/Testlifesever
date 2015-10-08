package com.fenghuo.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.DButils;

public class UserUser {
	//关注用户看是否已经关注
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	public boolean guanzhuUser(int uid,int fid) throws ClassNotFoundException, SQLException{
		boolean	 ishave=ishaveUser(uid, fid);
		 
		 if(ishave==false){
			 Timestamp time= new Timestamp(System.currentTimeMillis());
			 Message message=new Message("关注了你", uid, fid, (short)0, 0, time);
			 saveMessage(message);
		Connection conn=DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("insert into useruser(uid,fid) values (?,?)");
		ps.setInt(1, uid);
		ps.setInt(2, fid);
		int in = ps.executeUpdate();
		conn.close();
		if(in>0){
			return true; }
		return false;
		}return false;
	}
	//取消关注
	public boolean quxiaoUser(int uid,int fid) throws ClassNotFoundException, SQLException{
		boolean ishave=ishaveUser(uid, fid);
		 if(ishave==true){
		Connection conn=DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("delete from useruser where uid=? and fid=?");
		ps.setInt(1, uid);
		ps.setInt(2, fid);
		int in = ps.executeUpdate();
		conn.close();
		if(in>0){
			return true; }
		return false;
		}return false;
	}
	
	//看是否已经关注
	public boolean ishaveUser(int uid,int fid) throws ClassNotFoundException, SQLException{
		
		 
		Connection conn =DButils.getInstance().getConnection(); 
		
		PreparedStatement pstmt=conn.prepareStatement("select uid from useruser where uid=? and fid=?");
		pstmt.setInt(1, uid);
		pstmt.setInt(2, fid);
		ResultSet rs = pstmt.executeQuery();
	    boolean ishave = rs.next();
	    
		rs.close();
		conn.close();
		return ishave;
	}
	
	
	
	//看是否已经关注
		public boolean ishaveInv(int uid,int invid) throws ClassNotFoundException, SQLException{
			
			 
			Connection conn = DButils.getInstance().getConnection(); 
			PreparedStatement pstmt=conn.prepareStatement("select uid invuser where uid=? and fid=?");
			pstmt.setInt(1, uid);
			pstmt.setInt(2, invid);
			ResultSet rs = pstmt.executeQuery();
			boolean	 ishave=rs.next();
			rs.close();
			conn.close();
			
			return ishave;
		}
		
		
		public boolean guanzhuInv(int uid,int invid) throws ClassNotFoundException, SQLException{
			boolean ishave=ishaveInv(uid, invid);
			 if(ishave==false){
			Connection conn=DButils.getInstance().getConnection(); 
			PreparedStatement ps = conn.prepareStatement("insert into invuser(uid,invid) values(?,?)");
			ps.setInt(1, uid);
			ps.setInt(2, invid);
			boolean in = ps.execute();
			conn.close();
			
				return in; }else{return false;}
		}
		
		
		//取消关注
		public boolean quxiaoInv(int uid,int invid) throws ClassNotFoundException, SQLException{
			boolean ishave=ishaveUser(uid, invid);
			 if(ishave==false){
			Connection conn=DButils.getInstance().getConnection(); ;
			PreparedStatement ps = conn.prepareStatement("deldete from invuser where uid=? and invid=?");
			ps.setInt(1, uid);
			ps.setInt(2, invid);
			ps.execute();
			conn.close();
				return true; 
			
			}return false;
		}
		
		
		//查找关注的帖子
		public Set<Invitation> userInv(int uid) {
			
			Set<Invitation> userinv = idUser(uid).getUserinv();
			return userinv;
                		
		}
		//查找关注的用户
		public List<User> userGuanZhu(int uid){
		    List<Integer> list=new ArrayList<Integer>();
			List<User> list2=new ArrayList<User>();
			
			
			try {
				Connection conn = DButils.getInstance().getConnection(); 
				PreparedStatement	pstmt = conn.prepareStatement("select fid from useruser where uid=?");
				pstmt.setInt(1, uid);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					int fid=rs.getInt(1);
					list.add(fid);}
				rs.close();
					conn.close();
					if(list.size()!=0){
						
						for(int id:list){
							idUser(id);
							
						list2.add(idUser(id));	
						}
					return list2;	
					}else{return null;
			
					}
					
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		public List<User> topUser(){
		    List<Integer> list=new ArrayList<Integer>();
			List<User> list2=new ArrayList<User>();
			
			
			try {
				Connection conn =DButils.getInstance().getConnection(); 
				PreparedStatement	pstmt = conn.prepareStatement("select fid,count(*) as c from useruser group by fid order by c desc limit 0,20");
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					int fid=rs.getInt(1);
					list.add(fid);}
					rs.close();
					conn.close();
						for(int id:list){
							idUser(id);
						list2.add(idUser(id));	
						}
					return list2;	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		//查找关注的用户
		public List<User> userFans(int fid){
		    List<Integer> list=new ArrayList<Integer>();
			List<User> list2=new ArrayList<User>();
			try {
				Connection conn = DButils.getInstance().getConnection(); 
			PreparedStatement pstmt;
				pstmt = conn.prepareStatement("select uid from useruser where fid=?");
				pstmt.setInt(1, fid);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
				int uid=rs.getInt(1);
				list.add(uid);
				rs.close();
				conn.close();
				if(list.size()!=0){
						for(int id:list){
							idUser(id);
						list2.add(idUser(id));	
						}
					return list2;	
					}else{return null;
						
						
					}
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} 
			return null;
		}
		public User idUser(int id){
			
			@SuppressWarnings("unchecked")
			List<User> list = hibernateTemplate.find("from User u where u.id=?",id);
			if (list.size() != 0) {
				return list.get(0);
			}return null;
		
			
		}
		public long guanzhuCount(int fid){
			long	guanzhucount=0;
				Connection conn;
				try {
					conn = DButils.getInstance().getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement("select count(*) from useruser where fid=?");
				pstmt.setInt(1, fid);
				ResultSet rs = pstmt.executeQuery();
			
				while(rs.next()){
					guanzhucount=rs.getLong(1);
					System.out.println(guanzhucount+"------");
				}
				rs.close();
			conn.close();
			return guanzhucount;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
return 0;			}
			
			
			
			
		}
		public boolean saveMessage(Message message) {
			Integer id = (Integer) hibernateTemplate.save(message);
			if (id > 0) {
				return true;
			}
			return false;
		}

}

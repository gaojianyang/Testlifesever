//package com.fenghuo.dao;
//
//import java.sql.Connection;
//
//
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import org.springframework.orm.hibernate3.HibernateTemplate;
//
//
//import com.fenghuo.utils.DButils;
//
//public class ClubUserDao {
//	private HibernateTemplate hibernateTemplate;
//
//	public HibernateTemplate getHibernateTemplate() {
//		return hibernateTemplate;
//	}
//
//	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
//		this.hibernateTemplate = hibernateTemplate;
//	}
//

//	// 加入社团
//	
//	public boolean joinClub(int cid, int uid) throws ClassNotFoundException,
//			SQLException {
//
//		Connection conn = DButils.getConn();
//
//		PreparedStatement pstmt = conn
//				.prepareStatement("select uid clubuser where cid=? and uid=?");
//		pstmt.setInt(1, cid);
//		pstmt.setInt(2, uid);
//		ResultSet rs = pstmt.executeQuery();
//		boolean ishave = rs.isFirst();
//		DButils.closeConn(conn);
//		if (ishave == true) {
//
//			PreparedStatement ps = conn
//					.prepareStatement("insert into clubuser(cid,uid) values (?,?)");
//			ps.setInt(1, cid);
//			ps.setInt(2, uid);
//			int in = ps.executeUpdate();
//			if (in > 0) {
//				return true;
//			}
//			DButils.closeRS(rs);
//			return false;
//		}
//		return false;
//	}
//
//	// 退出社团
//	public boolean quitClub(int cid, int uid) throws ClassNotFoundException,
//			SQLException {
//
//		Connection conn = DButils.getConn();
//
//		PreparedStatement pstmt = conn
//				.prepareStatement("select uid clubuser where cid=? and uid=?");
//		pstmt.setInt(1, cid);
//		pstmt.setInt(2, uid);
//		ResultSet rs = pstmt.executeQuery();
//		boolean ishave = rs.isFirst();
//		DButils.closeRS(rs);
//		try {
//			if (ishave == false) {
//				PreparedStatement ps = conn
//						.prepareStatement("delete from clubuser where cid=？and uid=?");
//				ps.setInt(1, cid);
//				ps.setInt(2, uid);
//				ps.executeUpdate();
//			
//				DButils.closeConn(conn);
//				return true;
//			}
//			return false;
//		} catch (Exception e) {
//			return false;
//		}
//
//	}
//	

	// 根据uid查询club

//	public Set<Club> queryUserClub(int uid,User user) 
//			 {
//		Set<Club> clubuser=new HashSet<Club>();
//	  clubuser = user.getClubuser();
//		return clubuser;
//	
//	}
	//根据cid查找user
	
//	public Set<User> queryClubUser(int cid){
//
//		
//		if(clubDao.queryClub(cid)!=null){
//			Set<User> clubuser=new HashSet<User>();
//		 clubuser = clubDao.queryClub(cid).getClubuser();
//			return clubuser;
//		}
//		
//		return null;
//	}
	
	//根据cid查找club
	
	

//		Connection conn = DButils.getConn();
//		List<Integer> list = new ArrayList<Integer>();
//		List<Club> list2 = new ArrayList<Club>();
//		PreparedStatement pstmt = conn
//				.prepareStatement("select cid clubuser where uid=?");
//		pstmt.setInt(1, uid);
//		ResultSet rs = pstmt.executeQuery();
//		while (rs.next()) {
//			int cid = rs.getInt(1);
//			list.add(cid);
//		}
//		DButils.closeRS(rs);
//		DButils.closeConn(conn);
//		if (list.size() != 0) {
//			for (Integer id : list) {
//				
//				if (clubDao.queryClub(id) != null) {
//					list2.add(clubDao.queryClub(id));
//				}
//				;
//
//			}
//
//			return list2;
//		}
//
//		return null;
//
//	}

//}
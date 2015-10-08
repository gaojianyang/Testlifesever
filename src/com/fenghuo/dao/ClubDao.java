package com.fenghuo.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Club;
import com.fenghuo.pojo.Invitation;
import com.fenghuo.utils.DButils;

public class ClubDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 判斷社團名字是否重複组建社团并建立社团贴

	public Club createClub(Club club) throws ClassNotFoundException, SQLException {
		@SuppressWarnings("unchecked")
		List<Club> list=hibernateTemplate.find("from Club c where c.name=?", club.getName());
		
		if(list.size()==0){
			Invitation invitation = new Invitation();
			invitation.setUid(club.getUid());
			invitation.setTitle(club.getName());
			invitation.setContent(club.getIntroduce());
			invitation.setCreatetime(club.getTime());
			invitation.setTime(club.getTime());
			invitation.setType((short) 5);
			Integer invid=(Integer) hibernateTemplate.save(invitation);
		    club.setInvid(invid);
			invitation.setId(invid);
		Integer id = (Integer) hibernateTemplate.save(club);
		if (id > 0) {
			Connection conn =DButils.getInstance().getConnection();  ;
			Statement stmt=conn.createStatement();
			
			stmt.execute("create table inv_"+invid+"(id integer primary key auto_increment,uid integer,content varchar(200),zan integer,time datetime);");
			PreparedStatement ps = conn.prepareStatement("insert into inv_"+invitation.getId()
					+ "(uid,content,zan,time) values (?,?,?,?)");
			 ps.setInt(1,invitation.getUid());
			 ps.setString(2, invitation.getContent());
			 ps.setInt(3, 0);
			 ps.setTimestamp(4, invitation.getTime());
		     ps.executeUpdate();
			 PreparedStatement psmt = conn.prepareStatement("insert into invuser(uid,invid) values (?,?)");
			 psmt.setInt(1, club.getUid());
			psmt.setInt(2, club.getInvid());
			int in2 = psmt.executeUpdate();
			conn.close();
			if (in2> 0) {
				return club;
		}
			
			return null;}
		return null;
		}
		return null;
	}

	// 解散社团
	public boolean deleteClub(int id, int invid) {
		Club club = new Club();
		club.setId(id);
		try {
			hibernateTemplate.delete(club);
			Invitation invitation = new Invitation();
			invitation.setId(invid);
			hibernateTemplate.delete(invitation);
			Connection conn = DButils.getInstance().getConnection(); 
			Statement stmt=conn.createStatement();
			stmt.execute("DROP TABLE inv_"+invid);
			PreparedStatement ps = conn.prepareStatement("delete invuser where invid=？");
			ps.setInt(1,invid);
			int i = ps.executeUpdate();
		   conn.close();
			if(i>0){
				return true;
				
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	

	}
	//根据uid查找社团
	public boolean queryClub(int uid,int cid){
		@SuppressWarnings("unchecked")
		List<Club> list=hibernateTemplate.find("from Club c where c.id=? and c.uid=?",new Object[]{cid,uid});
      if(list.size()!=0){
    	  return true;
      }
      return false;
	}
	// 按标题查询帖子
		public List<Club> titleclubInvitation(String title,int pageNo) {
			Session session=hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery("from Club c where c.name like ? order by c.time desc");
			query.setString(0, "%"+title+"&");
			query.setFirstResult((pageNo-1)*20);
			query.setMaxResults(20);
			@SuppressWarnings("unchecked")
			List<Club> list=query.list();
			session.close();
			return list;
			
		}
	
	//所有社团按时间排
	public List<Club> allInvitation(int pageNo) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Club c order by c.time desc");
		
		query.setFirstResult((pageNo-1)*20);
		query.setMaxResults(20);
		@SuppressWarnings("unchecked")
		List<Club> list=query.list();
	session.close();
	hibernateTemplate.getSessionFactory().close();
		return list;

	}
	public List<Club> titleClub(String title,int pageNo) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Club c where c.title like ? order by c.time desc");
		query.setString(0, "%"+title+"%");
		query.setFirstResult((pageNo-1)*20);
		query.setMaxResults(20);
		@SuppressWarnings("unchecked")
		List<Club> list=query.list();
		
		session.close();
		hibernateTemplate.getSessionFactory().close();

		
		return list;
		
	}
	//所有社团按人数排
//	public List<Club> numInvitation(){
//		
//		List<Integer> list=new ArrayList<Integer>();
//		List<Long> listl=new ArrayList<Long>();
//		Connection conn;
//		try {
//			conn = DButils.getConn();
//			PreparedStatement ps=conn.prepareStatement("select uid,count(*) from invuser ");
//			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	
	
	
	
	
	
	//根据cid查找社团
	public Club queryClub(int cid){
		try {@SuppressWarnings("unchecked")
		List<Club> alllist = hibernateTemplate
				.find("from Club c where c.cid=?",cid);
		
		if(alllist.size()!=0){
			
			return alllist.get(0);}
		} catch (Exception e) {
			// TODO: handle exception
		return null;}
		return null;
	}
	public Club queryinvClub(int invid){
		try {@SuppressWarnings("unchecked")
		List<Club> alllist = hibernateTemplate
				.find("from Club c where c.invid=?",invid);
		if(alllist.size()!=0){
			return alllist.get(0);}
		} catch (Exception e) {
			// TODO: handle exception
		return null;}
		return null;
	}
	//总共拥有的社团
	public long clubCount(){
    List find = hibernateTemplate.find("select count(*) from Club");
		long allnumber= (Long) find.get(0);
		return allnumber;
              }
	public long titleCount(String title){
	    List find = hibernateTemplate.find("select count(*) from Club c where c.title like ?", "%"+title+"&");
			long allnumber= (Long) find.get(0);
			return allnumber;
	              }

}	
	


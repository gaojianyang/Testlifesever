package com.fenghuo.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.Sport;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.DButils;
import com.fenghuo.utils.Timeutils;

public class SportDao {
	
	private HibernateTemplate hibernateTemplate;
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 创建活动 并建立活动贴
	public Sport createSport(Sport sport) throws ClassNotFoundException,
			SQLException {
		Integer id = (Integer) hibernateTemplate.save(sport);
		if (id > 0) {
			Connection conn = DButils.getInstance().getConnection(); 
			Statement stmt = conn.createStatement();
			 stmt.execute("create table spo_"
					+ id
					+ "(id integer primary key auto_increment,uid integer,content varchar(1000),time datetime);");
				PreparedStatement pstmt2 = conn
						.prepareStatement("insert into spo_"+id+"(uid,content,time) values (?,?,?)");
				pstmt2.setInt(1, sport.getUid());
				pstmt2.setString(2, "活动主题: "+sport.getTitle()+" 注意事项： "+sport.getContent()+" 预计时间："+Timeutils.monthToString(sport.getTime()));
				pstmt2.setTimestamp(3, sport.getCreatetime());
				pstmt2.executeUpdate();
			PreparedStatement pstmt = conn
					.prepareStatement("insert into spouser(uid,sid) values (?,?)");
			pstmt.setInt(1, sport.getUid());
			pstmt.setInt(2, id);
			int in = pstmt.executeUpdate();
		conn.close();
		
		if(in>0){
			sport.setId(id);
			return sport;}
		else{return null;}

		}
		return null;
	}

	// 结束活动
	public boolean deleteSport(int id) {
		Sport sport = new Sport();
		sport.setId(id);
		try {
			hibernateTemplate.delete(sport);
			Connection conn = DButils.getInstance().getConnection(); 
			
			Statement stmt=conn.createStatement();
			stmt.execute("DROP TABLE spo_"+id);
			
			PreparedStatement pstmt = conn
					.prepareStatement("delete spouser where sid=?");
			pstmt.setInt(1, id);
			pstmt.execute();
			conn.close();
			
			return pstmt.execute();
		} catch (Exception e) {
			return false;
		}
	}
	public void UpSport(Sport sport) {
	
		
			hibernateTemplate.update(sport);}
	

	// 参加活动确定下是否在参加表里面 并将sport人数减一
	public boolean joinSport(int uid, int sid, int suid, Timestamp time)
			throws ClassNotFoundException, SQLException {
		
		boolean	ishave = ishavesport(uid, sid);

			if (ishave == false) {

				@SuppressWarnings("unchecked")
				List<Sport> list = hibernateTemplate.find(
						"from Sport s where sid=?", sid);
				if (list.size() != 0) {
					int num = list.get(0).getNeedperson();

					if (num > 0) {
						list.get(0).setNeedperson(num--);
						hibernateTemplate.update(list.get(0));
						Connection conn = DButils.getInstance().getConnection(); 

						PreparedStatement pstmt = conn
								.prepareStatement("insert into spouser(uid,sid) values (?,?)");
						pstmt.setInt(1, uid);
						pstmt.setInt(2, sid);
						int in = pstmt.executeUpdate();
						conn.close();
						Message message = new Message("参加了你的活动",
								uid, suid, (short) 0, sid, time);
						saveMessage(message);
						if (in > 0) {
							return true;
						} else {
							return false;
						}
					}
					return false;
				}

				return false;
			}

			return false;
	
	}

	// 取消参加确定下是否在参加表里面 sport人数加一
	public boolean quxiaoSport(int uid, int sid, int suid, Timestamp time)
			throws ClassNotFoundException, SQLException {
		try {

			if (uid != suid) {

			boolean	ishave = ishavesport(uid, sid);
				if (ishave == true) {

					@SuppressWarnings("unchecked")
					List<Sport> list = hibernateTemplate.find(
							"from Sport s where sid=?", sid);
					if (list.size() != 0) {
						int num = list.get(0).getNeedperson();

						list.get(0).setNeedperson(num+1);
						hibernateTemplate.update(list.get(0));
						Connection conn = DButils.getInstance().getConnection(); 

						PreparedStatement pstmt = conn
								.prepareStatement("delete spouser where uid=? and sid=?");
						pstmt.setInt(1, uid);
						pstmt.setInt(2, sid);
						;

						conn.close();
						Message message = new Message("I am quit your sport",
								uid, suid, (short) 0, 0, time);
						saveMessage(message);

							return pstmt.execute();
						}  return false;
					
          
				}

				return false;
			}
		} catch (Exception e) {
			return false;
		}

		return false;

	}

	// 回复活动 看是不是楼主是的话给每个参加的人发消息

	public boolean replySport(Sreply reply, int sid)
			throws ClassNotFoundException, SQLException {
		List<Integer> list2 = queryUid(sid);
		if (list2.size() != 0) {
	for (Integer id : list2) {
		if(reply.getId()!=id){
				Message message = new Message(reply.getContent(), reply.getUid(), id,(short)4, sid, reply.getTime());
       saveMessage(message);}
	}
		}else{}

		Connection conn =DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("insert into spo_" + sid
				+ "(uid,content,time) values(?,?,?)");

		ps.setInt(1, reply.getUid());
		ps.setString(2,reply.getContent());
		ps.setTimestamp(3, reply.getTime());

		int in = ps.executeUpdate();
		ps.close();
		conn.close();

		if (in > 0) {
			return true;
		}else {
		
		return false;}

	}

	// 确认是否在参加表里

	public boolean ishavesport(int uid, int sid) throws ClassNotFoundException,
			SQLException {

		Connection conn = DButils.getInstance().getConnection(); 

		PreparedStatement pstmt = conn
				.prepareStatement("select uid from spouser where uid=? and sid=?");
		pstmt.setInt(1, uid);
		pstmt.setInt(2, sid);
		ResultSet rs = pstmt.executeQuery();
		boolean ishave = rs.next();
		rs.close();
		conn.close();
		return ishave;

	}

	// 根据sid查找uid查询谁参加了

	public Set<User> queryUser(int sid) 
			{
		if(idSport(sid)!=null){
			Set<User> userspo=new HashSet<User>();
		 userspo=idSport(sid).getUser();
			return userspo;
		}
		return null;
	
		
		
//		List<Integer> list = new ArrayList<Integer>();
//
//		Connection conn = DButils.getConn();
//
//		PreparedStatement pstmt = conn
//				.prepareStatement("select uid from joinsport where sid=?");
//
//		pstmt.setInt(1, sid);
//
//		ResultSet rs = pstmt.executeQuery();
//
//		while (rs.next()) {
//
//			int id = rs.getInt(1);
//			list.add(id);
//		}
//		DButils.closeRS(rs);
//		DButils.closeConn(conn);
//		return list;

	}
	public List<Integer> queryUid(int invid) 
	 {

List<Integer> list = new ArrayList<Integer>();

Connection conn;
try {
	conn = DButils.getInstance().getConnection(); 

	PreparedStatement pstmt = conn
			.prepareStatement("select uid from spouser where sid=?");

	pstmt.setInt(1, invid);

	ResultSet rs = pstmt.executeQuery();

	while (rs.next()) {

		int id = rs.getInt(1);
		list.add(id);
	}
	rs.close();
	conn.close();
}catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

return list;

}
	//根据uid查找sid

//	public List<Integer> querySid(int uid) throws ClassNotFoundException,
//			SQLException {
//		List<Integer> list = new ArrayList<Integer>();
//
//		Connection conn = DButils.getConn();
//
//		PreparedStatement pstmt = conn
//				.prepareStatement("select sid from joinsport where uid=?");
//
//		pstmt.setInt(1, uid);
//
//		ResultSet rs = pstmt.executeQuery();
//
//		while (rs.next()) {
//
//			int id = rs.getInt(1);
//			list.add(id);
//		}
//		
//		
//		
//		DButils.closeRS(rs);
//		DButils.closeConn(conn);
//		
//		
//		
//		return list;
//
//	}
	
	

	// 根据title查找活动（分页）
	public List<Sport> titleSport(String title,int pageNo) {
	
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Sport s where s.title like ? and s.time>? and s.needperson>0 order by s.time");
		query.setString(0, "%"+title+"%");
		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60);
		query.setTimestamp(1, time);
		query.setFirstResult((pageNo-1)*30);
		query.setMaxResults(30);
		@SuppressWarnings("unchecked")
		List<Sport> list=query.list();
		
		session.close();
		hibernateTemplate.getSessionFactory().close();
		
		return list;
		
	}
	
	
	
	
	
	// 显示当日日期前一小时后的所有活动（分页）
	
	public List<Sport> allSport(int pageNo) {
		
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Sport s where s.time>? and s.needperson>-1 order by s.time asc");
	
		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60);
		query.setTimestamp(0, time);
		query.setFirstResult((pageNo-1)*30);
		query.setMaxResults(30);
		@SuppressWarnings("unchecked")
		List<Sport> list=query.list();
		session.close();
		return list;
		
	}
	//根据sid查找sport
	public Sport idSport(int sid){
		try {
			@SuppressWarnings("unchecked")
			List<Sport> list=hibernateTemplate.find("from Sport spo where spo.id=?",sid);
         return list.get(0);
		} catch (Exception e) {
          return null;		}
	}
		
	//查找回复总数	
   public int sportCount(int sid){
	   
	   Connection conn;
	try {
		conn =DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("select count(*) from spo_"+ sid );
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int count=rs.getInt(1);
			return count;
		}
		rs.close();
		conn.close();
   	return 0;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		return 0;
	}
		
	
	   
   }
 public boolean zhiSpo(int sid){
	   
	   Connection conn;
	try {
		conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("insert into zhispo(sid,time) values (?,?)");
		ps.setInt(1, sid);
		Timestamp time=new Timestamp(System.currentTimeMillis());
		ps.setTimestamp(2, time);
		 boolean execute = ps.execute();
		
		conn.close();
   	
   	return execute;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	 return false;  
	   
   }
	public List<Sport> TopSport() {
	List<Sport> list=new ArrayList<Sport>();
	
		try {	Connection conn;
		int usercount;
		
			conn = DButils.getInstance().getConnection();  
			PreparedStatement ps=conn.prepareStatement("select sid from zhispo order by time limit 0,2");
ResultSet rs = ps.executeQuery();

while (rs.next()) {
	usercount=	rs.getInt(1);
List<Sport> inv=hibernateTemplate.find("from Sport s where s.id=?",usercount);
	if(inv.size()!=0){list.add(inv.get(0));}
}
rs.close();
conn.close();

return list;
 
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	
		
	}
	
   
   
   
   //查找所有回复按时间排序
   
   public List<Sreply> sreplyQuery(int sid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Sreply> list = new ArrayList<Sreply>();
		Connection conn =DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("select * from spo_"
				+ sid + " order by time  limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			Timestamp time = rs.getTimestamp(4);
			Sreply reply = new Sreply(id, uid, content,time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}
   public List<Sreply> sreplylouQuery(int sid,int suid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Sreply> list = new ArrayList<Sreply>();
		Connection conn =DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("select * from spo_"
				+ sid + "where uid=? order by time limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ps.setInt(1, suid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			Timestamp time = rs.getTimestamp(4);
			Sreply reply = new Sreply(id, uid, content,time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}
   public List<Sreply> sreplydaoQuery(int sid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Sreply> list = new ArrayList<Sreply>();
		Connection conn =DButils.getInstance().getConnection(); 
		PreparedStatement ps = conn.prepareStatement("select * from spo_"
				+ sid + " order by time desc limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			Timestamp time = rs.getTimestamp(4);
			Sreply reply = new Sreply(id, uid, content,time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}
   //sport count
   
 //查询总数
 	public long queryCount(){
 		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60);
 		List find = hibernateTemplate.find("select count(*) from Sport s where s.time>? and s.needperson>0 order by s.time",time);
 		
 		long allnumber=(Long) find.get(0);
 		return allnumber;
 		
 	}
 	public boolean saveMessage(Message message) {
		Integer id = (Integer) hibernateTemplate.save(message);
		if (id > 0) {
			return true;
		}
		return false;
	}
	
	
	
	
	
	
}

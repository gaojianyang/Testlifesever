package com.fenghuo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.Reply;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.DButils;

public class ReplyDao {

	private HibernateTemplate hibernateTemplate;
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 增加帖子回復
	public int addReply(int invid, int rid,short type, Reply reply)
			throws ClassNotFoundException, SQLException {
	
			List<Integer> list2 = queryUid(invid);
			if (list2.size() != 0) {
		for (Integer id : list2) {
			if(rid!=id){
					Message message = new Message(reply.getContent(), rid, id,
							type, invid, reply.getTime());
	       saveMessage(message);}
		
		}
			}


		Connection conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("insert into inv_" + invid
				+ "(uid,content,zan,time) values(?,?,?,?)");
		ps.setInt(1, reply.getUid());
		ps.setString(2, reply.getContent());
		ps.setInt(3, reply.getZan());
		ps.setTimestamp(4, reply.getTime());
		int in = ps.executeUpdate();
		conn.close();
		try {
			if (in > 0) {
				@SuppressWarnings("unchecked")
				List<Invitation> list = hibernateTemplate.find(
						"from Invitation inv where inv.id=?", invid);
				Invitation inv = list.get(0);
				inv.setTime(reply.getTime());
				hibernateTemplate.update(inv);
           return in;
				
			}// 更新帖子最新时间
			
		} catch (Exception e) {
			return 0;
		}

		return 0;

	}

	// 赞reply
	public boolean zanReply(int invid, Reply reply)
			throws ClassNotFoundException, SQLException {
		int num = reply.getZan();
		Connection conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("update inv_" + invid
				+ " set zan=? where id=?");

		ps.setInt(1, num+1);
		ps.setInt(2, reply.getId());

		int in = ps.executeUpdate();
conn.close();
		if (in > 0) {
			return true;
		}
		return false;

	}

	// 按赞排序
	public List<Reply> zanQuery(int invid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Reply> list = new ArrayList<Reply>();
		Connection conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select * from inv_"
				+ invid + " order by zan desc limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			int zan = rs.getInt(4);
			Timestamp time = rs.getTimestamp(5);
			Reply reply = new Reply(id, uid, content, zan, time);
			list.add(reply);
		}

	rs.close();
	conn.close();
		return list;
	}

	// 按时间daoxu排序
	public List<Reply> timedaoQuery(int invid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Reply> list = new ArrayList<Reply>();
		Connection conn =DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select * from inv_"
				+ invid + " order by time desc limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			int zan = rs.getInt(4);
			Timestamp time = rs.getTimestamp(5);
			Reply reply = new Reply(id, uid, content, zan, time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}
	public List<Reply> timezhengQuery(int invid, int pageNo)
			throws ClassNotFoundException, SQLException {
		List<Reply> list = new ArrayList<Reply>();
		Connection conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select * from inv_"
				+ invid + " order by time limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			int zan = rs.getInt(4);
			Timestamp time = rs.getTimestamp(5);
			Reply reply = new Reply(id, uid, content, zan, time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}

	// 根据id搜回复
	public Reply reidQuery(int invid, int reid) throws ClassNotFoundException,
			SQLException {
		List<Reply> list = new ArrayList<Reply>();
		Connection conn =DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select * from inv_"
				+ invid + " where id=?");
		ps.setInt(1, reid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()){
			int uid = rs.getInt(2);
			String content = rs.getString(3);
			int zan = rs.getInt(4);
			Timestamp time = rs.getTimestamp(5);
			Reply reply = new Reply(reid, uid, content, zan, time);
			list.add(reply);
		}
		rs.close();
		conn.close();

		return list.get(0);

	}
	
	
	
	
	

	public List<Integer> queryUid(int invid) 
			 {
		
		List<Integer> list = new ArrayList<Integer>();

		Connection conn;
		try {
			conn =DButils.getInstance().getConnection();  ;

			PreparedStatement pstmt = conn
					.prepareStatement("select uid from invuser where invid=?");

			pstmt.setInt(1, invid);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				int id = rs.getInt(1);
				list.add(id);
			}
		    rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}
//查找楼主回复
	public List<Reply> louZhuQuery(int invid, int pageNo,int uid)
			throws ClassNotFoundException, SQLException {
		List<Reply> list = new ArrayList<Reply>();
		Connection conn =DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select * from inv_"
				+ invid + " where uid=? order by time desc limit " + (pageNo - 1) * 20
				+ ", " + 20);
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String content = rs.getString(3);
			int zan = rs.getInt(4);
			Timestamp time = rs.getTimestamp(5);
			Reply reply = new Reply(id, uid, content, zan, time);
			list.add(reply);
		}
		rs.close();
		conn.close();
		return list;
	}
	
//该贴回复人数
    public long replyCount(int invid){
    	
    	try {   Connection conn =DButils.getInstance().getConnection(); 
    	
		PreparedStatement ps = conn.prepareStatement("select count(*) from inv_"+ invid );
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		long count=rs.getLong(1);
		rs.close();
		conn.close();
		
		return count;
			
		}
			
		}  catch (SQLException e) {

			return 0;		}
    	return 0;
    	
    }
    
    
    
 public long replyUidCount(int invid,int uid){
    	
    	try {   Connection conn = DButils.getInstance().getConnection();  
		PreparedStatement ps = conn.prepareStatement("select count(*) from inv_"+ invid+" where uid=?" );
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			long count=rs.getLong(1);
			rs.close();
			conn.close();
			return count;
		}
			
		}  catch (SQLException e) {

			return 0;		}
    	
    	return 0;
    }
    public boolean saveMessage(Message message) {
		Integer id = (Integer) hibernateTemplate.save(message);
		if (id > 0) {
			return true;
		}
		return false;
	}


	
	
	

	
	
	
//根据id查询帖子
@SuppressWarnings("unchecked")
public Invitation findInvitation(int invid){
	try {List<Invitation> list=new ArrayList<Invitation>();
		list=hibernateTemplate.find("from Invitation inv where inv.id=?",invid);
     return list.get(0);
	} catch (Exception e) {
      return null;		}
}
	
	
	
	
}

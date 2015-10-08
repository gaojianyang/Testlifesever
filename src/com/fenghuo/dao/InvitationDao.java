package com.fenghuo.dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Image;
import com.fenghuo.pojo.Invitation;
import com.fenghuo.utils.DButils;


public class InvitationDao {
	private HibernateTemplate hibernateTemplate;
	

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 查询所有帖子..后面写分页查询
	public List<Invitation> allInvitation(int pageNo) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Invitation invitation where type<5 order by invitation.time desc");
		query.setFirstResult((pageNo-1)*30);
		query.setMaxResults(30);
		@SuppressWarnings("unchecked")
		List<Invitation> list=query.list();
		
		session.close();
		 hibernateTemplate.getSessionFactory().close();
		
		return list;

	}

	// 按赞数目查询所有帖子
//	public List<Invitation> zanInvitation(int pageNo) {
//	
//		Session session=hibernateTemplate.getSessionFactory().openSession();
//		Query query = session.createQuery("from Invitation invitation order by invitation.zan desc");
//		query.setFirstResult((pageNo-1)*30);
//		query.setMaxResults(30);
//		@SuppressWarnings("unchecked")
//		List<Invitation> list=query.list();
//		
//		session.close();
//		
//		return list;
//
//		
//	}
	public boolean zhiInv(int invid){
		   
		   Connection conn;
		try {
			conn = DButils.getInstance().getConnection();  
			PreparedStatement ps = conn.prepareStatement("insert into zhiding(invid,time) values (?,?)");
			ps.setInt(1, invid);
			Timestamp time=new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(2, time);
			 boolean execute = ps.execute();
			
			conn.close();
	   	
	   	return execute;
		}  catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		 return false;  
		   
	   }
	
//zhidingtie
	public List<Invitation> TopInvitation() {
	List<Invitation> list=new ArrayList<Invitation>();
	
		try {	Connection conn;
		int usercount;
		
			conn = DButils.getInstance().getConnection();  
			PreparedStatement ps=conn.prepareStatement("select invid from zhiding order by time desc limit 0,2");
ResultSet rs = ps.executeQuery();

while (rs.next()) {
	usercount=	rs.getInt(1);
List<Invitation> inv=hibernateTemplate.find("from Invitation invitation where invitation.id=?",usercount);
	if(inv.size()!=0){list.add(inv.get(0));}
	
}
rs.close();
conn.close();
return list;
    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
		
	}
	public List<Invitation> ReInvitation() {
	List<Invitation> list=new ArrayList<Invitation>();
	
		try {	Connection conn;
		int usercount;
		
			conn = DButils.getInstance().getConnection();  
			
			PreparedStatement ps=conn.prepareStatement("select invid from zhiding where time>? limit 0,20");
			Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60*24*30);
			ps.setTimestamp(1, time);
			
			ResultSet rs = ps.executeQuery();

while (rs.next()) {
	usercount=	rs.getInt(1);
List<Invitation> inv=hibernateTemplate.find("from Invitation invitation where invitation.id=?",usercount);
	if(inv.size()!=0){list.add(inv.get(0));}
	
}
rs.close();
conn.close();
return list;
    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
		
	}
	// 上传图片
	
	public boolean upLoadimg(Image image){
	Integer id= (Integer) hibernateTemplate.save(image);
		if(id>0){
			return true;
			
		}
		return false;
	}
	
	
	//读取图片
	public List<Image> readImage(int invid){
		List<Image> image=hibernateTemplate.find("from Image i where i.invid=?", invid);
		return image;
		
	}
	
	   
	
	// 排行
//	public List<Invitation> zhouZanInvitation(int pageNo) {
		
//		Session session=hibernateTemplate.getSessionFactory().openSession();
//		Query query = session.createQuery("from Invitation invitation where invitation.createtime>? order by invitation.zan desc");
//		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60*24*7);
//		query.setTimestamp(0, time);
//		query.setFirstResult((pageNo-1)*20);
//		query.setMaxResults(20);
//		@SuppressWarnings("unchecked")
//		List<Invitation> list=query.list();
//		session.close();
//		return list;
//	}
	//月收藏排行
	
//public List<Invitation> yueZanInvitation(int pageNo) {
		
//		Session session=hibernateTemplate.getSessionFactory().openSession();
//		Query query = session.createQuery("from Invitation invitation where invitation.createtime>? order by invitation.zan desc");
//		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60*24*30);
//		query.setTimestamp(0, time);
//		query.setFirstResult((pageNo-1)*20);
//		query.setMaxResults(20);
//		@SuppressWarnings("unchecked")
//		List<Invitation> list=query.list();
//		session.close();
//		return list;
//	}
	

	// 按类型查询所有帖子
	public List<Invitation> typeInvitation(int pageNo,int type) {
		
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Invitation invitation where invitation.type=? order by invitation.time desc");
		query.setInteger(0, type);
		query.setFirstResult((pageNo-1)*30);
		query.setMaxResults(30);
		@SuppressWarnings("unchecked")
		List<Invitation> list=query.list();
		
		session.close();
		 hibernateTemplate.getSessionFactory().close();
		return list;

	}
	
	
	

	// 按标题查询帖子
	public List<Invitation> titleInvitation(String title,int pageNo) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery("from Invitation i where type<5 and i.title like ? order by i.time desc");
		query.setString(0, "%"+title+"%");
		query.setFirstResult((pageNo-1)*30);
		query.setMaxResults(30);
		@SuppressWarnings("unchecked")
		List<Invitation> list=query.list();
		
		session.close();
		 hibernateTemplate.getSessionFactory().close();
		return list;
		
	}
	
	


	// 添加帖子
	public int saveInvitation(Invitation invitation) throws ClassNotFoundException, SQLException {
		Integer id = (Integer) hibernateTemplate.save(invitation);

		if (id > 0) {
			Connection conn=DButils.getInstance().getConnection();  
			Statement stmt=conn.createStatement();
		
			stmt.execute("create table inv_"+id+"(id integer primary key auto_increment,uid integer,content varchar(200),zan integer,time datetime);");
			PreparedStatement ps = conn.prepareStatement("insert into inv_"+invitation.getId()
					+ "(uid,content,zan,time) values (?,?,?,?)");
		 ps.setInt(1,invitation.getUid());
			ps.setString(2, invitation.getContent());
			ps.setInt(3, 0);
			ps.setTimestamp(4, invitation.getTime());
			int in = ps.executeUpdate();
			
			PreparedStatement psmt=conn.prepareStatement("insert into invuser(uid,invid) values (?,?)");
            psmt.setInt(1,invitation.getUid());
            psmt.setInt(2,id);
            psmt.executeUpdate();
			conn.close();
		if(in>0){
			return id;}
		return 0;
		}
		return 0;
	}

	// 删除帖子
	public boolean deleteInvitation(int id) {
		Invitation invitation = new Invitation();
		invitation.setId(id);
		try {
			hibernateTemplate.delete(invitation);
			Connection conn=DButils.getInstance().getConnection();  
			Statement stmt=conn.createStatement();
			stmt.execute("DROP TABLE inv_"+id);
			PreparedStatement psmt=conn.prepareStatement("delete from invuser where invid=?");
            psmt.setInt(1,id);
            psmt.executeUpdate();
            conn.close();
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		return true;

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
	public int findInvUid(int invid){
		try {
			List<Integer> list=hibernateTemplate.find("select inv.uid from Invitation inv where inv.id=?",invid);
         return list.get(0);
		} catch (Exception e) {
          return 0;		}
	}
	
	
	//查询总数
//	public int queryzhouCount(){
//		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60*24*7);
//		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.createtime>?",time);
//		int allnumber=(Integer) find.get(0);
//		return allnumber;
//	}
	public long queryCount(){
		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.type<4");
		long allnumber= (Long) find.get(0);
		return allnumber;
	}
	public long querytitleCount(String title){
		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.type<4 and inv.title like ?","%"+title+"%");
		long allnumber= (Long) find.get(0);
		return allnumber;
	}
	public long querytitleclubCount(String title){
		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.type=5 and inv.title like ?","%"+title+"%");
		long allnumber= (Long) find.get(0);
		return allnumber;
	}
	public long querytypeCount(int type){
		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.type=?",type);
		
		long allnumber= (Long) find.get(0);
		return allnumber;
	}
	//查询帖子关注人数
	public long queryuserCount(int invid){
		Connection conn;
		long usercount=0;
		try {
			conn =DButils.getInstance().getConnection();  
			PreparedStatement ps=conn.prepareStatement("select count(*) from invuser where invid=?");
ResultSet rs = ps.executeQuery();

while (rs.next()) {
	usercount=rs.getLong(1);
}
     rs.close();
    conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		return 0;	
		}
		return usercount;
		
	}
	
	//查询帖子关注人数
		public List<Invitation> topCount(){
			Connection conn;
		List<Invitation> list=new ArrayList<Invitation>();
			try {
				conn =DButils.getInstance().getConnection();  
				PreparedStatement ps=conn.prepareStatement("select invid,count(*) as c from invuser group by invid order by c desc limit 0,20");
	ResultSet rs = ps.executeQuery();
	while (rs.next()) {
		int invid=	rs.getInt(1);
	Invitation invitation=	findInvitation(invid);
	if(invitation!=null){
	list.add(invitation);}
	}
	
	
	rs.close();
	    conn.close();
			}catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
			return list;
			
		}
	
//	public int queryyueCount(){
//		Timestamp time=new Timestamp(System.currentTimeMillis()-1000*60*60*24*30);
//		List find = hibernateTemplate.find("select count(*) from Invitation inv where inv.createtime>?",time);
//		int allnumber=(Integer) find.get(0);
//		return allnumber;
//	}
	
	
	
}
	// 分页查询关闭session！！！
//	 public List getAllUsers(int pageNo){
//		  try{
//		   Query query=hibernateTemplate.getSessionFactory().openSession().createQuery("from Userinfo");
//		   query.setFirstResult(pageNo*20);
//		   query.setMaxResults(20);
		   //String queryString="from Userinfo";
		   //hibernateTemplate.setMaxResults(10);
//		   List list=query.list();
//		   
//		   return list;
//		  }catch(HibernateException ex){
//		   ex.printStackTrace();
//		   return null;
//		  }
//		 }
//	 
//	 
//	 
//		 public int getItemCount(){
//		  try{
//		   Query query=hibernateTemplate.getSessionFactory().openSession().createQuery("select count(*) from Userinfo user");
//		   Object obj=query.uniqueResult();
//		   int count=Integer.parseInt(obj.toString());
//		   return (int)count;
//		  }catch(HibernateException ex){
//		   ex.printStackTrace();
//		   return 0;
//		  }
//		 }
//		 public void computePageCount(){
//			  int tempCount=getItemCount();
//			int  pageCount=tempCount%20==0?tempCount/20:tempCount/20+1;  
//			 }
//	
//}

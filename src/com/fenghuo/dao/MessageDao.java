package com.fenghuo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jboss.weld.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Message;

public class MessageDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// 添加消息 根据类型添加类型1 普通消息 2帖子消息 3 活动消息
	public boolean saveMessage(Message message) {
		Integer id = (Integer) hibernateTemplate.save(message);
		if (id > 0) {
			return true;
		}
		return false;
	}

	// 删除消息
	public void deleteMessage(List<Message> message) {

		try {
			hibernateTemplate.deleteAll(message);
		} catch (Exception e) {
			System.out.println(e + "-----");
		}

	}

	// 根据toid查找消息
	public List<Message> findlaterMessage(int toid) {

		List<Message> list = new ArrayList<Message>();
		
//		List<Message> listspo=hibernateTemplate.find("from Message m where m.toid=? and type=4 group by m.invid order by m.time desc",toid);
//		List<Message> listuser=hibernateTemplate.find("from Message m where m.toid=? and type=0 group by m.fromid order by m.time desc",toid);
//		List<Message> listinv=hibernateTemplate.find("from Message m where m.toid=? and type>4 group by m.invid order by m.time desc",toid);
		Session session=hibernateTemplate.getSessionFactory().openSession();
		
		Query query = session.createQuery("from Message m where m.toid=? and type=4 group by m.invid order by m.time desc" );
		query.setInteger(0, toid);
//		session.close();
		@SuppressWarnings("unchecked")
		List<Message> listspo=query.list();
		Query query2 =session.createQuery("from Message m where m.toid=? and type=0 group by m.fromid order by m.time desc");
		query2.setInteger(0, toid);
		@SuppressWarnings("unchecked")
		List<Message> listuser=query2.list();
		Query query3 =session.createQuery("from Message m where m.toid=? and type>4 group by m.invid order by m.time desc");
		query3.setInteger(0, toid);
		@SuppressWarnings("unchecked")
		List<Message> listinv=query3.list();
	    session.close();
	    
		hibernateTemplate.getSessionFactory().close();
		 
		
//		List<Message> listspo= hibernateTemplate
//				.find("  from Message m where m.toid=? and type=4 group by m.invid order by m.time desc",
//						toid);
//		List<Message> listuser = hibernateTemplate
//				.find("from Message m where m.toid=? and type<4 group by m.fromid order by m.time desc",
//						toid);
//
//		List<Message> listinv = hibernateTemplate
//				.find("from Message m where m.toid=? and type>4 group by m.invid order by m.time desc",
//						toid);
		if (listinv.size() != 0) {
			list.addAll(listinv);
		}
		if (listspo.size() != 0) {
			list.addAll(listspo);
		}
		if (listuser.size() != 0) {
			list.addAll(listuser);
		}

		return list;

	}

	// 根据toid查找消息
	public List<Message> findMessage(int toid) {
		Session session=hibernateTemplate.getSessionFactory().openSession();
		Query query = session.createQuery( "from Message m where m.toid=? ");
		query.setInteger(0, toid);
		@SuppressWarnings("unchecked")
		List<Message> list=query.list();
		session.close();
		 hibernateTemplate.getSessionFactory().close();
		return list;}
	
	
		// 根据toid查找消息
		public List<Message> findChatMessage(int toid) {
			Session session=hibernateTemplate.getSessionFactory().openSession();
			Query query = session.createQuery( "from Message m where m.toid=? and m.type=0");
			query.setInteger(0, toid);
			@SuppressWarnings("unchecked")
			List<Message> list=query.list();
			session.close();
			 hibernateTemplate.getSessionFactory().close();
			return list;
		
//		
//		List<Message> list = hibernateTemplate.find(
//				"from Message m where m.toid=?", toid);

//		
//		return list;

	}

}

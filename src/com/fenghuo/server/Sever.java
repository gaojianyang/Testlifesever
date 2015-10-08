//package com.fenghuo.server;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import com.fenghuo.dao.ClubDao;
//import com.fenghuo.dao.ClubUserDao;
//import com.fenghuo.dao.InvitationDao;
//import com.fenghuo.dao.MessageDao;
//import com.fenghuo.dao.ReplyDao;
//import com.fenghuo.dao.UserDao;
//import com.fenghuo.dao.UserUser;
//import com.fenghuo.pojo.Club;
//import com.fenghuo.pojo.Invitation;
//import com.fenghuo.pojo.Message;
//import com.fenghuo.pojo.Reply;
//import com.fenghuo.pojo.User;
//import com.fenghuo.utils.Constant;
//import com.fenghuo.utils.ListUtils;

//public class Sever extends HttpServlet {
//	String name, pass, title, content;
//	boolean success = true;
//	Timestamp time;
//	int cid, zan, page, pageCount, totalCount,invid,id,head,fid;
//	short type;
//	int endNum = 20;
//	List<Invitation> fenyelist = new ArrayList<Invitation>();
//	List<Invitation> alllist = new ArrayList<Invitation>();
//	List<Reply> replylist=new ArrayList<Reply>();
//	List<Reply> refenyelist=new ArrayList<Reply>();
//	List<Club> clublist=new ArrayList<Club>();
//	List<Club> clfenyelist=new ArrayList<Club>();
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//			throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		super.doPost(req, resp);
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"applicationContext.xml");
//		UserDao userDao = context.getBean("userDao", UserDao.class);
//		ReplyDao replyDao = context.getBean("replyDao", ReplyDao.class);
//		ClubDao clubDao = context.getBean("clubDao", ClubDao.class);
//		UserUser userUser=new UserUser();
//		MessageDao messageDao=context.getBean("messageDao",MessageDao.class);
//		ClubUserDao clubUserDao=new ClubUserDao();
//		InvitationDao invitationDao = context.getBean("invitationDao",
//				InvitationDao.class);
//		int flag = Integer.parseInt(req.getParameter("flag"));
//
//		switch (flag) {
//		case Constant.REGISTER:
//
//			name = req.getParameter("name");
//			pass = req.getParameter("pass");
//			String gender = req.getParameter("gender");
//			String college = req.getParameter("college");
//			String personal = req.getParameter("personal");
//			head = Integer.parseInt(req.getParameter("head"));
//			time = new Timestamp(System.currentTimeMillis());
//			User user = new User(name, pass, gender, head, college, personal,
//					1, time);
//
//			success = userDao.saveUser(user);
//
//			break;
//		case Constant.LOGIN:
//			name = req.getParameter("name");
//			pass = req.getParameter("pass");
//			user = userDao.userLogin(name, pass);
//
//			break;
//		case Constant.UPDATEUSER:
//			name = req.getParameter("name");
//			User user2 = userDao.queryUser(name);
//			user2.setPass(req.getParameter("pass"));
//			user2.setGender(req.getParameter("gender"));
//			user2.setCollege(req.getParameter("college"));
//			user2.setHead(Integer.parseInt(req.getParameter("head")));
//			success = userDao.updateUser(user2);
//
//			break;
//		case Constant.CREATEINVITATION:
//			id=Integer.parseInt(req.getParameter("uid"));
//			title = req.getParameter("title");
//			content = req.getParameter("content");
//			time = new Timestamp(System.currentTimeMillis());
//			type = (short) Integer.parseInt(req.getParameter("type"));
//            
//			Invitation invitation = new Invitation(id,title, content, 0, time,
//					time, type);
//			
//			try {
//				invid = invitationDao.saveInvitation(invitation);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			break;
//
//		case Constant.DELETEINVITATION:
//			invid = Integer.parseInt(req.getParameter("invid"));
//			invitationDao.deleteInvitation(invid);
//			break;
//		case Constant.ALLINVITATION:
//			page = Integer.parseInt("page");
//
//			alllist = invitationDao.allInvitation(page);
//			
//			
//			break;
//		case Constant.ZONGZANINVITATION:
//			page = Integer.parseInt("page");
//			alllist = invitationDao.allInvitation(page);
//			
//			break;
//		case Constant.TYPEINVITATION:
//			page = Integer.parseInt(req.getParameter("page"));
//			alllist = invitationDao.zanInvitation(page);
//			
//		    break;
//		case Constant.TITLEINVITATION:
//		
//			page = Integer.parseInt(req.getParameter("page"));
//			 title=req.getParameter("title");
//			alllist = invitationDao.titleInvitation(title,page);
//			
//			break;
//		case Constant.CREATEREPLY:
//		
//			invid=Integer.parseInt(req.getParameter("invid"));
//			name=req.getParameter("name");
//			int uid=Integer.parseInt(req.getParameter("invid"));
//			int rid=userDao.queryUser(name).getId();
//			content=req.getParameter("content");
//			time=new Timestamp(System.currentTimeMillis());
//			Reply reply=new Reply(name, content, 0, time);
//			try {
//				replyDao.addReply(invid,uid,rid, reply);
//			} catch (ClassNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//			break;
//		case Constant.ALLREPLY:
//			
//			invid = Integer.parseInt(req.getParameter("invid"));
//			page = Integer.parseInt(req.getParameter("page"));
//			
//			try {
//				replylist=replyDao.timeQuery(invid,page);
//			} catch (ClassNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//				 
//			break;
//		case Constant.ZANREPLY:	
//			id = Integer.parseInt(req.getParameter("uid"));
//			page = Integer.parseInt(req.getParameter("page"));
//			try {
//				 replylist=replyDao.zanQuery(id,page);
//				
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			break;
//		case Constant.CREATECLUB:
//			id = Integer.parseInt(req.getParameter("uid"));
//			title = req.getParameter("title");
//			content = req.getParameter("introduce");
//			time = new Timestamp(System.currentTimeMillis());	
//			head = Integer.parseInt(req.getParameter("head"));
//		    Club club=new Club(id,title, 0, content, id, time);
//		    invid= clubDao.createClub(club);
//		 		    
//   
//		    
//		    
//	       break;	
//		case Constant.DELETECLUB:
//			 id = Integer.parseInt(req.getParameter("uid"));
//		
//			cid = Integer.parseInt(req.getParameter("cid"));
//			success= clubDao.queryClub(id, cid);
//			if(success==true){
//			invid = Integer.parseInt(req.getParameter("invid"));
//			clubDao.deleteClub(id, invid);}
//			
//			break;
//			
//		case Constant.ALLCLUB:
//			page = Integer.parseInt(req.getParameter("page"));
//			 clublist = clubDao.allInvitation();
//			 totalCount = clublist.size();
//				if (totalCount % endNum > 0) {
//					pageCount = totalCount / endNum + 1;
//				} else {
//					pageCount = totalCount / endNum;
//				}
//				clfenyelist = ListUtils.clfenye(clublist, page);
//
//			
//			
//			break;
//		case Constant.JOINCLUB://是否已经存在
//			 id = Integer.parseInt(req.getParameter("uid"));
//				
//				cid = Integer.parseInt(req.getParameter("cid"));
//			try {
//				clubUserDao.joinClub(cid, id);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			break;
//		case Constant.DELETEUSERCLUB:
//			id = Integer.parseInt(req.getParameter("uid"));
//			
//			cid = Integer.parseInt(req.getParameter("cid"));
//			try {
//				clubUserDao.quitClub(cid, id);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
//		case Constant.MESSAGE:
//			 content=req.getParameter("content");
//			 int fromid=Integer.parseInt(req.getParameter("fromid"));
//			 int toid=Integer.parseInt(req.getParameter("toid"));
//			 time = new Timestamp(System.currentTimeMillis());
//			Message message=new Message(content, fromid, toid, (short) 1, 0, time);
//			messageDao.saveMessage(message);
//			
//			break;
//		case Constant.INVITATIONZAN:
//			invid = Integer.parseInt(req.getParameter("invid"));
//			Invitation invitation2=invitationDao.findInvitation(invid);
//		  if(invitation2!=null){
//			 success= invitationDao.updateZan(invitation2);
//	       }else{success=false;}
//		  
//			break;
//		case Constant.REPLYZAN:
//			invid=Integer.parseInt(req.getParameter("invid"));
//			int reid=Integer.parseInt(req.getParameter("reid"));
//			try {
//				replyDao.reidQuery(invid, reid);	
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			break;
//		case Constant.QUERYMESSAGE:
//			id = Integer.parseInt(req.getParameter("uid"));
//			List<Message> melist = messageDao.findMessage(id);
//			
//			break;
//		case Constant.QUERYUSER:
//			name=req.getParameter("uname");
//			User user3 = userDao.queryUser(name);
//			
//			break;
//		case Constant.GUANZHUUSER:
//			id = Integer.parseInt(req.getParameter("uid"));
//			fid= Integer.parseInt(req.getParameter("fid"));
//			try {
//				success=userUser.guanzhuUser(id, fid);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
//		case Constant.QUXIAOGUANZHU:
//			id = Integer.parseInt(req.getParameter("uid"));
//			fid= Integer.parseInt(req.getParameter("fid"));
//			try {
//				success=userUser.quxiaoUser(id, fid);
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			break;
//		case Constant.ZHOUZANINVITATION:
//			
//		
//			
//			break;
//		case Constant.YUEZANINVITATION:
//			break;
//			
//			
//			
//			
//
//		default:
//			break;
//		}
//
//	}
//
//}

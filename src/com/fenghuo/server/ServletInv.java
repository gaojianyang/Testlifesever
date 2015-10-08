package com.fenghuo.server;

import java.io.IOException;






import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fenghuo.dao.InvitationDao;
import com.fenghuo.dao.MessageDao;
import com.fenghuo.dao.ReplyDao;
import com.fenghuo.dao.UserDao;
import com.fenghuo.dao.UserUser;
import com.fenghuo.pojo.Image;
import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.Reply;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.Constant;
import com.fenghuo.utils.Timeutils;
import com.sun.script.javascript.JSAdapter;

public class ServletInv extends HttpServlet {

	/**
	 * 
	 */

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		InvitationDao invitationDao = context.getBean("invitationDao",
				InvitationDao.class);
		req.setCharacterEncoding("utf-8");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		UserUser useruser = context.getBean("userUser", UserUser.class);
		MessageDao messageDao=context.getBean("messageDao", MessageDao.class);
		ReplyDao replyDao = context.getBean("replyDao", ReplyDao.class);
		int flag = Integer.parseInt(req.getParameter("flag"));

		switch (flag) {
		case Constant.CREATEINVITATION:
			int id = Integer.parseInt(req.getParameter("uid"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			Timestamp time = new Timestamp(System.currentTimeMillis());
			short type = (short) Integer.parseInt(req.getParameter("type"));
			Invitation invitation = new Invitation(id, title, content, time,
					time, type);
			JSONObject object = new JSONObject();
			
			try {int invid = invitationDao.saveInvitation(invitation);
			
				if (invid > 0) {
					object.put("id", invid);
					object.put("iuid", id);
					object.put("title", title);
					object.put("type", type);
					object.put("time", Timeutils.monthToString(time));
					object.put("createtime", Timeutils.monthToString(time));
					object.put("head", userDao.headUser(id));
					object.put("uname", userDao.nameUser(id));
					object.put("zan", replyDao.replyCount(invid));
					object.put("state", true);
				} else {
					object.put("state", false);
				}
				responseOutWithJson(response, object);
				List<User> userGuanZhu = useruser.userGuanZhu(id);
				if(userGuanZhu!=null){
					for(User temp:userGuanZhu){
						Message message=new Message("发表了帖子： "+content, id, temp.getId(), (short)5, invid, time);
						messageDao.saveMessage(message);
						message=null;
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				object.put("state", false);
				responseOutWithJson(response, object);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				object.put("state", false);
				responseOutWithJson(response, object);

			}
		
			break;

		case Constant.DELETEINVITATION:
			     int invid2 = Integer.parseInt(req.getParameter("invid"));
				boolean success = invitationDao.deleteInvitation(invid2);
				JSONObject object2 = new JSONObject();
				object2.put("state", success);
				responseOutWithJson(response, object2);
			break;
		case Constant.ALLINVITATION:
			long allcount = invitationDao.queryCount();
			int page = Integer.parseInt(req.getParameter("page"));
			List<Invitation> alllist = invitationDao.allInvitation(page);
			if(page==1){
				List<Invitation> topInvitation = invitationDao.TopInvitation();
				topInvitation.addAll(alllist);
				JSONObject 	jsonObject = jsonList(topInvitation, allcount);
				responseOutWithJson(response, jsonObject);
			}else{
				JSONObject 	jsonObject = jsonList(alllist, allcount);
				responseOutWithJson(response, jsonObject);}
		System.out.println(allcount+"++++++++++++++++");
			break;
		case Constant.ZONGZANINVITATION:
			int page2 = Integer.parseInt("page");
			long allcount2 = invitationDao.queryCount();
			List<Invitation> alllist2 = invitationDao.allInvitation(page2);
				JSONObject	jsonObject2 = jsonList(alllist2, allcount2);
				responseOutWithJson(response, jsonObject2);
			break;
		case Constant.TYPEINVITATION:
			int page3 = Integer.parseInt(req.getParameter("page"));
			int type2 = Integer.parseInt(req.getParameter("type"));
			List<Invitation> alllist3 = invitationDao.typeInvitation(page3,
					type2);
			long allcount3 = invitationDao.queryCount();
			JSONObject	jsonObject3 = jsonList(alllist3, allcount3);
				responseOutWithJson(response, jsonObject3);
			break;
			
		case Constant.TITLEINVITATION:
			
			int page4 = Integer.parseInt(req.getParameter("page"));
			String title2=req.getParameter("title");
			long allcount7=invitationDao.querytitleCount(title2);
			if(allcount7<1){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
			}else{
			
			List<Invitation>	alllist7 = invitationDao.titleInvitation(title2,page4);
				
		        jsonList(alllist7, allcount7);
		        responseOutWithJson(response, jsonList(alllist7, allcount7));}
				break;
		case Constant.CREATEREPLY:
				int invid1=Integer.parseInt(req.getParameter("invid"));
			   int ruid=Integer.parseInt(req.getParameter("rid"));
			  short type3=(short) Integer.parseInt(req.getParameter("type"));
				content=req.getParameter("content");
				time=new Timestamp(System.currentTimeMillis());
				Reply reply=new Reply(ruid, content, 0, time);
				JSONObject jsonObject4=new JSONObject();
				try {
				int rid=replyDao.addReply(invid1,ruid,type3, reply);
				
				if(rid>0){
					jsonObject4.put("state", true);
					jsonObject4.put("time", Timeutils.monthToString(time));
					
				}else{
					jsonObject4.put("state", false);
					
				}
				
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					jsonObject4.put("state", false);} catch (SQLException e1) {
						jsonObject4.put("state", false);	}
				responseOutWithJson(response, jsonObject4);
				break;	
		case Constant.ALLREPLY:
		int invid9 = Integer.parseInt(req.getParameter("invid"));
		int page9 = Integer.parseInt(req.getParameter("page"));
			try {
		List<Reply> replylist=replyDao.timezhengQuery(invid9,page9);
		long count2=replyDao.replyCount(invid9);
		responseOutWithJson(response, jsonReply(replylist, count2));

		
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JSONObject jsObject20=new JSONObject();
				jsObject20.put("state", false);
				responseOutWithJson(response, jsObject20);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				JSONObject jsObject21=new JSONObject();
				jsObject21.put("state", false);
				responseOutWithJson(response, jsObject21);
			}
				 
			break;	
		case Constant.DAOREPLY:
			
			int invid10 = Integer.parseInt(req.getParameter("invid"));
			int page10 = Integer.parseInt(req.getParameter("page"));
				
				try {
			List<Reply> replylist=replyDao.timedaoQuery(invid10,page10);
			long count3=replyDao.replyCount(invid10);
			responseOutWithJson(response, jsonReply(replylist, count3));
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JSONObject jsObject20=new JSONObject();
					jsObject20.put("state", false);
					responseOutWithJson(response, jsObject20);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JSONObject jsObject21=new JSONObject();
					jsObject21.put("state", false);
					responseOutWithJson(response, jsObject21);
				}
					 
				break;	
		//zan paixu		
		case Constant.ZANREPLY:	
			int invid7= Integer.parseInt(req.getParameter("invid"));
			int page7 = Integer.parseInt(req.getParameter("page"));
			
			try {
				long count7=replyDao.replyCount(invid7);
				List<Reply> replylist2=replyDao.zanQuery(invid7,page7);
			responseOutWithJson(response, jsonReply(replylist2, count7));

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONObject jsObject20=new JSONObject();
				jsObject20.put("state", false);
				responseOutWithJson(response, jsObject20);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JSONObject jsObject21=new JSONObject();
				jsObject21.put("state", false);
				responseOutWithJson(response, jsObject21);
			}

			break;		
//		case Constant.INVITATIONZAN:
//		int	invid2 = Integer.parseInt(req.getParameter("invid"));
//			 success= replyDao.updateZan(invid2);
//		  JSONObject jsonObject4=new JSONObject();
//		  jsonObject4.put("state", success);
//		  responseOutWithJson(response,jsonObject4);
//		  
//		  
//			break;
			//zantiezi
		case Constant.REPLYZAN:
		    int	invid3=Integer.parseInt(req.getParameter("invid"));
			int reid=Integer.parseInt(req.getParameter("reid"));
			JSONObject jsonObject9=new JSONObject();
			try {
				
				boolean zanReply = replyDao.zanReply(invid3, replyDao.reidQuery(invid3, reid));
				jsonObject9.put("state", zanReply);
			} catch (ClassNotFoundException e) {
				jsonObject9.put("state", false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				jsonObject9.put("state", false);
			}
			responseOutWithJson(response, jsonObject9);
			break;	
		case Constant.LOUZHUREPLY:
		    int	invid5=Integer.parseInt(req.getParameter("invid"));
			int page5=Integer.parseInt(req.getParameter("page"));
			int findInvUid = invitationDao.findInvUid(invid5);
			JSONObject jsonObject1=new JSONObject();
			try {
				
				List<Reply> replylist=replyDao.louZhuQuery(invid5,page5,findInvUid);
				long count2=replyDao.replyUidCount(invid5,findInvUid);
				responseOutWithJson(response, jsonReply(replylist, count2));

				
					} catch (ClassNotFoundException e1) {
						jsonObject1.put("state", false);			} catch (SQLException e1) {
						// TODO Auto-generated catch block
							jsonObject1.put("state", false);					}
			responseOutWithJson(response, jsonObject1);
						 
			break;
		case Constant.ZHOUZANINVITATION:
//			int page17= Integer.parseInt("page");
//			int allcount4 = invitationDao.queryzhouCount();
//			List<Invitation> alllist5 = invitationDao.zhouZanInvitation(page17);
//
//			JSONObject jsonObject5 = jsonList(alllist5, allcount4);
//
//			responseOutWithJson(response, jsonObject5);
			break;
				
		case Constant.YUEZANINVITATION:
//			int page8= Integer.parseInt("page");
//			int allcount5 = invitationDao.queryyueCount();
//			List<Invitation> alllist6 = invitationDao.yueZanInvitation(page8);
//			JSONObject jsonObject7 = jsonList(alllist6, allcount5);
//			responseOutWithJson(response, jsonObject7);
			break;
		case Constant.GUANZHUINV:
			int	invid4=Integer.parseInt(req.getParameter("invid"));
			int uid4=Integer.parseInt(req.getParameter("uid"));
			boolean success3;
			try {
				success3=useruser.guanzhuInv(uid4, invid4);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				success3=false;			} catch (SQLException e) {
				// TODO Auto-generated catch block
					success3=false;				}
			JSONObject jsonObject6=new JSONObject();
			jsonObject6.put("state", success3);
			responseOutWithJson(response, jsonObject6);
			break;
		case Constant.QUXIAOINV:
			int	invid11=Integer.parseInt(req.getParameter("invid"));
			int uid5=Integer.parseInt(req.getParameter("uid"));
			boolean success4;
			try {
				success4=useruser.quxiaoInv(uid5, invid11);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				success4=false;			} catch (SQLException e) {
				// TODO Auto-generated catch block
					success4=false;				}
			JSONObject jsonObject7=new JSONObject();
			jsonObject7.put("state", success4);
			responseOutWithJson(response, jsonObject7);
			break;
			
		case Constant.INVIMAGE:
			
			int	invid12=Integer.parseInt(req.getParameter("invid"));

			
			List<Image> readImage = invitationDao.readImage(invid12);
			JSONObject jsonObject=new JSONObject();
			if(readImage.size()!=0){
				
				JSONArray jsonArray=new JSONArray();
				for(Image temp:readImage){
					JSONObject object11 = new JSONObject();
					object11.put("uid", temp.getUid());
					object11.put("title", temp.getTitle());
					object11.put("uri", temp.getUrl());
					object11.put("name",userDao.nameUser(temp.getUid()));
					object11.put("time",Timeutils.monthToString(temp.getTime()));
				jsonArray.add(object11);
				}
				jsonObject.put("image", jsonArray);
				jsonObject.put("state", true);
				
			}else{
				jsonObject.put("state", false);
			}
			responseOutWithJson(response, jsonObject);
			
			break;
		case Constant.TOPINV:
			List<Invitation> topCount = invitationDao.topCount();
			JSONArray jsonArray = new JSONArray();
			JSONObject jsonObject30 = new JSONObject();
			for (Invitation temp : topCount) {
				JSONObject object8 = new JSONObject();
				object8.put("id", temp.getId());
				object8.put("title", temp.getTitle());
				object8.put("type", temp.getType());
				object8.put("head", userDao.headUser(temp.getUid()));
				object8.put("uname",userDao.nameUser(temp.getUid()));
				object8.put("time",Timeutils.hourToString(temp.getTime()));
				object8.put("createtime",Timeutils.monthToString(temp.getCreatetime()));
				object8.put("zan",replyDao.replyCount(temp.getId()) );
				jsonArray.add(object8);
			}
			jsonObject30.put("state", true);
			break;
		
		default:
			break;
		}
	}

	protected void responseOutWithJson(HttpServletResponse response,
			JSONObject responseJSONObject) {
		// 将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");

		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(responseJSONObject.toString());
			responseJSONObject=null;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}

		}
	}
ApplicationContext context = new ClassPathXmlApplicationContext(
			"applicationContext.xml");
	public JSONObject jsonList(List<Invitation> alllist, long allcount){
	
		UserDao userDao = context.getBean("userDao", UserDao.class);
		ReplyDao replyDao = context.getBean("replyDao", ReplyDao.class);

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (alllist.size()!=0) {
			for (Invitation temp : alllist) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("iuid", temp.getUid());
				object.put("title", temp.getTitle());
				object.put("type", temp.getType());
				object.put("head", userDao.headUser(temp.getUid()));
				object.put("uname",userDao.nameUser(temp.getUid()));
				object.put("time",Timeutils.hourToString(temp.getTime()));
				object.put("createtime",Timeutils.monthToString(temp.getCreatetime()));
				object.put("zan",replyDao.replyCount(temp.getId()) );
				jsonArray.add(object);
			}
			jsonObject.put("state", true);
			jsonObject.put("count", allcount);
			jsonObject.put("inv", jsonArray);
		} else {

			jsonObject.put("state", false);
		}
		return jsonObject;
	}
	
	public JSONObject jsonReply(List<Reply> list,long replycount){
		UserDao userDao = context.getBean("userDao", UserDao.class);

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (list.size()!=0) {
			for (Reply temp :list) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("uid", temp.getUid());
				object.put("head",userDao.headUser(temp.getUid()) );
				object.put("uname", userDao.nameUser(temp.getUid()));
			    object.put("content", temp.getContent());
				object.put("time", Timeutils.monthToString( temp.getTime()));
				object.put("zan", temp.getZan());
				jsonArray.add(object);
			}
			jsonObject.put("state", true);
			jsonObject.put("count", replycount);
			jsonObject.put("allreply", jsonArray);
		} else {
			jsonObject.put("state", false);
		}
		return jsonObject;
	}
	
	
	
}
     



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

import com.fenghuo.dao.ClubDao;
import com.fenghuo.dao.InvitationDao;
import com.fenghuo.dao.MessageDao;
import com.fenghuo.dao.UserUser;

import com.fenghuo.pojo.Club;
import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.Constant;
import com.fenghuo.utils.Timeutils;

public class ServletClub extends HttpServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		doPost(request, response);
	}

	
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		req.setCharacterEncoding("utf-8");
   	      ClubDao clubDao = context.getBean("clubDao", ClubDao.class);
   		UserUser useruser = context.getBean("userUser", UserUser.class);

	     MessageDao messageDao=context.getBean("messageDao",MessageDao.class);
		int flag = Integer.parseInt(req.getParameter("flag"));
   switch (flag) {
   case Constant.CREATECLUB:
		    int uid = Integer.parseInt(req.getParameter("uid"));
			String title = req.getParameter("name");
			String content = req.getParameter("introduce");
			Timestamp time = new Timestamp(System.currentTimeMillis());	
			int head = Integer.parseInt(req.getParameter("head"));
			 Club club=new Club(uid,title, content,head, time);
	try {
		Club invitation= clubDao.createClub(club);
		JSONObject object=new JSONObject();
		if (invitation!=null) {
			
		
			object.put("id", invitation.getId());
			object.put("uid", invitation.getUid());
			object.put("name", invitation.getName());
			object.put("content", invitation.getIntroduce());
			object.put("invid", invitation.getInvid());
			object.put("head", invitation.getHead());
			object.put("time",Timeutils.yearToString(invitation.getTime()));
			object.put("number",invitationDao.queryuserCount(invitation.getInvid()));

			object.put("state", true);
		} else {

			object.put("state", false);
		}
		responseOutWithJson(response, object);
		List<User> userGuanZhu = useruser.userGuanZhu(uid);
		if(userGuanZhu!=null){
			for(User temp:userGuanZhu){
				Message message=new Message("成立了社团："+title, uid, temp.getId(), (short)6, invitation.getInvid(), time);
				messageDao.saveMessage(message);
				message=null;
			}
		}
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	break;
   case Constant.DELETECLUB:
		int id=Integer.parseInt(req.getParameter("uid"));
		
		int invid = Integer.parseInt(req.getParameter("invid"));
		boolean success2 = clubDao.deleteClub(id, invid);
		
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("state", success2);
			jsonObject.put("state", false);
		
		responseOutWithJson(response, jsonObject);
		break;
   case Constant.ALLCLUB:
		  int page = Integer.parseInt(req.getParameter("page"));
		  List<Club> clublist = clubDao.allInvitation(page);
		 long allcount=clubDao.clubCount();
	      responseOutWithJson(response,jsonList(clublist, allcount));
	break;
   case Constant.TITLECLUB:
	   int page4 = Integer.parseInt(req.getParameter("page"));
		String title2=req.getParameter("title");
		long allcount7=clubDao.titleCount(title2);
		if(allcount7<1){
			JSONObject jsonObjecst=new JSONObject();
			jsonObjecst.put("state", false);
		}else{
		   List<Club>	alllist7 = clubDao.titleClub(title2,page4);
	        jsonList(alllist7, allcount7);
	        responseOutWithJson(response, jsonList(alllist7, allcount7));}

	        
	   
	   
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
	
InvitationDao invitationDao=context.getBean("invitationDao",InvitationDao.class);
	public JSONObject jsonList(List<Club> alllist, long allcount) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (alllist != null) {
			for (Club temp : alllist) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("uid", temp.getUid());
				object.put("content", temp.getIntroduce());
				object.put("name", temp.getName());
				object.put("head", temp.getHead());
				object.put("invid", temp.getInvid());
				object.put("time", Timeutils.yearToString( temp.getTime()));
				object.put("number",invitationDao.queryuserCount(temp.getInvid()));
				jsonArray.add(object);
			
			}
			jsonObject.put("state", true);
			jsonObject.put("count", allcount);
			jsonObject.put("allclub", jsonArray);
		} else {

			jsonObject.put("state", false);
		}
		return jsonObject;
	}

}

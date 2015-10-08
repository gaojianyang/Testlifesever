package com.fenghuo.server;

import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fenghuo.dao.MessageDao;
import com.fenghuo.dao.UserDao;
import com.fenghuo.pojo.Message;
import com.fenghuo.utils.Constant;
import com.fenghuo.utils.Timeutils;

public class ServletMessage extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}
//	List<Message> message = httpJson.getMessage(jsonArray);
//	Set<Message> messagelater = new HashSet<Message>();
//	Set<Integer> messageclub = new HashSet<Integer>();
//	Set<Integer> messageinv = new HashSet<Integer>();
//	Set<Integer> messagespo = new HashSet<Integer>();
//	Set<Integer> messageuser = new HashSet<Integer>();
//
//	for (Message temp : message) {
//
//		switch (temp.getType()) {
//		case 0:
//			ChatMessage chatMessage = new ChatMessage(temp
//					.getContent(), temp.getFromid(), temp
//					.getFromhead(), temp.getTime(),
//					AllStatic.loginConfig.getId(), temp
//							.getFromname());
//			finalDb.save(chatMessage);
//			messageuser.add(temp.getFromid());
//			break;
//		case 1:
//			temp.setFromname(temp.getFromname() + "  发表帖子:");
//			messagelater.add(temp);
//			finalDb.save(temp);
//			break;
//		case 2:
//			temp.setFromname(temp.getFromname() + "  成立社团:");
//			messagelater.add(temp);
//			finalDb.save(temp);
//			break;
//		case 3:
//			temp.setFromname(temp.getFromname() + "  创办活动:");
//			messagelater.add(temp);
//			finalDb.save(temp);
//			break;
//		case 4:
//			messageclub.add(temp.getInvid());
//			break;
//		case 5:
//			messageinv.add(temp.getInvid());
//			break;
//		case 6:
//			messagespo.add(temp.getInvid());
//			break;
//		default:
//			break;
//		}
//
//	}
//	if (messageclub.size() != 0) {
//		for (Message temptwo : message) {
//
//			for (Integer intclub : messageclub) {
//				if ((temptwo.getInvid() == intclub)) {
//					Message message2 = new Message(
//							"社团有新消息", temptwo.getFromid(),
//							temptwo.getFromhead(),
//							(short) 4, intclub, "", temptwo
//									.getFromname());
//					messagelater.add(message2);
//				}
//
//			}
//		}
//	}
//	if (messagespo.size() != 0) {
//		for (Message temptwo : message) {
//			for (Integer intspo : messagespo) {
//				if ((temptwo.getInvid() == intspo)) {
//					Message message2 = new Message(
//							"活动有新消息", temptwo.getFromid(),
//							temptwo.getFromhead(),
//							(short) 6, intspo, "", temptwo
//									.getFromname());
//					messagelater.add(message2);
//				}
//			}
//		}
//	}
//	if (messageuser.size() != 0) {
//		for (Message temptwo : message) {
//			for (Integer intuser : messageuser) {
//				if ((temptwo.getFromid() == intuser)) {
//					Message message2 = new Message("发来了消息",
//							temptwo.getFromid(), temptwo
//									.getFromhead(),
//							(short) 0, intuser, "", temptwo
//									.getFromname());
//					messagelater.add(message2);
//				}
//			}
//		}
//	}
//
//	if (messageinv.size() != 0) {
//		for (Message temptwo : message) {
//			for (Integer intinv : messageinv) {
//				if ((temptwo.getInvid() == intinv)) {
//					Message message2 = new Message("帖子有更新",
//							temptwo.getFromid(), temptwo
//									.getFromhead(),
//							(short) 5, intinv, "", temptwo
//									.getFromname());
//					messagelater.add(message2);
//				}
//			}
//		}
//	}
//	
	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		MessageDao messageDao=context.getBean("messageDao", MessageDao.class);
		req.setCharacterEncoding("utf-8");
		int flag=Integer.parseInt(req.getParameter("flag"));
		switch (flag) {
			case Constant.MESSAGE:
				int fromid = Integer.parseInt(req.getParameter("fromid"));
				int toid = Integer.parseInt(req.getParameter("toid"));
				int invid = Integer.parseInt(req.getParameter("invid"));
				short type9= (short) Integer.parseInt(req.getParameter("type"));
			    String content=req.getParameter("content");
				Timestamp timestamp=new Timestamp(System.currentTimeMillis());
				Message message=new Message(content, fromid, toid, type9, invid, timestamp);
				boolean saveMessage = messageDao.saveMessage(message);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", saveMessage);
				jsonObject.put("time",Timeutils.monthToString(timestamp));
				responseOutWithJson(response, jsonObject);
				
				break;
			case Constant.RECMESSAGE:
				int uid=Integer.parseInt(req.getParameter("uid"));
//				List<Message> findMessage = messageDao.findMessage(uid);
				List<Message> laterMessage = messageDao.findlaterMessage(uid);
				List<Message> chatMessage=messageDao.findChatMessage(uid);
				
				JSONObject jsonReply = jsonMessage(laterMessage,chatMessage);
				responseOutWithJson(response, jsonReply);
//				messageDao.deleteMessage(findMessage);
				
			
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
	UserDao userDao=context.getBean("userDao", UserDao.class);
public JSONObject jsonMessage(List<Message> list,List<Message> chatlist){
		
		JSONArray jsonArraya = new JSONArray();
		JSONObject jsonObjecta = new JSONObject();
		JSONObject jsonObjectc=new JSONObject();
		JSONArray jsonArrayc = new JSONArray();
		JSONObject jsonObject= new JSONObject();

		if (list.size() != 0) {
			for (Message temp :list) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
			    object.put("content", temp.getContent());
				object.put("time",Timeutils.monthToString(temp.getTime()));
				object.put("invid", temp.getInvid());
				object.put("type", temp.getType());
				object.put("fromid", temp.getFromid());
				object.put("fromhead", userDao.headUser(temp.getFromid()));
				object.put("fromname",userDao.nameUser(temp.getFromid()));
				jsonArraya.add(object);
			}
			jsonObjecta.put("stateme", true);
			jsonObjecta.put("messagea", jsonArraya);
		} else {
			jsonObjecta.put("stateme", false);
		}
		if(chatlist.size()!=0){
			
			for (Message temp :chatlist) {
			JSONObject object = new JSONObject();
		    object.put("content", temp.getContent());
			object.put("time",Timeutils.monthToString(temp.getTime()));
			object.put("toid", temp.getToid());
			object.put("fromid", temp.getFromid());
			object.put("fromhead", userDao.headUser(temp.getFromid()));
			object.put("fromname",userDao.nameUser(temp.getFromid()));
			jsonArrayc.add(object);}
			jsonObjectc.put("statech", true);
			jsonObjectc.put("messagec", jsonArrayc);
			
		}else{
			jsonObjectc.put("statech", false);
		}
		jsonObject.put("allmessage", jsonObjecta);
		jsonObject.put("chatmessage", jsonObjectc);
		
		return jsonObject;
	}
 	    	

}

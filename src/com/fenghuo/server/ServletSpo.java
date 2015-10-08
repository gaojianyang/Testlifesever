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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fenghuo.dao.MessageDao;
import com.fenghuo.dao.SportDao;
import com.fenghuo.dao.Sreply;
import com.fenghuo.dao.UserDao;
import com.fenghuo.dao.UserUser;

import com.fenghuo.pojo.Message;
import com.fenghuo.pojo.Sport;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.Constant;
import com.fenghuo.utils.Timeutils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ServletSpo extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {}

	
	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		req.setCharacterEncoding("utf-8");
	SportDao sportDao=context.getBean("sportDao", SportDao.class);
	UserUser useruser = context.getBean("userUser", UserUser.class);
	MessageDao messageDao=context.getBean("messageDao", MessageDao.class);

		int flag=Integer.parseInt(req.getParameter("flag"));
	switch (flag) {
	case Constant.JOINSPORT:
		  int uid = Integer.parseInt(req.getParameter("uid"));
		  int sid = Integer.parseInt(req.getParameter("sid"));
		  int suid = Integer.parseInt(req.getParameter("suid"));
		Timestamp time = new Timestamp(System.currentTimeMillis());	
		try {
			boolean joinSport = sportDao.joinSport(uid, sid, suid, time);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", joinSport);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		}
		
		
		break;
	case Constant.CREATESPORT:
		  int uid2 = Integer.parseInt(req.getParameter("uid"));
		  int needperson = Integer.parseInt(req.getParameter("needperson"));
			String title = req.getParameter("title");
			String content = req.getParameter("content");
			String time9=req.getParameter("time");
			System.out.println(time9+"-------------");
			String time3=time9+":00.000";
			System.out.println(time3+"-------------");

			Timestamp time4=Timestamp.valueOf(time3);
			
			
			Timestamp time2 = new Timestamp(System.currentTimeMillis());
			if(time4.before(time2)){
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
				responseOutWithJson(response, jsonObject);
				
			}else{
			Sport sport=new Sport(uid2, title, content, time4, time2, needperson);
		try {JSONObject jsonObject2=new JSONObject();
			Sport temp = sportDao.createSport(sport);
			if(temp!=null){
				jsonObject2.put("id", temp.getId());
				jsonObject2.put("uid", temp.getUid());
				jsonObject2.put("head", userDao.headUser(temp.getUid()));
				jsonObject2.put("uname", userDao.nameUser(temp.getUid()));
				jsonObject2.put("content", temp.getContent());
				jsonObject2.put("title", temp.getTitle());
				jsonObject2.put("time", Timeutils.monthToString( temp.getTime()));
				jsonObject2.put("createtime", Timeutils.monthToString(temp.getCreatetime()));
				jsonObject2.put("needperson", temp.getNeedperson());
				jsonObject2.put("state", true);
			}else{
				jsonObject2.put("state", false);
			}
			responseOutWithJson(response, jsonObject2);
			List<User> userGuanZhu = useruser.userGuanZhu(uid2);
			if(userGuanZhu!=null){
				for(User temp2:userGuanZhu){
					Message message=new Message("发布了活动： "+title, uid2, temp2.getId(), (short)4, sport.getId(), time2);
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
		
		}}
		
		break;
	case Constant.QUITSPORT:
		 int uid3 = Integer.parseInt(req.getParameter("uid"));
		  int sid3= Integer.parseInt(req.getParameter("sid"));
		  int suid3 = Integer.parseInt(req.getParameter("suid"));
		Timestamp time6 = new Timestamp(System.currentTimeMillis());	
		try {
			boolean quxiaoSport = sportDao.quxiaoSport(uid3, sid3, suid3, time6);
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("state", quxiaoSport);
			responseOutWithJson(response, jsonObject2);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		}
		
		break;
	case Constant.REPLYSPORT:
		   int uid4 = Integer.parseInt(req.getParameter("uid"));
		   int sid4= Integer.parseInt(req.getParameter("spoid"));
		   System.out.println(sid4+"sid4-----");
		   System.out.println(uid4+"uid4-----");
	       String content2 = req.getParameter("content");
		   Timestamp time7=new Timestamp(System.currentTimeMillis());
          Sreply sreply=new Sreply(uid4, content2, time7);
		try {
			boolean replySport = sportDao.replySport(sreply, sid4);
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", replySport);
			responseOutWithJson(response, jsonObject);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		}
		break;

	case Constant.SPORTREPLY:
	int page=Integer.parseInt(req.getParameter("page"));
	int sid6=Integer.parseInt(req.getParameter("sid"));
		try {
			List<Sreply> sreplyQuery = sportDao.sreplyQuery(sid6, page);
			int replycount=sportDao.sportCount(sid6);
			responseOutWithJson(response, jsonReply(sreplyQuery, replycount));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
			responseOutWithJson(response, jsonObject);
		}
		
		break;
	case Constant.ALLSPORT:
		System.out.println("connect all sport");
		int page2=Integer.parseInt(req.getParameter("page"));
		List<Sport> allSport = sportDao.allSport(page2);
		long sportcount=sportDao.queryCount();
		if(page2==1){List<Sport> topSport = sportDao.TopSport();
		topSport.addAll(allSport);
		responseOutWithJson(response, jsonList(topSport, sportcount));
		}else{responseOutWithJson(response, jsonList(allSport, sportcount));}
	
		
		
		break;
	case Constant.TITLESPORT:
		int page3=Integer.parseInt(req.getParameter("page"));
		String title2=req.getParameter("title");
		long sportcount2=sportDao.queryCount();
		if(sportcount2<1){
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("state", false);
		}else{
		List<Sport> allSport2 = sportDao.titleSport(title2,page3);
		
	
		responseOutWithJson(response, jsonList(allSport2, sportcount2));
		}
		break;
	case Constant.DAOSREPLY:
		
		int page8=Integer.parseInt(req.getParameter("page"));
		int sid8=Integer.parseInt(req.getParameter("sid"));
			try {
				List<Sreply> sreplyQuery = sportDao.sreplydaoQuery(sid8, page8);
				int replycount=sportDao.sportCount(sid8);
				responseOutWithJson(response, jsonReply(sreplyQuery, replycount));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
				responseOutWithJson(response, jsonObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
				responseOutWithJson(response, jsonObject);
			}
		break;
	case Constant.LOUZHUSREPLY:
		int page9=Integer.parseInt(req.getParameter("page"));
		int sid9=Integer.parseInt(req.getParameter("sid"));
		int suid9=Integer.parseInt(req.getParameter("suid"));
			try {
				
				List<Sreply> sreplyQuery = sportDao.sreplylouQuery(sid9,suid9, page9);
				int replycount=sportDao.sportCount(sid9);
				responseOutWithJson(response, jsonReply(sreplyQuery, replycount));
			
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
				responseOutWithJson(response, jsonObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("state", false);
				responseOutWithJson(response, jsonObject);
			}
		break;
	case Constant.UPSPO:
		int sid10=Integer.parseInt(req.getParameter("sid"));
	    Sport sport10=sportDao.idSport(sid10);
	    if(sport10!=null){
		int uid8 = Integer.parseInt(req.getParameter("uid"));
		int needperson3 = Integer.parseInt(req.getParameter("needperson"));
		String title3= req.getParameter("title");
		String content3 = req.getParameter("content");
		String time10=req.getParameter("time");
		String time11=time10+":00.000";
		Timestamp time12=Timestamp.valueOf(time11);
		sport10.setContent(content3);
		sport10.setUid(uid8);
		sport10.setNeedperson(needperson3);
		sport10.setTime(time12);
		sport10.setTitle(title3);
		JSONObject jsonObject2=new JSONObject();
		sportDao.UpSport(sport10);
		jsonObject2.put("state", true);
		responseOutWithJson(response, jsonObject2);}else{
			
			JSONObject jsonObject2=new JSONObject();
			jsonObject2.put("state", false);
			responseOutWithJson(response, jsonObject2);
		}
	    
		break;

	case Constant.DELETESPORT:
		int sid11=Integer.parseInt(req.getParameter("sid"));
JSONObject jsonObject=new JSONObject();
jsonObject.put("state", sportDao.deleteSport(sid11));
responseOutWithJson(response, jsonObject);
		
		
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
	UserDao userDao=context.getBean("userDao", UserDao.class);
	public JSONObject jsonReply(List<Sreply> list,int replycount){
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (list != null) {
			for (Sreply temp :list) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("uid",temp.getUid());
				object.put("head",userDao.headUser(temp.getUid()));
			    object.put("content", temp.getContent());
				object.put("time", Timeutils.monthToString(temp.getTime()));
				object.put("uname",userDao.nameUser(temp.getUid()));
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
 	    	
	public JSONObject jsonList(List<Sport> alllist, long allcount) {

		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (alllist != null) {
			for (Sport temp : alllist) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("uid", temp.getUid());
				object.put("head", userDao.headUser(temp.getUid()));
				object.put("uname", userDao.nameUser(temp.getUid()));
			System.out.println(temp.getUid()+"__vvvvv");
				object.put("content", temp.getContent());
				object.put("title", temp.getTitle());
				object.put("time", Timeutils.monthToString( temp.getTime()));
				object.put("createtime", Timeutils.monthToString(temp.getCreatetime()));
				object.put("needperson", temp.getNeedperson());
				jsonArray.add(object);
			}
			jsonObject.put("state", true);
			jsonObject.put("count", allcount);
			jsonObject.put("allspo", jsonArray);
		} else {
		jsonObject.put("state", false);
		}
		System.out.println(jsonObject.toString()+"wuyu");
		return jsonObject;
	}
}

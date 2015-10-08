package com.fenghuo.server;


import java.io.IOException;




import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//import com.fenghuo.dao.ClubUserDao;
import com.fenghuo.dao.ClubDao;
import com.fenghuo.dao.InvitationDao;
import com.fenghuo.dao.ReplyDao;
import com.fenghuo.dao.SportDao;
import com.fenghuo.dao.UserDao;
import com.fenghuo.dao.UserUser;
import com.fenghuo.pojo.Club;
import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Sport;
import com.fenghuo.pojo.User;
import com.fenghuo.utils.Constant;
import com.fenghuo.utils.Timeutils;

public class ServletUser extends HttpServlet {

	/**
	 * 
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
	

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		SportDao sportDao=context.getBean("sportDao", SportDao.class);
		UserUser userUser = context.getBean("userUser", UserUser.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		ReplyDao replyDao = context.getBean("replyDao", ReplyDao.class);
 	      ClubDao clubDao = context.getBean("clubDao", ClubDao.class);

		InvitationDao invitationDao = context.getBean("invitationDao",
				InvitationDao.class);
		req.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		int flag=Integer.parseInt(req.getParameter("flag"));
		switch (flag) {
		case Constant.REGISTER:
			JSONObject jsonObject1 = new JSONObject();
			String name =  req.getParameter("name");
			String pass =  req.getParameter("pass");
			String gender =  req.getParameter("gender");
			String college =  req.getParameter("college");
			System.out.println(college+"----------");
			String level =  req.getParameter("level");
			String personal =  req.getParameter("personal");
			int head = Integer.parseInt( req.getParameter("head"));
		Timestamp time = new Timestamp(System.currentTimeMillis());
		System.out.println(time+"--------");
			User user = new User(name, pass, gender, head, college, personal,
					level, time);
		boolean	success = userDao.saveUser(user);
	        jsonObject1.put("state", success);
			responseOutWithJson(response, jsonObject1);
		case Constant.LOGIN:
			JSONObject jsonObject2 = new JSONObject();
			name = req.getParameter("name");
			pass = req.getParameter("pass");
			if (userDao.userLogin(name, pass) != null) {
			success = true;
			  User user2 = userDao.userLogin(name, pass);
				jsonObject2 = new JSONObject();
				jsonObject2.put("id", user2.getId());
				jsonObject2.put("gender", user2.getGender());
				jsonObject2.put("name", name);
				jsonObject2.put("head", user2.getHead());
				jsonObject2.put("level", user2.getLevel());
				jsonObject2.put("college", user2.getCollege());
				System.out.println(user2.getCollege()+"------------------");
				jsonObject2.put("personal", user2.getPersonal());
				jsonObject2.put("state", success);
				responseOutWithJson(response, jsonObject2);
			} else {
				success = false;
				System.out.println(success + "----------");
				jsonObject2.put("state", false);
				System.out.println(success + "----------");
			}responseOutWithJson(response, jsonObject2);
			break;
		case Constant.UPDATEUSER:
			JSONObject jsonObject3 = new JSONObject();
			int id =Integer.parseInt(req.getParameter("uid"));
			User user2 = userDao.idUser(id);
			user2.setLevel(req.getParameter("level"));
			user2.setCollege(req.getParameter("college"));
			user2.setHead(Integer.parseInt(req.getParameter("head")));
			user2.setPass(req.getParameter("pass"));
		boolean	success2 = userDao.updateUser(user2);
			jsonObject3.put("state", success2);
			responseOutWithJson(response, jsonObject3);
			break;
		case Constant.QUERYUSER:
			JSONObject jsonObject4 = new JSONObject();
			name = req.getParameter("name");
			User user5=userDao.queryUser(name);
			if (user5 != null) {
				jsonObject4.put("state", true);
				jsonObject4.put("id", user5.getId());
				jsonObject4.put("name", user5.getName());
				jsonObject4
						.put("college", user5.getCollege());
				jsonObject4.put("level", user5.getLevel());
				jsonObject4.put("gender",user5.getGender());
				jsonObject4.put("personal", user5
						.getPersonal());
				jsonObject4.put("head", user5.getHead());
				jsonObject4.put("guanzhucount", userUser.guanzhuCount(user5.getId()));

			} else {
				jsonObject4.put("state", false);
			}
			responseOutWithJson(response, jsonObject4);
			;
			break;
		case Constant.QUERYUSERID:
			JSONObject jsonObject8 = new JSONObject();
			int uid = Integer.parseInt(req.getParameter("uid"));
			System.out.println(uid+"-----");
			User user6=userDao.idUser(uid); 

			if (user6 != null) {
				jsonObject8.put("state", true);
				jsonObject8.put("id", uid);
				jsonObject8
						.put("college", user6.getCollege());
				jsonObject8.put("gender",user6.getGender());
				jsonObject8.put("name",user6.getName());
				jsonObject8.put("level", user6.getLevel());

				jsonObject8.put("personal",user6
						.getPersonal());
				jsonObject8.put("head", user6.getHead());
				jsonObject8.put("guanzhucount", userUser.guanzhuCount(uid));
			} else {
				jsonObject8.put("state", false);
			}
			responseOutWithJson(response, jsonObject8);
			;

			break;
		case Constant.GUANZHUUSER:

			JSONObject jsonObject5 = new JSONObject();

			int id1 = Integer.parseInt(req.getParameter("uid"));
			int fid1 =  Integer.parseInt(req.getParameter("fid"));
			try {   
				boolean success3 = userUser.guanzhuUser(id1, fid1);
				jsonObject5.put("state", success3);
				responseOutWithJson(response, jsonObject5);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Constant.QUXIAOGUANZHU:
			JSONObject jsonObject6 = new JSONObject();

			int id2 = Integer.parseInt(req.getParameter("uid"));
			int fid2 =  Integer.parseInt(req.getParameter("fid"));
			try {
			boolean	success4 = userUser.quxiaoUser(id2, fid2);
				jsonObject6.put("state", success4);
				responseOutWithJson(response, jsonObject6);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case Constant.USERCLUB:
			JSONObject jsonObject7 = new JSONObject();
			JSONArray jsonArray7 = new JSONArray();
			int id3 = Integer.parseInt(req.getParameter("uid"));
			System.out.println(id3+"---------------");
				if (userDao.idUser(id3).getUserinv().size() != 0) {
					for (Invitation temp : userDao.idUser(id3).getUserinv()) {
						if(temp.getType()==5&&clubDao.queryinvClub(temp.getId())!=null){
							Club queryinvClub = clubDao.queryinvClub(temp.getId());
						JSONObject object = new JSONObject();
						object.put("id", queryinvClub.getId());
						object.put("uid", queryinvClub.getUid());
						object.put("name", queryinvClub.getName());
						object.put("content", queryinvClub.getIntroduce());
						object.put("invid", queryinvClub.getInvid());
						object.put("head", queryinvClub.getHead());
						object.put("time",Timeutils.yearToString(queryinvClub.getTime()));
						object.put("number",invitationDao.queryuserCount(queryinvClub.getInvid()));
						jsonArray7.add(object);
						}
						
					
					}if(jsonArray7.size()!=0){
						jsonObject7.put("state", true);
						jsonObject7.put("userinv", jsonArray7);}else{jsonObject7.put("statec", false);}
					} else {
						jsonObject7.put("state", false);}
				responseOutWithJson(response, jsonObject7);
			
			break;

		case Constant.USERGUANZHU:
			JSONObject jsonObject9 = new JSONObject();
			JSONArray jsonArray9 = new JSONArray();
			int id4 = Integer.parseInt(req.getParameter("uid"));			
			
				if (userUser.userGuanZhu(id4) != null) {
					for (User temp2 : userUser.userGuanZhu(id4)) {
						JSONObject object = new JSONObject();
						object.put("id", temp2.getId());
						object.put("name", temp2.getName());
						object.put("college", temp2.getCollege());
						object.put("gender", temp2.getGender());
						object.put("personal", temp2.getPersonal());
						object.put("level", temp2.getLevel());
						object.put("head", temp2.getHead());
						object.put("guanzhucount",userUser.guanzhuCount(temp2.getId()));
						
						jsonArray9.add(object);
					}
					jsonObject9.put("state", true);
					jsonObject9.put("userguanzhu", jsonArray9);
				} else {

					jsonObject9.put("state", false);
				}

				responseOutWithJson(response, jsonObject9);

		
			break;
		case Constant.USERINVITATION:
			JSONObject jsonObject10 = new JSONObject();
			JSONArray jsonArray10 = new JSONArray();
			int id5 = Integer.parseInt(req.getParameter("uid"));			
				if (userUser.userInv(id5).size() != 0) {
					for (Invitation temp : userUser.userInv(id5)) {
						JSONObject object = new JSONObject();
						object.put("head", userDao.headUser(temp.getUid()));
						object.put("id", temp.getId());
						object.put("iuid", temp.getUid());
						object.put("uname", userDao.nameUser(temp.getUid()));
						object.put("title", temp.getTitle());
						object.put("type", temp.getType());
						object.put("time", Timeutils.monthToString(temp.getTime()));
						object.put("zan",replyDao.replyCount(temp.getId()));
						object.put("createtime", Timeutils.monthToString(temp.getCreatetime()));
						jsonArray10.add(object);
					}
					System.out.println(jsonArray10.toString()+"-----------");
					jsonObject10.put("state", true);
					jsonObject10.put("userinv", jsonArray10);
				} else {

					jsonObject10.put("state", false);
				}
				responseOutWithJson(response, jsonObject10);
			

			break;
		case Constant.SPORTUSER:
			
			JSONObject jsonObject11 = new JSONObject();
			JSONArray jsonArray11 = new JSONArray();
			int id6 = Integer.parseInt(req.getParameter("sid"));
			Set<User> queryUser = sportDao.queryUser(id6);
			
			if (queryUser != null) {
				for (User temp3 : queryUser) {
					JSONObject object = new JSONObject();
					object.put("id", temp3.getId());
					object.put("name", temp3.getName());
					object.put("college", temp3.getCollege());
					object.put("gender", temp3.getGender());
					object.put("personal", temp3.getPersonal());
					object.put("level", temp3.getLevel());
					object.put("head", temp3.getHead());
					jsonArray11.add(object);
				}
				jsonObject11.put("state", true);
				jsonObject11.put("user", jsonArray11);
			} else {

				jsonObject11.put("state", false);
			}

			responseOutWithJson(response, jsonObject11);

			
			break;
			
			
			
			
			
		case Constant.USERSPORT:
			int id8 = Integer.parseInt(req.getParameter("uid"));
		Set<Sport> list=userDao.idUser(id8).getUserspo();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		if (list.size()!=0) {
			for (Sport temp : list) {
				JSONObject object = new JSONObject();
				object.put("id", temp.getId());
				object.put("uid", temp.getUid());
				object.put("head", userDao.headUser(temp.getUid()));
				object.put("uname", userDao.nameUser(temp.getUid()));
				object.put("content", temp.getContent());
				object.put("title", temp.getTitle());
				object.put("time", Timeutils.monthToString( temp.getTime()));
				object.put("createtime", Timeutils.monthToString( temp.getCreatetime()));
				object.put("needperson", temp.getNeedperson());
				jsonArray.add(object);
			}
			jsonObject.put("state", true);
			jsonObject.put("allspo", jsonArray);
		} else {
		jsonObject.put("state", false);
		}
			responseOutWithJson(response, jsonObject);
			
			break;
		case Constant.USERFANS:
			
			JSONObject jsonObject12 = new JSONObject();
			JSONArray jsonArray12 = new JSONArray();
			int id11 = Integer.parseInt(req.getParameter("fid"));			
			
				if (userUser.userGuanZhu(id11) != null) {
					for (User temp2 : userUser.userGuanZhu(id11)) {
						JSONObject object = new JSONObject();
						object.put("id", temp2.getId());
						object.put("name", temp2.getName());
						object.put("college", temp2.getCollege());
						object.put("gender", temp2.getGender());
						object.put("personal", temp2.getPersonal());
						object.put("level", temp2.getLevel());
						object.put("head", temp2.getHead());
						jsonArray12.add(object);
					}
					jsonObject12.put("state", true);
					jsonObject12.put("userfans", jsonArray12);
				} else {

					jsonObject12.put("state", false);
				}

				responseOutWithJson(response, jsonObject12);

			
			break;
			
		case Constant.ALLINFO:
			
			int id10 = Integer.parseInt(req.getParameter("uid"));
			JSONObject jsonObjecti = new JSONObject();
			JSONArray jsonArrayi = new JSONArray();
				if (userUser.userInv(id10).size() != 0) {
					for (Invitation temp : userUser.userInv(id10)) {
						JSONObject object = new JSONObject();
						object.put("head", userDao.headUser(temp.getUid()));
						object.put("id", temp.getId());
						object.put("iuid", temp.getUid());
						object.put("uname", userDao.nameUser(temp.getUid()));
						object.put("title", temp.getTitle());
						object.put("type", temp.getType());
						object.put("time", Timeutils.monthToString(temp.getTime()));
						object.put("zan",replyDao.replyCount(temp.getId()));
						object.put("createtime", Timeutils.monthToString(temp.getCreatetime()));
						jsonArrayi.add(object);
					}
					System.out.println(jsonArrayi.toString()+"-----------");
					jsonObjecti.put("statei", true);
					jsonObjecti.put("userinv", jsonArrayi);
				} else {
					jsonObjecti.put("statei", false);
				}
			Set<Sport> lists=userDao.idUser(id10).getUserspo();
			JSONArray jsonArrays = new JSONArray();
			JSONObject jsonObjects = new JSONObject();
			if (lists.size()!= 0) {
				for (Sport temp : lists) {
					JSONObject object = new JSONObject();
					object.put("id", temp.getId());
					object.put("uid", temp.getUid());
					object.put("head", userDao.headUser(temp.getUid()));
					object.put("uname", userDao.nameUser(temp.getUid()));
					object.put("content", temp.getContent());
					object.put("title", temp.getTitle());
					object.put("time", Timeutils.monthToString( temp.getTime()));
					object.put("createtime", Timeutils.monthToString( temp.getCreatetime()));
					object.put("needperson", temp.getNeedperson());
					jsonArrays.add(object);
				}
				jsonObjects.put("states", true);
				jsonObjects.put("allspo", jsonArrays);
			} else {
			jsonObjects.put("states", false);
			}
			JSONObject jsonObjectc = new JSONObject();
			JSONArray jsonArrayc = new JSONArray();
			if (userDao.idUser(id10).getUserinv().size() != 0) {
				for (Invitation temp : userDao.idUser(id10).getUserinv()) {
					if(temp.getType()==5&&clubDao.queryinvClub(temp.getId())!=null){
						Club queryinvClub = clubDao.queryinvClub(temp.getId());
					JSONObject object = new JSONObject();
					object.put("id", queryinvClub.getId());
					object.put("uid", queryinvClub.getUid());
					object.put("name", queryinvClub.getName());
					object.put("content", queryinvClub.getIntroduce());
					object.put("invid", queryinvClub.getInvid());
					object.put("head", queryinvClub.getHead());
					object.put("time",Timeutils.yearToString(queryinvClub.getTime()));
					object.put("number",invitationDao.queryuserCount(queryinvClub.getInvid()));
					jsonArrayc.add(object);
					}
				}if(jsonArrayc.size()!=0){
				jsonObjectc.put("statec", true);
				jsonObjectc.put("userclub", jsonArrayc);}else{jsonObjectc.put("statec", false);}
			} else {
				jsonObjectc.put("statec", false);
			}
//				if (userDao.idUser(id10).getClubuser().size() != 0) {
//					for (Club temp : userDao.idUser(id10).getClubuser()) {
//						JSONObject object = new JSONObject();
//						object.put("id", temp.getId());
//						object.put("uid", temp.getUid());
//						object.put("name", temp.getName());
//						object.put("introduce", temp.getIntroduce());
//						object.put("invid", temp.getInvid());
//						object.put("head", temp.getHead());
//						object.put("time",Timeutils.yearToString(temp.getTime()));
//						object.put("number",invitationDao.queryuserCount(temp.getInvid()));
//						jsonArrayc.add(object);
//					}
//					jsonObjectc.put("statec", true);
//					jsonObjectc.put("userclub", jsonArrayc);
//				} else {
//					jsonObjectc.put("statec", false);
//				}
			
				JSONObject jsonObjectu = new JSONObject();
				JSONArray jsonArrayu = new JSONArray();
				
					if (userUser.userGuanZhu(id10) != null) {
						for (User temp2 : userUser.userGuanZhu(id10)) {
							JSONObject object = new JSONObject();
							object.put("id", temp2.getId());
							object.put("name", temp2.getName());
							object.put("college", temp2.getCollege());
							object.put("gender", temp2.getGender());
							object.put("personal", temp2.getPersonal());
							object.put("level", temp2.getLevel());
							object.put("head", temp2.getHead());
							object.put("guanzhucount",userUser.guanzhuCount(temp2.getId()));
							
							jsonArrayu.add(object);
						}
						jsonObjectu.put("stateu", true);
						jsonObjectu.put("userguanzhu", jsonArrayu);
					} else {

						jsonObjectu.put("stateu", false);
					}
       JSONObject jsObjectall=new JSONObject();
    jsObjectall.put("user", jsonObjectu);
   jsObjectall.put("sport", jsonObjects);
   jsObjectall.put("inv", jsonObjecti);
   jsObjectall.put("club", jsonObjectc);
responseOutWithJson(response, jsObjectall);
			
			break;
		case Constant.ZHIDINGINV:
			int invid=Integer.parseInt(req.getParameter("invid"));
			boolean zhiInv = invitationDao.zhiInv(invid);
			JSONObject jsonObject13=new JSONObject();
			jsonObject13.put("state", zhiInv);
			responseOutWithJson(response, jsonObject13);
			
			
			break;
		case Constant.ZHIDINGSPO:
			int sid=Integer.parseInt(req.getParameter("sid"));
			boolean zhiSpo = sportDao.zhiSpo(sid);
			JSONObject jsonObject14=new JSONObject();
			jsonObject14.put("state", zhiSpo);
			responseOutWithJson(response, jsonObject14);
			
			break;
		case Constant.ADMINUSER:
			
			int uuid=Integer.parseInt(req.getParameter("uid"));
			boolean zhiUser = userDao.orAdmin(uuid);
			JSONObject jsonObject15=new JSONObject();
			jsonObject15.put("state", zhiUser);
			responseOutWithJson(response, jsonObject15);
			
			
			break;
		case Constant.INVUSER:
			int invid2=Integer.parseInt(req.getParameter("invid"));
			Invitation findInvitation = invitationDao.findInvitation(invid2);
			JSONObject jsonObjectuu = new JSONObject();
			JSONArray jsonArrayuu = new JSONArray();
    if(findInvitation!=null){
	Set<User> user3 = findInvitation.getUser();
	
	for (User temp2 : user3) {
		JSONObject object = new JSONObject();
		object.put("id", temp2.getId());
		object.put("name", temp2.getName());
		object.put("college", temp2.getCollege());
		object.put("gender", temp2.getGender());
		object.put("personal", temp2.getPersonal());
		object.put("level", temp2.getLevel());
		object.put("head", temp2.getHead());
		jsonArrayuu.add(object);
	}
	jsonObjectuu.put("state", true);
	jsonObjectuu.put("user", jsonArrayuu);
} else {

	jsonObjectuu.put("state", false);
}
responseOutWithJson(response, jsonObjectuu);	
		break;
		
		case Constant.TOPINV:
			List<Invitation> topInvitation = invitationDao.TopInvitation();
			JSONArray jsonArray2 = new JSONArray();
			JSONObject jsonObject22 = new JSONObject();
			if (topInvitation.size()!=0) {
				for (Invitation temp : topInvitation) {
					JSONObject object = new JSONObject();
					object.put("id", temp.getId());
					object.put("title", temp.getTitle());
					object.put("type", temp.getType());
					object.put("head", userDao.headUser(temp.getUid()));
					object.put("uname",userDao.nameUser(temp.getUid()));
					object.put("time",Timeutils.hourToString(temp.getTime()));
					object.put("createtime",Timeutils.monthToString(temp.getCreatetime()));
					object.put("zan",replyDao.replyCount(temp.getId()) );
					jsonArray2.add(object);
				}
				jsonObject22.put("state", true);
				jsonObject22.put("inv", jsonArray2);
			} else {

				jsonObject22.put("state", false);
			}
			responseOutWithJson(response, jsonObject22);
			
			
			break;
		case Constant.TOPUSER:
			JSONObject jsonObject16=new JSONObject();
			JSONArray jsonArray3=new JSONArray();
			List<User> topUser = userUser.topUser();
			for (User temp2 : topUser) {
				JSONObject object = new JSONObject();
				object.put("id", temp2.getId());
				object.put("name", temp2.getName());
				object.put("college", temp2.getCollege());
				object.put("gender", temp2.getGender());
				object.put("personal", temp2.getPersonal());
				object.put("level", temp2.getLevel());
				object.put("head", temp2.getHead());
				jsonArray3.add(object);
			}
			jsonObject16.put("state", true);
			jsonObject16.put("user", jsonArray3);
			System.out.println(jsonObject16);
			responseOutWithJson(response, jsonObject16);
			
			break;
		default:
			break;
		}}
//	}

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
		}
		finally {
			if (out != null) {
				out.close();
			}
		}
	}
}

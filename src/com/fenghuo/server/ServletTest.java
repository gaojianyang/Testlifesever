package com.fenghuo.server;

import java.io.File;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fenghuo.dao.InvitationDao;
import com.fenghuo.pojo.Image;




public class ServletTest extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

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
    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		InvitationDao invitationDao = context.getBean("invitationDao",
				InvitationDao.class);
		response.setContentType("text/html");
		 boolean isUpload = ServletFileUpload.isMultipartContent(request);
		 System.out.println(request.toString());
		String path= request.getSession().getServletContext().getRealPath("/img");
		 System.out.println(path+"---");
	        if(isUpload){
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            factory.setSizeThreshold(10);
	            factory.setRepository(new File("E:\\upload"));
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            upload.setSizeMax(10*1024*1024);
	            
	         String title="";
	         String truepath="";
	         int invid=0;
	         int uid=0;
	            try{  
	                //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。  
	                List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
	               
	                for(FileItem item:list){  
	                    //获取表单属性名字。  
//	                    String name = item.getFieldName();  
	                    //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
	                    if(item.getFieldName().equals("name")){
	                    	title=item.getString();
	                    }else if(item.getFieldName().equals("invid")){
	                    	invid=Integer.parseInt(item.getString());
	                    }
	                    else if(item.getFieldName().equals("uid")){uid=Integer.parseInt(item.getString());}
	                    else if(item.getFieldName().equals("file")){
	                    	 String value = item.getName();  
		                        //取到最后一个反斜杠。  
		                        int start = value.lastIndexOf("\\");  
		                        //截取上传文件的 字符串名字。+1是去掉反斜杠。  
		                        String filename = value.substring(start+1); 
		                        truepath=filename+path;
		                        OutputStream out = new FileOutputStream(new File(path,filename));  
		                        InputStream in = item.getInputStream();  
		                        int length = 0;  
		                        byte[] buf = new byte[1024];  
		                        System.out.println("获取文件总量的容量:"+ item.getSize());  
		                        while((length = in.read(buf))!=-1){  
		                            out.write(buf,0,length);  
		                        }  
		                        in.close();  
		                        
		                        out.close();  
		                      truepath="http://10.2.16.34:8080/Testlife/img/"+filename;
	                    
	                    }
//	                    if(item.isFormField()){  
//	                        //获取用户具体输入的字符串，  
//	                        String value = item.getString();  
//	                    	request.setAttribute(name, value);
//	                    }  
//	                    //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
//	                    else{   
//	                        //获取路径名  
//	                        String value = item.getName();  
//	                        //取到最后一个反斜杠。  
//	                        int start = value.lastIndexOf("\\");  
//	                        //截取上传文件的 字符串名字。+1是去掉反斜杠。  
//	                        String filename = value.substring(start+1);  
//	                        request.setAttribute(name, filename);  
//	                        /*第三方提供的方法直接写到文件中。 
//	                         * item.write(new File(path,filename));*/ 
//	                        //收到写到接收的文件中。  
//	                        OutputStream out = new FileOutputStream(new File("D:\\png",filename));  
//	                        InputStream in = item.getInputStream();  
//	                        int length = 0;  
//	                        byte[] buf = new byte[1024];  
//	                        System.out.println("获取文件总量的容量:"+ item.getSize());  
//	                        while((length = in.read(buf))!=-1){  
//	                            out.write(buf,0,length);  
//	                        }  
//	                        in.close();  
//	                        out.close();  
//	                    }  
	                }  
	                Timestamp  time=new Timestamp(System.currentTimeMillis());
	                Image image=new Image(invid, uid,title, truepath, time);
	                boolean upLoadimg = invitationDao.upLoadimg(image);
	                JSONObject jsonObject=new JSONObject();
	                jsonObject.put("state", upLoadimg);
	                responseOutWithJson(response, jsonObject);
	                
	            }catch(Exception e){  
	                e.printStackTrace();  
	            }  
	           
	            
	               
	        }  
//	            try {
//	                List<FileItem> DiskFileItems =sfu.parseRequest(request);
//	                String pngname=null;
//	                for(FileItem item : DiskFileItems){
//	                    System.out.println("-------------key:"+ item.getFieldName()); 
//	                    System.out.println("-------------value:"+   item.getString()); 
//	                    System.out.println("-------------inputSream:"+  item.getInputStream()); 
//	                   String name=item.getFieldName();
//	                    
//	                    if(name.equals("name")){ pngname =item.getString();}
//	             
//	                
//	                }
//	            } catch (FileUploadException e) {
//	                e.printStackTrace();
//	            }
	       
	         
	        response.getWriter().print("okok!");
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
	    
	}



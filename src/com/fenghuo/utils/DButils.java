package com.fenghuo.utils;


//import java.beans.PropertyVetoException;

import java.sql.Connection;
import java.sql.SQLException;



import com.mchange.v2.c3p0.ComboPooledDataSource;



//import java.sql.Connection;
//
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//
//
//public class DButils {	
//	private static Connection conn;
//	  private ComboPooledDataSource cpds=null;
////获取数据口连接
////单例模式：从头到尾只保证当前类只会有一个对象
////1、将构造方法私有化
////2、定义一静态方法返回数据库的实例
//
//private DButils(){
//	
//}
//
////返回一个数据库连接，而且保证只有一个对象
//public static Connection getConn() throws ClassNotFoundException, SQLException{
//	
//	
//	if(conn==null || conn.isClosed()){
//		Class.forName("com.mysql.jdbc.Driver");
//		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/way?characterEncoding=utf-8","root","123456");
//	}
//	
//	return conn;
//}
//
////关闭方法
//public static void closeRS(ResultSet rs) throws SQLException{
//	if(rs!=null){
//		rs.close();
//	}
//}
//
//public static void closeConn(Connection conn) throws SQLException{
//	if(conn!=null){
//		conn.close();
//	}
//}
//
//
//}
public class DButils { 
	
    private static DButils dbcputils=null;  
    private   ComboPooledDataSource cpds;  
    private DButils(){  
        if(cpds==null){  
            cpds=new ComboPooledDataSource();  }
//            cpds.setDataSourceName("mydatasource");  
//       
     
//
//        cpds.setUser("root");  
//        cpds.setPassword("123456");  
//        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/way?characterEncoding=utf-8");  
////        <property name="user" value="root"></property>
////		<property name="password" value="123456"></property>
////		<property name="minPoolSize" value="1"></property>
////		<property name="maxPoolSize" value="50"></property>
////		<property name="initialPoolSize" value="1"></property>
////		<property name="maxIdleTime" value="25000"></property>
////		<property name="acquireIncrement" value="1"></property>
//////		 <property name="acquireRetryAttempts"> c3p0.acquireRetryDelay = 1000
////        c3p0.testConnectionOnCheckin = true
////        		c3p0.automaticTestTable = t_c3p0
////        		c3p0.idleConnectionTestPeriod = 18000
////        		c3p0.checkoutTimeout=5000
//        try {  
//            cpds.setDriverClass("com.mysql.jdbc.Driver");  
//        } catch (PropertyVetoException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        }  
//        cpds.setInitialPoolSize(3);  
//        cpds.setMaxIdleTime(5);  
//        cpds.setMaxPoolSize(30);  
//        cpds.setMinPoolSize(1); 
//        cpds.setBreakAfterAcquireFailure(false);
//        cpds.setAcquireRetryAttempts(60);
//        cpds.setNumHelperThreads(3);
//        cpds.setMaxStatements(30);
//        cpds.setAcquireRetryDelay(1000);
//        cpds.setCheckoutTimeout(2000);
//        cpds.setUnreturnedConnectionTimeout(15);
////        cpds.setTestConnectionOnCheckin(true);
////        cpds.setAutomaticTestTable("t_cc3p0");
//        cpds.setAcquireIncrement(1);
////        cpds.setCheckoutTimeout(2000);
////        cpds.setMaxConnectionAge(10);
////        cpds.setUnreturnedConnectionTimeout(5);
////        		c3p0.automaticTestTable = t_c3p0
////        		c3p0.idleConnectionTestPeriod = 18000
////        		c3p0.checkoutTimeout=5000}
//        }
    }  
    public synchronized static DButils getInstance(){  
        if(dbcputils==null){  
            dbcputils=new DButils(); }
        
        return dbcputils;  
    }  
    public Connection getConnection(){  
        try {  
           return cpds.getConnection();  
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return null;  
    }  
//      
//    public static void main(String[] args) throws SQLException {  
//        Connection con=null;  
//        long begin=System.currentTimeMillis();  
//        for(int i=0;i<1000000;i++){  
//            con=DButils.getInstance().getConnection();  
//            con.close();  
//        }     
//        long end=System.currentTimeMillis();  
//        System.out.println("耗时为:"+(end-begin)+"ms");  
//    }  
}  

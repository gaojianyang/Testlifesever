package com.fenghuo.utils;

public class Constant {                                              //1客户端传入                                                                    2.服务器传出  
	public final static int REGISTER=0;        //注册1        name pass gender head college personal            uid  
	public final static int LOGIN=1;           //登入1        name pass                                  uid college personal 
	public final static int UPDATEUSER=2;      //修改用户信息1  pass gender head college personal            true false
	public final static int CREATEINVITATION=3;//发帖2        uid title content type                      invid title createtime 
	public final static int DELETEINVITATION=4;//删帖2        invid                                         true false
	public final static int ALLINVITATION=5;   //查询所有帖子2   flag=5  page                                   list<invitation>    
	public final static int ZONGZANINVITATION=6;   //按赞排序2  flag=6   page                                  list<invitation>
	public final static int TYPEINVITATION=7;  //按类型查询2    flag=7    page                                 list<invitation>
	public final static int TITLEINVITATION=8;  //按标题查询2    title  page
	public final static int CREATEREPLY=9;     //发表回复3      invid uid content                            true false   time
	public final static int DELETEREPLY=10;     //删除回复3x     
	public final static int ALLREPLY=11;       //所有回复3      flag=11  page                                    list<reply>
	public final static int ZANREPLY=12;      //回复按赞排序3    ...
	public final static int PAGEREPLY=13;      //回复按页数排序3x
	public final static int CREATECLUB=14;     //成立社团4      uid title introduce head                      invid
	public final static int DELETECLUB=15;     //删除社团4      cid   uid                                     true
	public final static int ALLCLUB=16;        //查询所有社团4   
	public final static int JOINCLUB=17;       //每个用户参加的社团5 uid                                         list<club>
	public final static int DELETEUSERCLUB=18; //退出社团5         uid cid                                        true
	public final static int MESSAGE=19;        //发送的消息6      content  fromid toid                          true              
	public final static int INVITATIONZAN=20;            //帖子点赞7  invid
	public final static int REPLYZAN=21;            //回復点赞7      invid replyid
	public final static int QUERYMESSAGE=22;   //查询有没有新消息8     uid                                     list<message>  fromname
	public final static int QUERYUSER=23;   //按名字查询用户                       name                                        user
	public final static int GUANZHUUSER=24;   //关注用户                            uid  fid                                          true
	public final static int QUERYGUANZHUUSER=44;   //查找关注用户                                                                                                                     list<user>
	public final static int QUXIAOGUANZHU=25;   //取消关注                     uid   fid
	public final static int ZHOUZANINVITATION=26;   //一周赞的排行   
	public final static int YUEZANINVITATION=27;   //一月的排行
	public final static int CREATESPORT=28;   //成立活动
	public final static int DELETESPORT=29;   //删除活动
	public final static int JOINSPORT=30;   //参加活动
	public final static int QUITSPORT=31;   //QUXIAO活动
	public final static int REPLYSPORT=32;   //HUIFU活动
	public final static int USERCLUB=33;   //HUIFU活动
	public final static int USERINVITATION=34;   //HUIFU活动
	public final static int USERGUANZHU=36;   //HUIFU活动
	public final static int SPORTREPLY=37;
	public final static int USERSPORT=35;   //HUIFU活动
	public final static int SPORTUSER=40;   //HUIFU活动
	public final static int ALLSPORT=38;   //HUIFU活动
	public final static int TITLESPORT=39;   //HUIFU活动
	public final static int GUANZHUINV=40;   //HUIFU活动
	public final static int QUXIAOINV=47;   //HUIFU活动
	public final static int LOUZHUREPLY=41;   //HUIFU活动
	public final static int QUERYUSERID=42;   //HUIFU活动
	public final static int DAOREPLY=43;   //HUIFU活动
	public final static int INVUSER=45;   //HUIFU活动
	public final static int TITLECLUB=46;   //HUIFU活动
	public final static int RECMESSAGE=44;   //HUIFU活动
	public final static int TOPUSER=49;   //HUIFU活动
	public final static int TOPINV=48;   //HUIFU活动
	public final static int ALLINFO=50;   //HUIFU活动
	public final static int USERFANS=51;   //HUIFU活动
	public final static int DAOSREPLY=52;   //HUIFU活动
	public final static int LOUZHUSREPLY=53;  
	public final static int UPSPO=54;
	public final static int ADMINUSER=55;
	public final static int ZHIDINGINV=56;
	public final static int ZHIDINGSPO=57;
	public final static int INVIMAGE=58;
	
}

package com.fenghuo.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Timeutils {

	
	public static String hourToString(Timestamp time){
		String stime="";
		DateFormat sdf=new SimpleDateFormat("HH:mm");
		stime=sdf.format(time);
		return stime;
	}
	public static String monthToString(Timestamp time){
		String stime="";
		DateFormat sdf=new SimpleDateFormat("MM-dd HH:mm");
		stime=sdf.format(time);
		return stime;
	}
	public static String yearToString(Timestamp time){
		String stime="";
		DateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		stime=sdf.format(time);
		return stime;
	}
	
}

package com.fenghuo.utils;

import java.util.ArrayList;
import java.util.List;

import com.fenghuo.pojo.Club;
import com.fenghuo.pojo.Invitation;
import com.fenghuo.pojo.Reply;

public class ListUtils {
	public ListUtils(){}
	public static List<Invitation>  fenye(List<Invitation> alllist,int page){
		List<Invitation> list=new ArrayList<Invitation>();
		 //数据总数
		int totalCount = alllist.size();
		 //总的页数
		int pageCount = 0;
		 //每页显示的总数
		int endNum = 20;
		 //当前页码
		int startNum = page;
		 /*计算出总共能分成多少页*/
		if (totalCount % endNum > 0)      //数据总数和每页显示的总数不能整除的情况
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //数据总数和每页显示的总数能整除的情况
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //当前页数为第一页
		{
		if(totalCount <= endNum)  //数据总数小于每页显示的数据条数
		{
		 //截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //截取起始下标
		int fromIndex = (startNum - 1) * endNum;
		 //截取截止下标
		int toIndex = startNum * endNum;
		 /*计算截取截止下标*/
		if ((totalCount - toIndex) % endNum >= 0)
		 {
		 toIndex = startNum * endNum;
		 }
		 else
		 {
		 toIndex = (startNum - 1) * endNum + (totalCount % endNum);
		 }
		 if (totalCount >= toIndex)
		 {
			 list = list.subList(fromIndex, toIndex);
		 }
		 } 
		 }
		 else
		 {
			 list = null;
		 } 
		
	}

		return list;
		
		
	}
	public static List<Reply>  refenye(List<Reply> alllist,int page){
		List<Reply> list=new ArrayList<Reply>();
		 //数据总数
		int totalCount = alllist.size();
		 //总的页数
		int pageCount = 0;
		 //每页显示的总数
		int endNum = 20;
		 //当前页码
		int startNum = page;
		 /*计算出总共能分成多少页*/
		if (totalCount % endNum > 0)      //数据总数和每页显示的总数不能整除的情况
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //数据总数和每页显示的总数能整除的情况
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //当前页数为第一页
		{
		if(totalCount <= endNum)  //数据总数小于每页显示的数据条数
		{
		 //截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //截取起始下标
		int fromIndex = (startNum - 1) * endNum;
		 //截取截止下标
		int toIndex = startNum * endNum;
		 /*计算截取截止下标*/
		if ((totalCount - toIndex) % endNum >= 0)
		 {
		 toIndex = startNum * endNum;
		 }
		 else
		 {
		 toIndex = (startNum - 1) * endNum + (totalCount % endNum);
		 }
		 if (totalCount >= toIndex)
		 {
			 list = list.subList(fromIndex, toIndex);
		 }
		 } 
		 }
		 else
		 {
			 list = null;
		 } 
		
	}

		return list;
		
		
	}
	public static List<Club>  clfenye(List<Club> alllist,int page){
		List<Club> list=new ArrayList<Club>();
		 //数据总数
		int totalCount = alllist.size();
		 //总的页数
		int pageCount = 0;
		 //每页显示的总数
		int endNum = 20;
		 //当前页码
		int startNum = page;
		 /*计算出总共能分成多少页*/
		if (totalCount % endNum > 0)      //数据总数和每页显示的总数不能整除的情况
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //数据总数和每页显示的总数能整除的情况
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //当前页数为第一页
		{
		if(totalCount <= endNum)  //数据总数小于每页显示的数据条数
		{
		 //截止到总的数据条数(当前数据不足一页，按一页显示)，这样才不会出现数组越界异常
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //截取起始下标
		int fromIndex = (startNum - 1) * endNum;
		 //截取截止下标
		int toIndex = startNum * endNum;
		 /*计算截取截止下标*/
		if ((totalCount - toIndex) % endNum >= 0)
		 {
		 toIndex = startNum * endNum;
		 }
		 else
		 {
		 toIndex = (startNum - 1) * endNum + (totalCount % endNum);
		 }
		 if (totalCount >= toIndex)
		 {
			 list = list.subList(fromIndex, toIndex);
		 }
		 } 
		 }
		 else
		 {
			 list = null;
		 } 
		
	}

		return list;
		
	}

}

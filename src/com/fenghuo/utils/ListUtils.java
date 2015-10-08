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
		 //��������
		int totalCount = alllist.size();
		 //�ܵ�ҳ��
		int pageCount = 0;
		 //ÿҳ��ʾ������
		int endNum = 20;
		 //��ǰҳ��
		int startNum = page;
		 /*������ܹ��ֳܷɶ���ҳ*/
		if (totalCount % endNum > 0)      //����������ÿҳ��ʾ�������������������
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //����������ÿҳ��ʾ�����������������
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //��ǰҳ��Ϊ��һҳ
		{
		if(totalCount <= endNum)  //��������С��ÿҳ��ʾ����������
		{
		 //��ֹ���ܵ���������(��ǰ���ݲ���һҳ����һҳ��ʾ)�������Ų����������Խ���쳣
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //��ȡ��ʼ�±�
		int fromIndex = (startNum - 1) * endNum;
		 //��ȡ��ֹ�±�
		int toIndex = startNum * endNum;
		 /*�����ȡ��ֹ�±�*/
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
		 //��������
		int totalCount = alllist.size();
		 //�ܵ�ҳ��
		int pageCount = 0;
		 //ÿҳ��ʾ������
		int endNum = 20;
		 //��ǰҳ��
		int startNum = page;
		 /*������ܹ��ֳܷɶ���ҳ*/
		if (totalCount % endNum > 0)      //����������ÿҳ��ʾ�������������������
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //����������ÿҳ��ʾ�����������������
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //��ǰҳ��Ϊ��һҳ
		{
		if(totalCount <= endNum)  //��������С��ÿҳ��ʾ����������
		{
		 //��ֹ���ܵ���������(��ǰ���ݲ���һҳ����һҳ��ʾ)�������Ų����������Խ���쳣
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //��ȡ��ʼ�±�
		int fromIndex = (startNum - 1) * endNum;
		 //��ȡ��ֹ�±�
		int toIndex = startNum * endNum;
		 /*�����ȡ��ֹ�±�*/
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
		 //��������
		int totalCount = alllist.size();
		 //�ܵ�ҳ��
		int pageCount = 0;
		 //ÿҳ��ʾ������
		int endNum = 20;
		 //��ǰҳ��
		int startNum = page;
		 /*������ܹ��ֳܷɶ���ҳ*/
		if (totalCount % endNum > 0)      //����������ÿҳ��ʾ�������������������
		{
		pageCount = totalCount / endNum + 1;
		 }
		 else   //����������ÿҳ��ʾ�����������������
		{
		pageCount = totalCount / endNum;
		 }
		 if(totalCount > 0)
		 {
		 if(startNum <= pageCount)
		 {
		 if(startNum == 1)     //��ǰҳ��Ϊ��һҳ
		{
		if(totalCount <= endNum)  //��������С��ÿҳ��ʾ����������
		{
		 //��ֹ���ܵ���������(��ǰ���ݲ���һҳ����һҳ��ʾ)�������Ų����������Խ���쳣
		list = list.subList(0, totalCount);
		 }
		 else
		 {
			 list= list.subList(0, endNum);
		 }
		 }
		 else
		 {
		 //��ȡ��ʼ�±�
		int fromIndex = (startNum - 1) * endNum;
		 //��ȡ��ֹ�±�
		int toIndex = startNum * endNum;
		 /*�����ȡ��ֹ�±�*/
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

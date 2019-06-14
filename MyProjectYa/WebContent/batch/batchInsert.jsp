<%@page import="vo.Smember"%>
<%@page import="dao.MemberDao"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	List<Smember> list=new ArrayList<Smember>();
	
	for(int i=0; i<=100; i++){
		Smember member=new Smember();
		member.setId("user"+(i+1));
		member.setPassword("1234");
		member.setName("사용자"+(i+1));
// 		member.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		list.add(member);
	}
	
	///////////////////
	
	MemberDao memberDao=MemberDao.getInstance();
	
// 	for(Member member:list){
// 		memberDao.insert(member);
// 	}
	
	
	memberDao.batchInsert(list); //1000명의 회원정보 insert
	
%>
배치 insert 작업완료<br>

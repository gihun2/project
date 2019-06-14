<%@page import="org.json.simple.JSONArray"%>
<%@page import="dao.MemberDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
MemberDao memberDao=MemberDao.getInstance();

JSONArray jsonArray=memberDao.getCountPerAddress();

out.println(jsonArray);
%>
<%//=jsonArray%>
package action.member;

import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class JoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//회원가입 처리
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		String name=request.getParameter("name");
		String strBirthday=request.getParameter("birthday");
		String gender=request.getParameter("gender");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String phone=request.getParameter("phone");
		String mobile=request.getParameter("mobile");

		/* 방법1:
		 (문자열 날짜정보
		 -> SimpleDateFormat을 이용해 java.util.Date 로 변환
		 -> java.util.date를 java.sql.Date로 변환)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date date=sdf.parse(strBirthday);
		java.sql.Date birthday=new java.sql.Date(date.getTime());
		*/
		// 방법2 (문자열 날짜정보-> java.sql.Date로 변환)
		// java.sql.Date.valueOf() 메소드는 "yyyy-mm-dd" 형식으로 파싱함.
		// java.sql.Date birthday = java.sql.Date.valueOf(strBirthday);		
		
		Timestamp birthday = Timestamp.valueOf(strBirthday + " 00:00:00");
		
		Smember member = new Smember(id, pass, name, birthday, gender, email, address, phone, mobile);
		
		MemberDao memberDao=MemberDao.getInstance();
		memberDao.insert(member); //회원가입 처리 완료
		
		
		
		//회원가입 처리완료되면 클라이언트를 로그인 페이지로 이동시켜줘야함
		/*
		ActionForward forward=new ActionForward();
		forward.setRedirect(true); //리다이렉트면 명령어형식
		forward.setPath("loginForm.do");
		return forward;
		*/
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('회원가입이 완료되었습니다.');");
		out.println("location.href = 'loginForm.do';");
		out.println("</script>");
		out.flush();
		out.close();
		return null;
	}

}

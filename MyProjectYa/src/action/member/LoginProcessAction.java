package action.member;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

public class LoginProcessAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		
		ActionForward forward=null;
		
		//DB작업 객체 생성
		MemberDao memberDao=MemberDao.getInstance();
		int check=memberDao.loginCheck(id, pass);
		if(check!=1) { //로그인 실패시
			String message="";
			if(check==-1) { //ID 불일치
				message="해당하는 ID가 없습니다...ㅠㅠ";
			} else if(check==0) { //Password 불일치
				message="비밀번호가 일치하지 않습니다..ㅠㅠ";
			}
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('"+message+"');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
//		if(check==-1) { //ID 불일치
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out=response.getWriter();
//			out.println("<script>");
//			out.println("alert('해당하는 ID가 없습니다...ㅠㅠ');");
//			out.println("history.back();");
//			out.println("</script>");
//			out.close();
//			return null;
//		} else if(check==0) { //Password 불일치
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out=response.getWriter();
//			out.println("<script>");
//			out.println("alert('비밀번호가 일치하지 않습니다..ㅠㅠ');");
//			out.println("// location.href='loginForm.me'");
//			out.println("history.back();");
//			out.println("</script>");
//			// out.flush(); // 버퍼비우기(물내리기)
//			out.close(); // close()시에 flush()를 내부적으로 실행하고 자원을 닫는다.
//			return null; //프론트 컨트롤러의 3단계 작업 생략하도록 null 리턴.
//		} 
		
		//모두 일치(로그인 성공)
		//세션구하기
		HttpSession session=request.getSession();
		session.setAttribute("id", id);
		
		//쿠키 만들기
		Cookie cookies=new Cookie("Project_ID", id);
		cookies.setMaxAge(60*60);
		response.addCookie(cookies);
		
		// .me로 끝나면 명령어 형식
		// -> 프론트 컨트롤러로 다시 입력을 유도
		// -> 리다이렉트로 브라우저가 재요청하도록 함.
		forward=new ActionForward();
		forward.setPath("main.do");  
		forward.setRedirect(true); // 리다이렉트(브라우저 재요청)
		return forward;
		
	}
	
}

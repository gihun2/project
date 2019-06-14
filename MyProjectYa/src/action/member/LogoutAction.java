package action.member;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import vo.ActionForward;

public class LogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id=request.getParameter("id");
		Cookie[] cookies=request.getCookies();
		
		if(cookies != null) {
			HttpSession session=request.getSession();
			session.removeAttribute("id");
			
			for(Cookie tempCookie : cookies) {
				if(tempCookie.getName().equals("Project_ID")){
					tempCookie.setMaxAge(0);
					response.addCookie(tempCookie);
					break;
				}
			}
		}
		
		/*방법1:
		ActionForward forward=new ActionForward();
		forward.setPath("main.do");
		forward.setRedirect(true);
		return forward;
		*/
		//방법2:
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('logout 되었습니다.');");
		out.println("location.href='main.do';");
		out.println("</script>");
		out.close();
		return null;
	}

}

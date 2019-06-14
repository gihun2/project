package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;

public class BoardAdminDeleteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// int num, String pass 파라미터 가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		String pass = request.getParameter("pass");

		// DB객체 준비
		BoardDao boardDao = BoardDao.getInstance();
		// 메소드호출 deleteAllBoard(num, pass)
		boolean isSuccess = boardDao.deleteBoard(num, pass);

		// (글패스워드가 달라서)삭제 실패시
		if (!isSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('글 패스워드가 틀륍니다...');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}

		// 삭제 성공시
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('삭제 성공했습니다.');");
		out.println("location.href='boardAllShowForm.do'");
		out.println("</script>");
		out.close();
		return null;
	}

}
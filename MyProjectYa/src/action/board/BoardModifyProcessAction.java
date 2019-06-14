package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardModifyProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("boardModifyProcessAction");
		
		
		// num name pass subject content 파라미터 가져오기
		Sboard board=new Sboard();
		board.setNum(Integer.parseInt(request.getParameter("num")));
		board.setName(request.getParameter("name"));
		board.setPass(request.getParameter("pass"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		System.out.println("board: "+board);
		
		//DB객체 준비
		BoardDao boardDao=BoardDao.getInstance();
		//메소드 호출 updateBoard(board)
		//boolean isSuccess==true 수정성공
		//                 ==false 수정실패
		boolean isSuccess=boardDao.updateBoard(board);	
		System.out.println("isSuccess: "+isSuccess);
		//글수정 실패시
		if(!isSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('글 비밀번호 틀륌.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		//페이지번호 파라미터 가져오기
		String pageNum=request.getParameter("pageNum");
		
		//글수정 성공시
		//수정한 글이 있는 페이지로 글목록 요청
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do?pageNum=" + pageNum);
		forward.setRedirect(true);
		return forward;
	}

}

package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//글번호 파라미터 가져오기
		int num= Integer.parseInt(request.getParameter("num"));	
		
		//DB객체 준비		
		BoardDao boardDao=BoardDao.getInstance();
		//조회수 1증가
		boardDao.updateReadcount(num);
		//글번호에 해당하는 글 전체내용 가져오기
		Sboard board=boardDao.getBoard(num);
		
		request.setAttribute("board", board); //글번호 해당하는 글내용
		
		ActionForward forward=new ActionForward();
		forward.setPath("center/content");
		forward.setRedirect(false);
		return forward;		
	}

}//BoardDetailAction()

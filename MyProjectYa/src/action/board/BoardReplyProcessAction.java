package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardReplyProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//페이지 번호 파라미터 가져오기
		String pageNum=request.getParameter("pageNum");
		
		Sboard board=new Sboard();
		//사용자 직접 입력 값 파라미터 가져와서 저장
		board.setName(request.getParameter("name"));
		board.setPass(request.getParameter("pass"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		//[답글을 다는 대상글]의 답글관련 정보 파라미터 가져와서 저장
		board.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		board.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		board.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		// 답글 작성자의 IP주소 가져와서 저장
		board.setIp(request.getRemoteAddr());
		
		//DB객체 준비
		BoardDao boardDao=BoardDao.getInstance();
		//메소드호출 replyInsert(board) 답글쓰기
		boardDao.replyInsert(board);
		
		//답글쓰기 작업이 완료되면 기존 글목록 화면으로 이동
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do?pageNum="+pageNum);
		forward.setRedirect(true);
		return forward;
	}

}//boardReplyProcessAction

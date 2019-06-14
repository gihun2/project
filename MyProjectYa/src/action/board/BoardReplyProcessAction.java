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
		
		//������ ��ȣ �Ķ���� ��������
		String pageNum=request.getParameter("pageNum");
		
		Sboard board=new Sboard();
		//����� ���� �Է� �� �Ķ���� �����ͼ� ����
		board.setName(request.getParameter("name"));
		board.setPass(request.getParameter("pass"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		//[����� �ٴ� ����]�� ��۰��� ���� �Ķ���� �����ͼ� ����
		board.setRe_ref(Integer.parseInt(request.getParameter("re_ref")));
		board.setRe_lev(Integer.parseInt(request.getParameter("re_lev")));
		board.setRe_seq(Integer.parseInt(request.getParameter("re_seq")));
		// ��� �ۼ����� IP�ּ� �����ͼ� ����
		board.setIp(request.getRemoteAddr());
		
		//DB��ü �غ�
		BoardDao boardDao=BoardDao.getInstance();
		//�޼ҵ�ȣ�� replyInsert(board) ��۾���
		boardDao.replyInsert(board);
		
		//��۾��� �۾��� �Ϸ�Ǹ� ���� �۸�� ȭ������ �̵�
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do?pageNum="+pageNum);
		forward.setRedirect(true);
		return forward;
	}

}//boardReplyProcessAction

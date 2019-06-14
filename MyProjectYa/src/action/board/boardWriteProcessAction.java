package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class boardWriteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String name = request.getParameter("name");
//		String pass = request.getParameter("pass");
//		String subject = request.getParameter("subject");
//		String content = request.getParameter("content");
		
		//�Ķ���� �� �����ͼ� Board ��ü�� �����
		Sboard board = new Sboard();
		board.setName(request.getParameter("name"));
		board.setPass(request.getParameter("pass"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		//���ۼ��� IP�ּ� �� ����
		board.setIp(request.getRemoteAddr());
		
		//DB�۾� ��ü �غ� BoardDao
		BoardDao boardDao=BoardDao.getInstance();
		//�޼ҵ� ȣ�� insert(board) ���ۼ� �Ϸ�
		boardDao.insert(board);
		//�۸��(boardList.do)ȭ������ �̵�. �����̷�Ʈ
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do");
		forward.setRedirect(true);
		return forward;
	}

}

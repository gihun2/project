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
		//�۹�ȣ �Ķ���� ��������
		int num= Integer.parseInt(request.getParameter("num"));	
		
		//DB��ü �غ�		
		BoardDao boardDao=BoardDao.getInstance();
		//��ȸ�� 1����
		boardDao.updateReadcount(num);
		//�۹�ȣ�� �ش��ϴ� �� ��ü���� ��������
		Sboard board=boardDao.getBoard(num);
		
		request.setAttribute("board", board); //�۹�ȣ �ش��ϴ� �۳���
		
		ActionForward forward=new ActionForward();
		forward.setPath("center/content");
		forward.setRedirect(false);
		return forward;		
	}

}//BoardDetailAction()

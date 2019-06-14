package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// int num �Ķ���� ��������
		int num=Integer.parseInt(request.getParameter("num"));
		
		//DB��ü �غ�
		BoardDao boardDao= BoardDao.getInstance();
		Sboard board=boardDao.getBoard(num);
		
		//request ������ü�� ����
		request.setAttribute("board", board);
		
		//����ġ ������� jsp��� �̵��Ҷ� ������ ������ request��ü�� ���޵ȴ�.
//		request.setAttribute("num", num); 
		//�۹�ȣ num�� ������ü�� ���� ������ �ʿ䰡 ����.
		//���� request��ü�̹Ƿ� �۹�ȣ �Ķ���ͷ� �ٷ� ã�� �� �ִ�.
		
		//update.jsp�� �̵�
		ActionForward forward=new ActionForward();
		forward.setPath("center/fileUpdate");
		forward.setRedirect(false); //����ġ ��� �̵�
		return forward;
	}

}//BoardFileModifyFormAction

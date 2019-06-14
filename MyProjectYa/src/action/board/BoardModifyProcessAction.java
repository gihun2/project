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
		
		
		// num name pass subject content �Ķ���� ��������
		Sboard board=new Sboard();
		board.setNum(Integer.parseInt(request.getParameter("num")));
		board.setName(request.getParameter("name"));
		board.setPass(request.getParameter("pass"));
		board.setSubject(request.getParameter("subject"));
		board.setContent(request.getParameter("content"));
		
		System.out.println("board: "+board);
		
		//DB��ü �غ�
		BoardDao boardDao=BoardDao.getInstance();
		//�޼ҵ� ȣ�� updateBoard(board)
		//boolean isSuccess==true ��������
		//                 ==false ��������
		boolean isSuccess=boardDao.updateBoard(board);	
		System.out.println("isSuccess: "+isSuccess);
		//�ۼ��� ���н�
		if(!isSuccess) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('�� ��й�ȣ Ʋ��.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
		//��������ȣ �Ķ���� ��������
		String pageNum=request.getParameter("pageNum");
		
		//�ۼ��� ������
		//������ ���� �ִ� �������� �۸�� ��û
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do?pageNum=" + pageNum);
		forward.setRedirect(true);
		return forward;
	}

}

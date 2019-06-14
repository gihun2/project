package action.board;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;

public class BoardDeleteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// int num, String pass �Ķ���� �������� 
		int num=Integer.parseInt(request.getParameter("num"));
		String pass=request.getParameter("pass");
		
		//DB��ü �غ�
		BoardDao boardDao = BoardDao.getInstance();
		//�޼ҵ�ȣ�� deleteBoard(num, pass)
		boolean isSuccess=boardDao.deleteBoard(num, pass);
		
		// (���н����尡 �޶�)���� ���н�
		if(!isSuccess) { 
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('�� �н����尡 Ʋ���ϴ�...');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		//������ ��ȣ �Ķ���� ��������
		String pageNum=request.getParameter("pageNum");
		
		//���� ������
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do?pageNum="+pageNum);
		forward.setRedirect(true);
		return forward;
	}

}

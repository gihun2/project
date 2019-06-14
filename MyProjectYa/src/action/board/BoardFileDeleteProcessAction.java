package action.board;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileDeleteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// int num, String pass �Ķ���� �������� 
				int num=Integer.parseInt(request.getParameter("num"));
				String pass=request.getParameter("pass");
				
				//DB��ü �غ�
				BoardDao boardDao = BoardDao.getInstance();
				
				//�޼ҵ� ȣ�� boolean checkBoard(num, pass)
				boolean result=boardDao.checkBoard(num, pass);
				// (���н����尡 �޶�)���� ���н�
				if(!result) { 
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out=response.getWriter();
					out.println("<script>");
					out.println("alert('�� �н����尡 Ʋ���ϴ�...');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return null;
				}
				// ���н����� ��ġ�ϸ� �����̸� �����ͼ� ���ϻ��� ó��
					Sboard board=boardDao.getBoard(num);
					String filename=board.getFilename();
				
					//application ������ü ��������
					ServletContext application=request.getServletContext();
					String realPath=application.getRealPath("/upload");
					String deleteFilePath=realPath+"/"+filename;
					System.out.println("������ ���ϰ��:: "+deleteFilePath);
					
					//���� �Ǵ� ���丮 ������ ���� File ��ü
					File file= new File(deleteFilePath);
					if(file.exists()) { //�ش��ο� �ش��̸��� ������ ����
						file.delete(); //�ش����� ����
					}
					
					
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
				
				boardDao.getBoard(num);
				
				//������ ��ȣ �Ķ���� ��������
				String pageNum=request.getParameter("pageNum");
				
				//���� ������
				ActionForward forward=new ActionForward();
				forward.setPath("boardList.do?pageNum="+pageNum);
				forward.setRedirect(true);
				return forward;
	}

}//BoardFileDeleteProcessAction

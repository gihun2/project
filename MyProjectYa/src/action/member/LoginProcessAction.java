package action.member;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

public class LoginProcessAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		
		ActionForward forward=null;
		
		//DB�۾� ��ü ����
		MemberDao memberDao=MemberDao.getInstance();
		int check=memberDao.loginCheck(id, pass);
		if(check!=1) { //�α��� ���н�
			String message="";
			if(check==-1) { //ID ����ġ
				message="�ش��ϴ� ID�� �����ϴ�...�Ф�";
			} else if(check==0) { //Password ����ġ
				message="��й�ȣ�� ��ġ���� �ʽ��ϴ�..�Ф�";
			}
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out=response.getWriter();
			out.println("<script>");
			out.println("alert('"+message+"');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
//		if(check==-1) { //ID ����ġ
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out=response.getWriter();
//			out.println("<script>");
//			out.println("alert('�ش��ϴ� ID�� �����ϴ�...�Ф�');");
//			out.println("history.back();");
//			out.println("</script>");
//			out.close();
//			return null;
//		} else if(check==0) { //Password ����ġ
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out=response.getWriter();
//			out.println("<script>");
//			out.println("alert('��й�ȣ�� ��ġ���� �ʽ��ϴ�..�Ф�');");
//			out.println("// location.href='loginForm.me'");
//			out.println("history.back();");
//			out.println("</script>");
//			// out.flush(); // ���ۺ���(��������)
//			out.close(); // close()�ÿ� flush()�� ���������� �����ϰ� �ڿ��� �ݴ´�.
//			return null; //����Ʈ ��Ʈ�ѷ��� 3�ܰ� �۾� �����ϵ��� null ����.
//		} 
		
		//��� ��ġ(�α��� ����)
		//���Ǳ��ϱ�
		HttpSession session=request.getSession();
		session.setAttribute("id", id);
		
		//��Ű �����
		Cookie cookies=new Cookie("Project_ID", id);
		cookies.setMaxAge(60*60);
		response.addCookie(cookies);
		
		// .me�� ������ ��ɾ� ����
		// -> ����Ʈ ��Ʈ�ѷ��� �ٽ� �Է��� ����
		// -> �����̷�Ʈ�� �������� ���û�ϵ��� ��.
		forward=new ActionForward();
		forward.setPath("main.do");  
		forward.setRedirect(true); // �����̷�Ʈ(������ ���û)
		return forward;
		
	}
	
}

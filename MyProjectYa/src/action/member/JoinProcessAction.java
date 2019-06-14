package action.member;

import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class JoinProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//ȸ������ ó��
		String id=request.getParameter("id");
		String pass=request.getParameter("pass");
		String name=request.getParameter("name");
		String strBirthday=request.getParameter("birthday");
		String gender=request.getParameter("gender");
		String email=request.getParameter("email");
		String address=request.getParameter("address");
		String phone=request.getParameter("phone");
		String mobile=request.getParameter("mobile");

		/* ���1:
		 (���ڿ� ��¥����
		 -> SimpleDateFormat�� �̿��� java.util.Date �� ��ȯ
		 -> java.util.date�� java.sql.Date�� ��ȯ)
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
		java.util.Date date=sdf.parse(strBirthday);
		java.sql.Date birthday=new java.sql.Date(date.getTime());
		*/
		// ���2 (���ڿ� ��¥����-> java.sql.Date�� ��ȯ)
		// java.sql.Date.valueOf() �޼ҵ�� "yyyy-mm-dd" �������� �Ľ���.
		// java.sql.Date birthday = java.sql.Date.valueOf(strBirthday);		
		
		Timestamp birthday = Timestamp.valueOf(strBirthday + " 00:00:00");
		
		Smember member = new Smember(id, pass, name, birthday, gender, email, address, phone, mobile);
		
		MemberDao memberDao=MemberDao.getInstance();
		memberDao.insert(member); //ȸ������ ó�� �Ϸ�
		
		
		
		//ȸ������ ó���Ϸ�Ǹ� Ŭ���̾�Ʈ�� �α��� �������� �̵����������
		/*
		ActionForward forward=new ActionForward();
		forward.setRedirect(true); //�����̷�Ʈ�� ��ɾ�����
		forward.setPath("loginForm.do");
		return forward;
		*/
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('ȸ�������� �Ϸ�Ǿ����ϴ�.');");
		out.println("location.href = 'loginForm.do';");
		out.println("</script>");
		out.flush();
		out.close();
		return null;
	}

}

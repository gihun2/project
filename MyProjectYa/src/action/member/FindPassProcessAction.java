package action.member;

import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.SimpleEmail;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class FindPassProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String id=request.getParameter("id");
		String name=request.getParameter("name");
		String strBirthday=request.getParameter("birthday");
		String gender=request.getParameter("gender");
		String email=request.getParameter("email");	
		
		Timestamp birthday = Timestamp.valueOf(strBirthday + " 00:00:00");	
		
		System.out.println("id ::"+id);
		System.out.println("name ::"+name);
		System.out.println("birthday ::"+birthday);
		System.out.println("gender ::"+gender);
		System.out.println("email ::"+email);
		
		MemberDao memberDao=MemberDao.getInstance();
		Smember member = memberDao.getMember(id);
		System.out.println("Member : " + member);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if((id.equals(member.getId()) && name.equals(member.getName()) &&
				birthday.equals(member.getBirthday()) && gender.equals(member.getGender()) &&
				email.equals(member.getEmail()))) {
			

			String subject="�н����� ã�� ����Դϴ�.";
			String content= id+"���� �н������"+member.getPassword()+"�Դϴ�.";

			long beginTime=System.currentTimeMillis();

			//SimpleEmail ��ü����
			SimpleEmail simpleEmail=new SimpleEmail();

			//SMTP ���� ���� ����
			simpleEmail.setHostName("smtp.daum.net");
			simpleEmail.setSmtpPort(465); // 465(SSL) or 587(TLS)
			simpleEmail.setAuthentication("tjrlgns.12", "aaaa1234");

			simpleEmail.setSSLOnConnect(true);
			simpleEmail.setStartTLSEnabled(true);

			//�����»�� ����
			simpleEmail.setFrom("tjrlgns.12@daum.net", "������", "utf-8");

			//�޴»�� ����
			simpleEmail.addTo(email, "�޴»���̸�", "utf-8");
			System.out.println("receiver : "+email);

			// �̸������� ����
			simpleEmail.setSubject(subject);
			// �̸��ϳ��� ����
			simpleEmail.setMsg(content);

			new Thread(() -> {
					try{ 
						simpleEmail.send(); 
						long execTime = System.currentTimeMillis() - beginTime;
						System.out.println("�̸��� ���ۿ� �ɸ��ð�: "+execTime);
					}catch(Exception e){}
			}).start();			
			
			out.println("<script>");
			out.println("alert('��й�ȣ�� ������ �̸��Ϸ� ���۵Ǿ����ϴ�.');");
			out.println("location.href = 'main.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return null;
		} else {
			out.println("<script>");
			out.println("alert('������ ��ġ���� �ʽ��ϴ�');");
			out.println("location.href = 'main.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return null;
		} //if() else
	}

}//FindPassProcessAction Ŭ����

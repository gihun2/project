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
			

			String subject="패스워드 찾기 결과입니다.";
			String content= id+"님의 패스워드는"+member.getPassword()+"입니다.";

			long beginTime=System.currentTimeMillis();

			//SimpleEmail 객체생성
			SimpleEmail simpleEmail=new SimpleEmail();

			//SMTP 서버 연결 설정
			simpleEmail.setHostName("smtp.daum.net");
			simpleEmail.setSmtpPort(465); // 465(SSL) or 587(TLS)
			simpleEmail.setAuthentication("tjrlgns.12", "aaaa1234");

			simpleEmail.setSSLOnConnect(true);
			simpleEmail.setStartTLSEnabled(true);

			//보내는사람 설정
			simpleEmail.setFrom("tjrlgns.12@daum.net", "관리자", "utf-8");

			//받는사람 설정
			simpleEmail.addTo(email, "받는사람이름", "utf-8");
			System.out.println("receiver : "+email);

			// 이메일제목 설정
			simpleEmail.setSubject(subject);
			// 이메일내용 설정
			simpleEmail.setMsg(content);

			new Thread(() -> {
					try{ 
						simpleEmail.send(); 
						long execTime = System.currentTimeMillis() - beginTime;
						System.out.println("이메일 전송에 걸린시간: "+execTime);
					}catch(Exception e){}
			}).start();			
			
			out.println("<script>");
			out.println("alert('비밀번호가 귀하의 이메일로 전송되었습니다.');");
			out.println("location.href = 'main.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return null;
		} else {
			out.println("<script>");
			out.println("alert('정보가 일치하지 않습니다');");
			out.println("location.href = 'main.do';");
			out.println("</script>");
			out.flush();
			out.close();
			return null;
		} //if() else
	}

}//FindPassProcessAction 클래스

package action;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import vo.ActionForward;

public class MailProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application=request.getServletContext();
		String realPath=application.getRealPath("/temp");
		int maxPostSize = 1024 * 1024 * 10;
		
		MultipartRequest multi
		=new MultipartRequest(request, 
							  realPath, 
							  maxPostSize, 
							  "utf-8", 
							  new DefaultFileRenamePolicy());
		//파라미터 가져오기
		String receiver=multi.getParameter("receiver");
		String subject=multi.getParameter("subject");
		String filename=multi.getFilesystemName("filename");
		String content=multi.getParameter("content");
		File file=null;
		if(filename!=null && filename.length()>0) {
			file=new File(realPath, filename);
			System.out.println("파일 경로 getCanonicalPath : "+file.getCanonicalPath()); //파일의 절대경로(더 정확히 가져온다)
		}
		
		System.out.println("receiver ::"+receiver);
		System.out.println("subject ::"+subject);
		System.out.println("filename ::"+filename);
		System.out.println("content ::"+content);
	
		// 첨부파일 객체 준비
		EmailAttachment attachment=new EmailAttachment();
		attachment.setPath(file.getCanonicalPath()); //realPath+"/"+filename
		attachment.setDescription("첨부파일 설명");
		attachment.setName(filename);

		
		
		response.setContentType("text/html; charset=UTF-8");
		
		long beginTime=System.currentTimeMillis();
		
		PrintWriter out = response.getWriter();
		
		//MultiPartEmail 객체생성
		MultiPartEmail multiPartEmail=new MultiPartEmail();
		
		//SMTP 서버 연결 설정
		multiPartEmail.setHostName("smtp.daum.net");
		multiPartEmail.setSmtpPort(465); // 465(SSL) or 587(TLS)
		multiPartEmail.setAuthentication("tjrlgns.12", "aaaa1234");

		multiPartEmail.setSSLOnConnect(true);
		multiPartEmail.setStartTLSEnabled(true);
		
		//보내는사람 설정
		multiPartEmail.setFrom("tjrlgns.12@daum.net", "관리자", "utf-8");

		//받는사람 설정
		multiPartEmail.addTo(receiver, "받는사람이름", "utf-8");
		System.out.println("receiver : "+receiver);

		// 이메일제목 설정
		multiPartEmail.setSubject(subject);
		// 이메일내용 설정
		multiPartEmail.setMsg(content);
		// 첨부파일 추가
		multiPartEmail.attach(attachment); //add로 동작
		
		new Thread(() -> {
				try{ 
					multiPartEmail.send(); 
					long execTime = System.currentTimeMillis() - beginTime;
					System.out.println("이메일 전송에 걸린시간: "+execTime);
				}catch(Exception e){}
		}).start();			
		
		out.println("<script>");
		out.println("alert('이메일 전송이 완료되었습니다.');");
		out.println("location.href = 'main.do';");
		out.println("</script>");
		out.flush();
		out.close();
		return null;
	} 

}//MailProcessAction()

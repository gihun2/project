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
		//�Ķ���� ��������
		String receiver=multi.getParameter("receiver");
		String subject=multi.getParameter("subject");
		String filename=multi.getFilesystemName("filename");
		String content=multi.getParameter("content");
		File file=null;
		if(filename!=null && filename.length()>0) {
			file=new File(realPath, filename);
			System.out.println("���� ��� getCanonicalPath : "+file.getCanonicalPath()); //������ ������(�� ��Ȯ�� �����´�)
		}
		
		System.out.println("receiver ::"+receiver);
		System.out.println("subject ::"+subject);
		System.out.println("filename ::"+filename);
		System.out.println("content ::"+content);
	
		// ÷������ ��ü �غ�
		EmailAttachment attachment=new EmailAttachment();
		attachment.setPath(file.getCanonicalPath()); //realPath+"/"+filename
		attachment.setDescription("÷������ ����");
		attachment.setName(filename);

		
		
		response.setContentType("text/html; charset=UTF-8");
		
		long beginTime=System.currentTimeMillis();
		
		PrintWriter out = response.getWriter();
		
		//MultiPartEmail ��ü����
		MultiPartEmail multiPartEmail=new MultiPartEmail();
		
		//SMTP ���� ���� ����
		multiPartEmail.setHostName("smtp.daum.net");
		multiPartEmail.setSmtpPort(465); // 465(SSL) or 587(TLS)
		multiPartEmail.setAuthentication("tjrlgns.12", "aaaa1234");

		multiPartEmail.setSSLOnConnect(true);
		multiPartEmail.setStartTLSEnabled(true);
		
		//�����»�� ����
		multiPartEmail.setFrom("tjrlgns.12@daum.net", "������", "utf-8");

		//�޴»�� ����
		multiPartEmail.addTo(receiver, "�޴»���̸�", "utf-8");
		System.out.println("receiver : "+receiver);

		// �̸������� ����
		multiPartEmail.setSubject(subject);
		// �̸��ϳ��� ����
		multiPartEmail.setMsg(content);
		// ÷������ �߰�
		multiPartEmail.attach(attachment); //add�� ����
		
		new Thread(() -> {
				try{ 
					multiPartEmail.send(); 
					long execTime = System.currentTimeMillis() - beginTime;
					System.out.println("�̸��� ���ۿ� �ɸ��ð�: "+execTime);
				}catch(Exception e){}
		}).start();			
		
		out.println("<script>");
		out.println("alert('�̸��� ������ �Ϸ�Ǿ����ϴ�.');");
		out.println("location.href = 'main.do';");
		out.println("</script>");
		out.flush();
		out.close();
		return null;
	} 

}//MailProcessAction()

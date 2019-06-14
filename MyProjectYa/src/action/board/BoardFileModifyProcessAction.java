package action.board;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileModifyProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardFileModifyProcessAction");
		
		// (1) ���ε� �۾�
				// 1 request
				
				// 2 upload ���� ����� ������ ���
				ServletContext application=request.getServletContext();
				String realPath=application.getRealPath("/upload");
				System.out.println("realPath::"+realPath);
				
				// 3 ���Ͼ��ε� �ִ�ũ�� ���Ѱ�
				int maxPostSize = 1024 * 1024 * 10; //1024byte * 1024 * 10; (���ε� 10MB ����)
				
				// 4 �ѱ�ó�� �����ڵ�� ���� "utf-8"
				
				// 5 ���ε��ϴ� ���ϸ��� ���� ���ε�� ���ϸ�� ���� ��� => ���ϸ� �ڵ����� ��å
				
				// ���Ͼ��ε� ó�� �Ϸ�!
				MultipartRequest multi
					=new MultipartRequest(request, 
										  realPath, 
										  maxPostSize, 
										  "utf-8", 
										  new DefaultFileRenamePolicy());		
			// =======================���ε� �Ϸ� =======================================
				int num=Integer.parseInt(multi.getParameter("num"));
				String pass = multi.getParameter("pass");
				
				//DB��ü �غ�
				BoardDao boardDao=BoardDao.getInstance();
				boolean result=boardDao.checkBoard(num, pass);
				if(result==false) { //���н����� ����ġ��	
					if(multi.getFilesystemName("newFilename") != null) {// ���� ���ε� ������
						String filename= multi.getFilesystemName("newFilename"); // �����̸� ��������
						String deleteFilePath= realPath+"/"+filename; // ���ε���+�����̸�
						File file=new File(deleteFilePath); 
						if(file.exists()) { // �ش���ġ�� ������ �����ϸ�
							file.delete(); // ���� ����
						}
					}				
					
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out=response.getWriter();
					out.println("<script>");
					out.println("alert('�� ��й�ȣ Ʋ��.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return null;
				}			
				
		// num name pass subject content �Ķ���� ��������
				Sboard board=new Sboard();
				board.setNum(Integer.parseInt(multi.getParameter("num")));
				board.setName(multi.getParameter("name"));
				board.setPass(multi.getParameter("pass"));
				board.setSubject(multi.getParameter("subject"));
				board.setContent(multi.getParameter("content"));
				
				//����ó�� �ڡ�
				if(multi.getFilesystemName("newFilename") != null) {
					// ���Ӱ� ������ ���� ����
					
					// ���� ���ε����� ����
					String oldFilename= multi.getParameter("oldFilename"); // �����̸� ��������
					String deleteFilePath= realPath+"/"+oldFilename; // ���ε���+�����̸�
					File file=new File(deleteFilePath); 
					if(file.exists()) { // �ش���ġ�� ������ �����ϸ�
						file.delete(); // ���� ����
					}
					//�ű� ���ε� �����̸����� ����
					board.setFilename(multi.getFilesystemName("newFilename"));
				} else {
					//���� ���� ���� -> ���� �����̸����� ����
					board.setFilename(multi.getParameter("oldFilename"));
				}
				
				// �ۼ��� DBó��
				boardDao.updateBoard(board);
				
				//��������ȣ �Ķ���� ��������
				String pageNum=multi.getParameter("pageNum");
				
				//�ۼ��� ������
				//������ ���� �ִ� �������� �۸�� ��û
				ActionForward forward=new ActionForward();
				forward.setPath("boardFileList.do?pageNum=" + pageNum);
				forward.setRedirect(true);
				return forward;
		
	}

}//BoardFileModifyProcessAction

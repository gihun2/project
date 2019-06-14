package action.board;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileWriteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		// (2) DB�۾�
		// �Ķ���� �� �����ͼ� Board ��ü�� �����
		// ���ε带 ���� �⺻request�� MultipartRequest�� ��ȯ�ߴ�.
		// -> �Ķ���� �����ö� ��ȯ�� MultipartRequest�� �����;� �Ѵ�.
		Sboard board = new Sboard();
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		
		String originalFileName=multi.getOriginalFileName("filename");
		System.out.println("���� ���ϸ�: "+originalFileName);
		
		String realFilename=multi.getFilesystemName("filename");
		System.out.println("���� ���ϸ�: "+realFilename);
		
		board.setFilename(realFilename); //���� ���ϸ����� ����
		
		//���ۼ��� IP�ּ� �� ����
		board.setIp(request.getRemoteAddr());
		
		//DB�۾� ��ü �غ� BoardDao
		BoardDao boardDao=BoardDao.getInstance();
		//�޼ҵ� ȣ�� insert(board) ���ۼ� �Ϸ�
		boardDao.insert(board);
		//�۸��(boardList.do)ȭ������ �̵�. �����̷�Ʈ
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do");
		forward.setRedirect(true);
		return forward;

	}

}//BoardFileWriteProcessAction

package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �۹�ȣ �Ķ���� ��������
		int num = Integer.parseInt(request.getParameter("num"));

		// DB��ü �غ�
		BoardDao boardDao = BoardDao.getInstance();
		// ��ȸ�� 1����
		boardDao.updateReadcount(num);
		// �۹�ȣ�� �ش��ϴ� �� ��ü(��)���� ��������
		Sboard board = boardDao.getBoard(num);
		
		// �� ���� �ٹٲ� ó�� �ϴ� ���
		//1) <pre>tag ���
		//2) \r\n ��ɾ <br>tag�� ġȯ
		String content="";
		if(board.getContent() != null){
			content=board.getContent().replace("\r\n", "<br>");
			board.setContent(content);
		}
		
		
		String filename=board.getFilename();
		String ext ="";
		if(filename!=null) {
			int index = filename.lastIndexOf('.');
			ext = filename.substring(index+1); //�� ��ġ �������� ��������	
		}	
		
		request.setAttribute("board", board); // �۹�ȣ �ش��ϴ� �۳���
		request.setAttribute("ext", ext); //���ϸ� Ȯ���� ����
		
		ActionForward forward = new ActionForward();
		forward.setPath("center/fileContent");
		forward.setRedirect(false);
		return forward;
	}

}// BoardFileDetailAction

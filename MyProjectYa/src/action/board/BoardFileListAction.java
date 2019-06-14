package action.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// ������ ��ȣ �Ķ���� ��������
		/*
		�� ������ �� ���ڵ�(��) 3�Ǿ� ��������.
		������ ��ȣ		�������ȣ(0���� ����)
		1			->			0	
		2			->			3 
		3			->			6 
		4			->			9
		�������ȣ=(��������ȣ-1)*3 
		 */
		
		//��������ȣ �Ķ���� ��������
		String strPageNum=request.getParameter("pageNum"); //��������ȣ
		if(strPageNum==null) {
			strPageNum="1"; //������ �⺻�� 1�� ����.
		}
		int pageNum=Integer.parseInt(strPageNum); //��������ȣ�� ������ ��ȯ
		
		//�˻��� �Ķ���� ��������
		//�˻� ��û�� ���ϸ�(�˻� ��ư�� �ȴ�������) null �� ����
		String search=request.getParameter("search");
		if(search==null) {
			search="";
		}
		
		// =========================================================
		// �� �������� �ش��ϴ� �۸�� ���ϱ� �۾�
		// =========================================================	
		int pageSize=10; //�� ������ �� ������ ��(���ڵ�) ���� ����ȭ
		int startRow=(pageNum-1)*pageSize; //�������� ������ ��ȣ
		
		// DB��ü �غ�
		BoardDao boardDao=BoardDao.getInstance();
		
		// getBoards() ȣ���ؼ� �۸�� �����͸� list�� ��������
		List<Sboard> list=boardDao.getBoards(startRow, pageSize, search);
		
		// =========================================================
		// ������ ��� ���ϱ� �۾�
		// =========================================================
		int allRowCount=0; //��ü �� ����
		if(search == null || search.equals("")) {
			allRowCount=boardDao.getBoardCount();
		}else {
			allRowCount=boardDao.getBoardCount(search);
		}
		
		
		int maxPage= allRowCount / pageSize +(allRowCount % pageSize ==0 ? 0 : 1);
//		if(allRowCount % pageSize > 0) {
//		maxPage++;
//		}
		//1������~maxPage���������� ������ -> ������ ��ϴ����� �����ش�.
		
		//������������ȣ(1)							����������ȣ(10)
		//1 2 3 4 5 6 7 8 9 10						--[��ϱ��� 10��]
		//������������ȣ(11)						����������ȣ(20)
		//11 12 13 14 15 16 17 18 19 20				--[��ϱ��� 10��]
		
		//�� ����������� �����ϴ� ����������
		int pageBlockSize=5;
		
		//������������ȣ ���ϱ�
		int startPage = ((pageNum/pageBlockSize)-(pageNum%pageBlockSize==0 ? 1 : 0))*pageBlockSize+1;
		//����������ȣ ���ϱ�
		int endPage = startPage+pageBlockSize-1;
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		
		Map<String, Integer> pageInfoMap= new HashMap<>();
		pageInfoMap.put("startPage", startPage); //������������ȣ
		pageInfoMap.put("endPage", endPage);	//����������ȣ
		pageInfoMap.put("pageBlockSize", pageBlockSize); //������ ��� ũ��
		pageInfoMap.put("maxPage", maxPage); //��ü ��������
		pageInfoMap.put("allRowCount", allRowCount); //��ü �హ��
		pageInfoMap.put("pageNum", pageNum); //����ڰ� ��û�� ��������ȣ
		
		// request ������ü�� ����
		request.setAttribute("list", list); //�۸�� list
		request.setAttribute("pageInfoMap", pageInfoMap); //��������� ��°��� ������
		
		request.setAttribute("search", search); //�˻���
		
		ActionForward forward=new ActionForward();
		forward.setPath("center/fileNotice");
		forward.setRedirect(false);
		return forward;
	}

}//BoardFileListAction

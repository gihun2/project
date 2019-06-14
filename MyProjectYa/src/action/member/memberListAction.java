package action.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class memberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// �����ڸ� �����ϴ� �׼�.
		// ���� ���ϱ�
		HttpSession session = request.getSession();
		// ���� �α��� ���� ���ƴ��� Ȯ��
		String id = (String) session.getAttribute("id");
		
		ActionForward forward=null;
		if(!"admin".equals(id)) { // = if(id==null || !id.equals("admin"))
			forward=new ActionForward("main.do",true);
			return forward;
		}
		
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
		
		// DB��ü �غ� (�����ڿ�)
		MemberDao memberDao=MemberDao.getInstance();
		//�۹�ȣ�� �ش��ϴ� �� ��ü���� ��������
		Smember member=memberDao.getMember(id);
		
		request.setAttribute("member", member); //�۹�ȣ �ش��ϴ� �۳���
		// getAllMembers() ȣ���ؼ� ȸ����� �����͸� list�� ��������
		List<Smember> list=memberDao.getAllMembers(startRow, pageSize, search);
		
		// =========================================================
		// ������ ��� ���ϱ� �۾�
		// =========================================================
		int allRowCount=0; //��ü �� ����
		if(search == null || search.equals("")) {
			allRowCount=memberDao.getMemberCount();
		}else {
			allRowCount=memberDao.getMemberCount(search);
		}
		
		int maxPage= allRowCount / pageSize +(allRowCount % pageSize ==0 ? 0 : 1);
		
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
		
		//request ������ü�� ������
		//����ġ�� �̵��� jsp���� ��Ƽ� ����.
		request.setAttribute("list", list);
		request.setAttribute("pageInfoMap", pageInfoMap); //��������� ��°��� ������
		System.out.println("pageInfoMap : " + pageInfoMap);
		request.setAttribute("search", search); //�˻���
		
		forward=new ActionForward();
		forward.setPath("member/member_list");
		forward.setRedirect(false);
		return forward;
	}

}

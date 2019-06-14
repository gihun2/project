package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

public class joinIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ID �ߺ��˻��� userid �Ķ���� ��������
				String userid=request.getParameter("userid");
				
				MemberDao memberDao=MemberDao.getInstance();
				int count=memberDao.countById(userid);
//				int check==1 ���̵� ����. "������� ID�Դϴ�. "
//				int check==0 ���̵� ����. "��밡���� ID�Դϴ�."
				
				boolean isDuplicated=false;
				if(count>0) {
					isDuplicated=true;
				}
				//ID�ߺ�Ȯ�� ������� request ��ü�� �����Ѵ�.
				request.setAttribute("isDuplicated", isDuplicated);
				
				ActionForward forward=new ActionForward();
				forward.setPath("member/joinIdCheck");
				forward.setRedirect(false);
				return forward;
	}

}

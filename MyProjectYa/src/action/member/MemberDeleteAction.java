package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

// ȸ������ �۾��� ó���ϴ� Action Ŭ����

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
ActionForward forward = new ActionForward();
		
		// ������ �������ִ� �α����� ID ������ �����´�
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		String password = request.getParameter("password");
		
		MemberDao dao = MemberDao.getInstance();
		int check = dao.deleteMember(id, password);
		
		if(check == 1){
			// ������ ȸ������ ����
			session.removeAttribute("sessionID");
			forward.setRedirect(true);
			forward.setNextPath("ResultForm.do");
		}
		else{
			System.out.println("ȸ�� ���� ����");
			return null;
		}
		
		return forward;
	}

}

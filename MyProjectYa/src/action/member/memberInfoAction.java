package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;


// ���� �α����� ������� ȸ�������� �����ִ� Action Ŭ����


public class memberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception {		
		
		String paramID = request.getParameter("id");
		// �� ���̵� �ش��ϴ� ȸ�������� �����´�.
		MemberDao dao = MemberDao.getInstance();
		Smember member = dao.getUserInfo(paramID);
		
		//request ������ü�� ����
		request.setAttribute("member", member);
		
		// ModifyForm.jsp�� �̵�
		ActionForward forward = new ActionForward();
		forward.setPath("member/ModifyForm");
		forward.setRedirect(false); //����ġ ��� �̵�
		return forward;
	}

}//memberInfoAction Ŭ����

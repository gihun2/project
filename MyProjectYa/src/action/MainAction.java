package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

// Action�� ��Ʈ�ѷ� �����̴�.
public class MainAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	ActionForward forward = new ActionForward();
	forward.setPath("main");
	forward.setRedirect(false);
	
	return forward;
	}

}

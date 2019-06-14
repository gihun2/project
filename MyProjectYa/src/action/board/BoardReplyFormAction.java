package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//���� �׼ǿ��� �Ķ���� �������� �۾� ���ʿ�
		//�Ķ���� ������ �並 ����µ��� ���Ǳ� ����
		ActionForward forward = new ActionForward();
		forward.setPath("center/replyWrite");
		forward.setRedirect(false);
		return forward;
	}

}//boardReplyFormAction()

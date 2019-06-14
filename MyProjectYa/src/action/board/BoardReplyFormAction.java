package action.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import vo.ActionForward;

public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//현재 액션에서 파라미터 가져오기 작업 불필요
		//파라미터 정보는 뷰를 만드는데만 사용되기 때문
		ActionForward forward = new ActionForward();
		forward.setPath("center/replyWrite");
		forward.setRedirect(false);
		return forward;
	}

}//boardReplyFormAction()

package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;


// 현재 로그인한 사용자의 회원정보를 보여주는 Action 클래스


public class memberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception {		
		
		String paramID = request.getParameter("id");
		// 그 아이디 해당하는 회원정보를 가져온다.
		MemberDao dao = MemberDao.getInstance();
		Smember member = dao.getUserInfo(paramID);
		
		//request 영역객체에 저장
		request.setAttribute("member", member);
		
		// ModifyForm.jsp로 이동
		ActionForward forward = new ActionForward();
		forward.setPath("member/ModifyForm");
		forward.setRedirect(false); //디스패치 방식 이동
		return forward;
	}

}//memberInfoAction 클래스

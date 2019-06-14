package action.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

public class joinIdCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ID 중복검사할 userid 파라미터 가져오기
				String userid=request.getParameter("userid");
				
				MemberDao memberDao=MemberDao.getInstance();
				int count=memberDao.countById(userid);
//				int check==1 아이디 있음. "사용중인 ID입니다. "
//				int check==0 아이디 없음. "사용가능한 ID입니다."
				
				boolean isDuplicated=false;
				if(count>0) {
					isDuplicated=true;
				}
				//ID중복확인 결과값을 request 객체에 저장한다.
				request.setAttribute("isDuplicated", isDuplicated);
				
				ActionForward forward=new ActionForward();
				forward.setPath("member/joinIdCheck");
				forward.setRedirect(false);
				return forward;
	}

}

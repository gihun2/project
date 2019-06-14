package action.member;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class memberModifyProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("memberModifyProcessAction");
		
		Smember member=new Smember();
		member.setId(request.getParameter("id"));
		member.setPassword(request.getParameter("password"));
		member.setName(request.getParameter("name"));
		member.setBirthday(Timestamp.valueOf(request.getParameter("birthday") + " 00:00:00"));
		member.setGender(request.getParameter("gender"));
		member.setEmail(request.getParameter("Email"));
		member.setAddress(request.getParameter("address"));
		member.setTel(request.getParameter("tel"));
		member.setMtel(request.getParameter("Mtel"));
		
		//DB객체 준비
		MemberDao memberDao=MemberDao.getInstance();
		
		//메소드 호출 updateMember(member)
		boolean isSuccess=memberDao.updateMember(member);
		
		ActionForward forward=new ActionForward();
		forward.setPath("memberList.do");
		forward.setRedirect(true);
		return forward;
	}

}

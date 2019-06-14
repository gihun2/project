package action.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;


public class memberChoiceDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] id = request.getParameterValues("choiceDelete");
			System.out.println("String[] id : " + id);
	      for (String str : id) {
	         System.out.println("forEach :  " + str);
	      }

	      MemberDao memberDao = MemberDao.getInstance();
	      int num = memberDao.deleteChoiceMember(id);

//	      response.setContentType("application/json; charset=UTF-8");
//	      PrintWriter out = response.getWriter();
//
//	      out.println(num);
//	      System.out.println("deleteChoiceMember Result : " + num);
//	      out.close();
	      
	      ActionForward forward=new ActionForward();
	      forward.setPath("memberList.do");
	      forward.setRedirect(true);
	      return forward;
	   }
	}

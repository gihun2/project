package action.member;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;

public class joinIdCheckJsonAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ID 중복검사할 userid 파라미터 가져오기
				String userid=request.getParameter("userid");
				
				MemberDao memberDao=MemberDao.getInstance();
				int count=memberDao.countById(userid);
				System.out.println("Userid, count : " + userid + count);
				
				boolean isDuplicated=false;
				if(count>0) {
					isDuplicated=true;
				}
				
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter out=response.getWriter();
				
				// 1) JSON List컬렉션
//				out.println("["+isDuplicated+","+10+"]");
				
//				JSONArray jsonArray=new JSONArray();
//				jsonArray.add(isDuplicated); //인덱스 0
//				jsonArray.add(10); //인덱스 1
//				jsonArray.add("문자열"); //인덱스 2
//				out.println(jsonArray);
//				System.out.println(jsonArray);
				
				// 2) JSON Map컬렉션 
//				JSONObject jsonObject=new JSONObject();
//				jsonObject.put("isDup", isDuplicated);
//				jsonObject.put("number", 10); 
//				out.println(jsonObject);
//				System.out.println(jsonObject);
				
				// 3)자바스크립트 기본타입으로 전송할때
				out.println(isDuplicated);
				System.out.println(isDuplicated);
				
				out.close();
				return null;
	}

}

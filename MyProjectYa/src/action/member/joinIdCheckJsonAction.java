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
		// ID �ߺ��˻��� userid �Ķ���� ��������
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
				
				// 1) JSON List�÷���
//				out.println("["+isDuplicated+","+10+"]");
				
//				JSONArray jsonArray=new JSONArray();
//				jsonArray.add(isDuplicated); //�ε��� 0
//				jsonArray.add(10); //�ε��� 1
//				jsonArray.add("���ڿ�"); //�ε��� 2
//				out.println(jsonArray);
//				System.out.println(jsonArray);
				
				// 2) JSON Map�÷��� 
//				JSONObject jsonObject=new JSONObject();
//				jsonObject.put("isDup", isDuplicated);
//				jsonObject.put("number", 10); 
//				out.println(jsonObject);
//				System.out.println(jsonObject);
				
				// 3)�ڹٽ�ũ��Ʈ �⺻Ÿ������ �����Ҷ�
				out.println(isDuplicated);
				System.out.println(isDuplicated);
				
				out.close();
				return null;
	}

}

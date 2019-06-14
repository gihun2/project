package action.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import action.Action;
import dao.MemberDao;
import vo.ActionForward;
import vo.Smember;

public class memberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 관리자만 접근하는 액션.
		// 세션 구하기
		HttpSession session = request.getSession();
		// 정상 로그인 과정 거쳤는지 확인
		String id = (String) session.getAttribute("id");
		
		ActionForward forward=null;
		if(!"admin".equals(id)) { // = if(id==null || !id.equals("admin"))
			forward=new ActionForward("main.do",true);
			return forward;
		}
		
		//페이지번호 파라미터 가져오기
		String strPageNum=request.getParameter("pageNum"); //페이지번호
		if(strPageNum==null) {
			strPageNum="1"; //페이지 기본값 1로 설정.
		}
		int pageNum=Integer.parseInt(strPageNum); //페이지번호를 정수로 변환
		
		//검색어 파라미터 가져오기
		//검색 요청을 안하면(검색 버튼을 안눌렀을시) null 값 리턴
		String search=request.getParameter("search");
		if(search==null) {
			search="";
		}		
		
		// =========================================================
		// 한 페이지에 해당하는 글목록 구하기 작업
		// =========================================================	
		int pageSize=10; //한 페이지 당 보여줄 글(레코드) 개수 변수화
		int startRow=(pageNum-1)*pageSize; //페이지의 시작행 번호
		
		// DB객체 준비 (관리자용)
		MemberDao memberDao=MemberDao.getInstance();
		//글번호에 해당하는 글 전체내용 가져오기
		Smember member=memberDao.getMember(id);
		
		request.setAttribute("member", member); //글번호 해당하는 글내용
		// getAllMembers() 호출해서 회원목록 데이터를 list로 가져오기
		List<Smember> list=memberDao.getAllMembers(startRow, pageSize, search);
		
		// =========================================================
		// 페이지 블록 구하기 작업
		// =========================================================
		int allRowCount=0; //전체 행 갯수
		if(search == null || search.equals("")) {
			allRowCount=memberDao.getMemberCount();
		}else {
			allRowCount=memberDao.getMemberCount(search);
		}
		
		int maxPage= allRowCount / pageSize +(allRowCount % pageSize ==0 ? 0 : 1);
		
		//한 페이지블록을 구성하는 페이지갯수
		int pageBlockSize=5;
				
		//시작페이지번호 구하기
		int startPage = ((pageNum/pageBlockSize)-(pageNum%pageBlockSize==0 ? 1 : 0))*pageBlockSize+1;
		//끝페이지번호 구하기
		int endPage = startPage+pageBlockSize-1;
		if(endPage>maxPage) {
			endPage=maxPage;
		}
		
		Map<String, Integer> pageInfoMap= new HashMap<>();
		pageInfoMap.put("startPage", startPage); //시작페이지번호
		pageInfoMap.put("endPage", endPage);	//끝페이지번호
		pageInfoMap.put("pageBlockSize", pageBlockSize); //페이지 블록 크기
		pageInfoMap.put("maxPage", maxPage); //전체 페이지수
		pageInfoMap.put("allRowCount", allRowCount); //전체 행갯수
		pageInfoMap.put("pageNum", pageNum); //사용자가 요청한 페이지번호
		
		//request 영역객체에 싣으면
		//디스패치로 이동한 jsp까지 살아서 간다.
		request.setAttribute("list", list);
		request.setAttribute("pageInfoMap", pageInfoMap); //페이지블록 출력관련 데이터
		System.out.println("pageInfoMap : " + pageInfoMap);
		request.setAttribute("search", search); //검색어
		
		forward=new ActionForward();
		forward.setPath("member/member_list");
		forward.setRedirect(false);
		return forward;
	}

}

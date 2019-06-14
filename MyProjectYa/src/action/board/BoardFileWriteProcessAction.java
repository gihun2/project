package action.board;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileWriteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// (1) 업로드 작업
		// 1 request
		
		// 2 upload 폴더 만들고 물리적 경로
		ServletContext application=request.getServletContext();
		String realPath=application.getRealPath("/upload");
		System.out.println("realPath::"+realPath);
		
		// 3 파일업로드 최대크기 제한값
		int maxPostSize = 1024 * 1024 * 10; //1024byte * 1024 * 10; (업로드 10MB 제한)
		
		// 4 한글처리 유니코드로 설정 "utf-8"
		
		// 5 업로드하는 파일명이 기존 업로드된 파일명과 같은 경우 => 파일명 자동변경 정책
		
		// 파일업로드 처리 완료!
		MultipartRequest multi
		=new MultipartRequest(request, 
							  realPath, 
							  maxPostSize, 
							  "utf-8", 
							  new DefaultFileRenamePolicy());				
		
		// (2) DB작업
		// 파라미터 값 가져와서 Board 객체로 만들기
		// 업로드를 위해 기본request를 MultipartRequest로 변환했다.
		// -> 파라미터 가져올때 변환된 MultipartRequest로 가져와야 한다.
		Sboard board = new Sboard();
		board.setName(multi.getParameter("name"));
		board.setPass(multi.getParameter("pass"));
		board.setSubject(multi.getParameter("subject"));
		board.setContent(multi.getParameter("content"));
		
		String originalFileName=multi.getOriginalFileName("filename");
		System.out.println("원본 파일명: "+originalFileName);
		
		String realFilename=multi.getFilesystemName("filename");
		System.out.println("실제 파일명: "+realFilename);
		
		board.setFilename(realFilename); //실제 파일명으로 저장
		
		//글작성자 IP주소 값 저장
		board.setIp(request.getRemoteAddr());
		
		//DB작업 객체 준비 BoardDao
		BoardDao boardDao=BoardDao.getInstance();
		//메소드 호출 insert(board) 글작성 완료
		boardDao.insert(board);
		//글목록(boardList.do)화면으로 이동. 리다이렉트
		ActionForward forward=new ActionForward();
		forward.setPath("boardList.do");
		forward.setRedirect(true);
		return forward;

	}

}//BoardFileWriteProcessAction

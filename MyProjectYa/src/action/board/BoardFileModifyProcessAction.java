package action.board;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileModifyProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardFileModifyProcessAction");
		
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
			// =======================업로드 완료 =======================================
				int num=Integer.parseInt(multi.getParameter("num"));
				String pass = multi.getParameter("pass");
				
				//DB객체 준비
				BoardDao boardDao=BoardDao.getInstance();
				boolean result=boardDao.checkBoard(num, pass);
				if(result==false) { //글패스워드 불일치시	
					if(multi.getFilesystemName("newFilename") != null) {// 파일 업로드 했으면
						String filename= multi.getFilesystemName("newFilename"); // 파일이름 가져오기
						String deleteFilePath= realPath+"/"+filename; // 업로드경로+파일이름
						File file=new File(deleteFilePath); 
						if(file.exists()) { // 해당위치에 파일이 존재하면
							file.delete(); // 파일 삭제
						}
					}				
					
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out=response.getWriter();
					out.println("<script>");
					out.println("alert('글 비밀번호 틀륌.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return null;
				}			
				
		// num name pass subject content 파라미터 가져오기
				Sboard board=new Sboard();
				board.setNum(Integer.parseInt(multi.getParameter("num")));
				board.setName(multi.getParameter("name"));
				board.setPass(multi.getParameter("pass"));
				board.setSubject(multi.getParameter("subject"));
				board.setContent(multi.getParameter("content"));
				
				//파일처리 ★★
				if(multi.getFilesystemName("newFilename") != null) {
					// 새롭게 수정할 파일 있음
					
					// 기존 업로드파일 삭제
					String oldFilename= multi.getParameter("oldFilename"); // 파일이름 가져오기
					String deleteFilePath= realPath+"/"+oldFilename; // 업로드경로+파일이름
					File file=new File(deleteFilePath); 
					if(file.exists()) { // 해당위치에 파일이 존재하면
						file.delete(); // 파일 삭제
					}
					//신규 업로드 파일이름으로 설정
					board.setFilename(multi.getFilesystemName("newFilename"));
				} else {
					//파일 수정 안함 -> 기존 파일이름으로 저장
					board.setFilename(multi.getParameter("oldFilename"));
				}
				
				// 글수정 DB처리
				boardDao.updateBoard(board);
				
				//페이지번호 파라미터 가져오기
				String pageNum=multi.getParameter("pageNum");
				
				//글수정 성공시
				//수정한 글이 있는 페이지로 글목록 요청
				ActionForward forward=new ActionForward();
				forward.setPath("boardFileList.do?pageNum=" + pageNum);
				forward.setRedirect(true);
				return forward;
		
	}

}//BoardFileModifyProcessAction

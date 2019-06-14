package action.board;

import java.io.File;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import dao.BoardDao;
import vo.ActionForward;
import vo.Sboard;

public class BoardFileDeleteProcessAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// int num, String pass 파라미터 가져오기 
				int num=Integer.parseInt(request.getParameter("num"));
				String pass=request.getParameter("pass");
				
				//DB객체 준비
				BoardDao boardDao = BoardDao.getInstance();
				
				//메소드 호출 boolean checkBoard(num, pass)
				boolean result=boardDao.checkBoard(num, pass);
				// (글패스워드가 달라서)삭제 실패시
				if(!result) { 
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out=response.getWriter();
					out.println("<script>");
					out.println("alert('글 패스워드가 틀륍니다...');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return null;
				}
				// 글패스워드 일치하면 파일이름 가져와서 파일삭제 처리
					Sboard board=boardDao.getBoard(num);
					String filename=board.getFilename();
				
					//application 영역객체 가져오기
					ServletContext application=request.getServletContext();
					String realPath=application.getRealPath("/upload");
					String deleteFilePath=realPath+"/"+filename;
					System.out.println("삭제할 파일경로:: "+deleteFilePath);
					
					//파일 또는 디렉토리 정보를 가진 File 객체
					File file= new File(deleteFilePath);
					if(file.exists()) { //해당경로에 해당이름의 파일이 존재
						file.delete(); //해당파일 삭제
					}
					
					
				//메소드호출 deleteBoard(num, pass)
				boolean isSuccess=boardDao.deleteBoard(num, pass);
				// (글패스워드가 달라서)삭제 실패시
				if(!isSuccess) { 
					response.setContentType("text/html; charset=UTF-8");
					PrintWriter out=response.getWriter();
					out.println("<script>");
					out.println("alert('글 패스워드가 틀륍니다...');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
					return null;
				}
				
				boardDao.getBoard(num);
				
				//페이지 번호 파라미터 가져오기
				String pageNum=request.getParameter("pageNum");
				
				//삭제 성공시
				ActionForward forward=new ActionForward();
				forward.setPath("boardList.do?pageNum="+pageNum);
				forward.setRedirect(true);
				return forward;
	}

}//BoardFileDeleteProcessAction

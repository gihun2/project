package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.ActionFactory;
import vo.ActionForward;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// http://localhost:80/funweb-mvc/main.do
		System.out.println("요청받음");
		
		//1) 사용자 요청정보(명령commend) 확인
		String requestURI=request.getRequestURI();
		//-> URI주소: /funweb-mvc/main.do
		
		String contextPath=request.getContextPath();
		//-> contextPath 까지가 주소에서 프로젝트이름까지이다.
		//-> /funweb-mvc
		
		String command=requestURI.substring(contextPath.length()+1);
		//-> /main.do
		//-> contextPath.length로 받으면 가장 뒤의 주소값만 가져온다.
		
		int index=command.lastIndexOf(".");
		command=command.substring(0, index);
		//->.의 위치를 찾아서 substring으로 다시 저장시키면 주소값만 저장할 수 있다.
		//-> main
		
		//2) 확인된 요청(명령어)을 처리하고 읍답할 뷰(jsp)를 선택함 
		//   또는 새로운 요청명령어를 선택함.
		
		// -브라우저가 요청할 명령어 : sendRedirect
		// -서버가 실행할 뷰(jsp) : requestDispatching
		
		// 모델2에서 JSP를 사용하는 용도로
		// sendRedirect 대신 requestDispatching 방식 사용한다.
		
		Action action=null;
		ActionForward forward=null;
		
		ActionFactory factory=ActionFactory.getInstance();
		action=factory.getAction(command);
		if(action!=null) {
			try {
				forward=action.execute(request, response);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		//모듈2 는 실제 있는 jsp를 가상의 공간을 만들어서 가상의 주소를 찾으면 나오게 만드는 것이다.
		
		//3) 선택된 뷰(JSP)를 실행
		// 이동작업
		if(forward!=null) {
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				String path="WEB-INF/views/"+forward.getPath()+".jsp";
				RequestDispatcher dispatcher=request.getRequestDispatcher(path);
				dispatcher.forward(request, response); //핵심!
			}
		}//if
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//한글처리 먼저하고 doGet 호출,실행되어야 문자가 이상한걸로 안뜬다.
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}

}

package action;

import java.util.HashMap;
import java.util.Map;

import action.member.*;
import action.board.*;

public class ActionFactory {
	
	private static ActionFactory factory=new ActionFactory();
	
	public static ActionFactory getInstance() {
		return factory;
	}


	private Map<String, Action> map;
	//싱글톤에서 생성자는 private 접근제한자 적용
	private ActionFactory() {
		map=new HashMap<>();
		//회원관리 명령어 처리
		map.put("main", new MainAction());
		map.put("welcome", new welcomeAction());
		map.put("joinForm", new joinFormAction());		
		map.put("joinProcess", new JoinProcessAction());
		map.put("joinIdCheck", new joinIdCheckAction());
		map.put("loginForm", new LoginFormAction());
		map.put("loginProcess", new LoginProcessAction());
		map.put("logout", new LogoutAction());
		map.put("joinIdCheckJson", new joinIdCheckJsonAction());
		
		//관리자용
		map.put("mail", new MailFormAction());
		map.put("mailProcess", new MailProcessAction());
		map.put("columnChart", new ChartAction());
		map.put("memberInfo", new memberInfoAction());
		map.put("memberList", new memberListAction());
		map.put("memberChoiceDeleteForm", new memberChoiceDeleteFormAction());
		map.put("memberChoiceDelete", new memberChoiceDeleteAction());
		map.put("findPassForm", new FindPassFormAction());
		map.put("findPassProcess", new FindPassProcessAction());
		map.put("memberModifyProcess", new memberModifyProcessAction());
		map.put("boardAllShowForm", new BoardAllShowFormAction());
		map.put("boardAdminDeleteForm", new BoardAdminDeleteFormAction());
		map.put("boardAdminDeleteProcess", new BoardAdminDeleteProcessAction());
		
		//일반 게시판 명령어 처리 등록
		map.put("boardList", new BoardListAction());
		map.put("boardWriteForm", new BoardWriteFormAction());
		map.put("boardWriteProcess", new boardWriteProcessAction());
		map.put("boardDetail", new BoardDetailAction());
		map.put("boardModifyForm", new BoardModifyFormAction());
		map.put("boardModifyProcess", new BoardModifyProcessAction());
		map.put("boardDeleteForm", new BoardDeleteFormAction());
		map.put("boardDeleteProcess", new BoardDeleteProcessAction());
		map.put("boardReplyForm", new BoardReplyFormAction());
		map.put("boardReplyProcess", new BoardReplyProcessAction());
		
		//파일 게시판 명령어 처리 등록
		map.put("boardFileList", new BoardFileListAction());
		map.put("boardFileWriteForm", new BoardFileWriteFormAction());
		map.put("boardFileWriteProcess", new BoardFileWriteProcessAction());
		map.put("boardFileDetail", new BoardFileDetailAction());
		map.put("boardFileDeleteForm", new BoardFileDeleteFormAction());
		map.put("boardFileDeleteProcess", new BoardFileDeleteProcessAction());
		map.put("boardFileModifyForm", new BoardFileModifyFormAction());
		map.put("boardFileModifyProcess", new BoardFileModifyProcessAction());
	}
	
	
	public Action getAction(String command) {
		return map.get(command);
	}//getAction

	
}//MemberActionFactory

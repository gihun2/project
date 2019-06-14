package vo;

import java.sql.Timestamp;

/*
Board 테이블 생성 SQL문
CREATE TABLE board(
	num INT PRIMARY KEY,
	name VARCHAR(20),
	pass VARCHAR(15),
	subject VARCHAR(50),
	content VARCHAR(2000),
	filename VARCHAR(50)
	re_ref INT, 
	re_lev INT,
	re_seq INT,
	readcount INT,
	reg_date TIMESTAMP,
	ip VARCHAR(30)
);
컬럼 추가
ALTER TABLE board ADD ip VARCHAR(30);
 */
	public class Sboard {
			private int num;			//글번호
			private String name;		//작성자명
			private String pass;		//글 비밀번호	
			private String subject;		//글제목
			private String content;		//글내용
			private String filename;	//업로드한 파일명
			private int re_ref;			//글그룹 번호
			private int re_lev;			//글 들여쓰기(답글) 레벨
			private int re_seq;			//글그룹 내에서의 순서
			private int readcount;		//조회수
			private Timestamp reg_date;	//글 작성(등록) 날짜시간
			private String ip;			//작성자 IP주소
			
			public String getIp() {
				return ip;
			}
			public void setIp(String ip) {
				this.ip = ip;
			}		
			public int getNum() {
				return num;
			}
			public void setNum(int num) {
				this.num = num;
			}
			public String getPass() {
				return pass;
			}
			public void setPass(String pass) {
				this.pass = pass;
			}
			public String getSubject() {
				return subject;
			}
			public void setSubject(String subject) {
				this.subject = subject;
			}
			public String getContent() {
				return content;
			}
			public void setContent(String content) {
				this.content = content;
			}
			public String getFilename() {
				return filename;
			}
			public void setFilename(String filename) {
				this.filename = filename;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getRe_ref() {
				return re_ref;
			}
			public void setRe_ref(int re_ref) {
				this.re_ref = re_ref;
			}
			public int getRe_lev() {
				return re_lev;
			}
			public void setRe_lev(int re_lev) {
				this.re_lev = re_lev;
			}
			public int getRe_seq() {
				return re_seq;
			}
			public void setRe_seq(int re_seq) {
				this.re_seq = re_seq;
			}
			public int getReadcount() {
				return readcount;
			}
			public void setReadcount(int readcount) {
				this.readcount = readcount;
			}
			public Timestamp getReg_date() {
				return reg_date;
			}
			public void setReg_date(Timestamp reg_date) {
				this.reg_date = reg_date;
			}

			
	}
	
	

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Sboard;

public class BoardDao {
	private static BoardDao boardDao=new BoardDao();
	
	private BoardDao() {
	}

	public static BoardDao getInstance() {
		return boardDao;
	}
	
	public int insert(Sboard board) {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int rowCount=0; //INSERT 된 행(레코드)의 개수
		
		
		try {
			con=H2DBUtil.getConnection();
			//글번호 만들기 작업
			int num=0; //생성된 글번호를 저장할 변수
			StringBuilder sb=new StringBuilder();
			sb.append("SELECT MAX(num) FROM sboard ");
			
			pstmt=con.prepareStatement(sb.toString());
			//SELECT 실행
			rs=pstmt.executeQuery();
			//rs 데이터 있으면 num=최대값+1, 없으면 num=1
			if(rs.next()) {
				num=rs.getInt(1)+1;
			} else {
				num=1;
			}
			
			sb.setLength(0); //기존 버퍼내용 삭제. 기존객체 재사용
			pstmt.close(); //SELECT문을 가진 psmt 객체 닫기
			//글쓰기 작업	
			sb.append("INSERT INTO sboard (num, name, pass, subject, content, filename, re_ref, re_lev, re_seq, readcount, ip, reg_date) ");
			sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
			//INSERT문을 가진 pstmt 객체 생성.
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getPass());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			pstmt.setString(6, board.getFilename());
			pstmt.setInt(7, num); // re_ref==num 주글일때는 글그룹번호와 글번호가 같음.
			pstmt.setInt(8, 0); //re_lev 들여쓰기 레벨
			pstmt.setInt(9, 0); //re_seq 글그룹 내에서는 오름차순 정렬. 최상단에 주글이 옴.
			pstmt.setInt(10, 0); //readcount 조회수
			pstmt.setString(11, board.getIp()); //IP주소
			//INSERT문 실행
			rowCount=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}		
		return rowCount;
	}//insert()
	
	public List<Sboard> getBoards(int startRow, int pageSize, String search){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		List<Sboard> list=new ArrayList<>();
		
		try {
			con=H2DBUtil.getConnection();
			
			StringBuilder sb= new StringBuilder();
			sb.append("SELECT * FROM sboard ");
			if(search != null) {
				sb.append("WHERE subject LIKE ? ");
			}
			sb.append("ORDER BY re_ref DESC, re_lev, re_seq ");
			sb.append("OFFSET ? LIMIT ? ");
			
			pstmt=con.prepareStatement(sb.toString());
			
		if(search != null) {//검색어가 있을때
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
		} else {//검색어가 없을때
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
		}
			//실행
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Sboard board=new Sboard();
				board.setNum(rs.getInt("num"));
				board.setName(rs.getString("name"));
				board.setPass(rs.getString("pass"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setFilename(rs.getString("filename"));
				board.setRe_ref(rs.getInt("re_ref"));
				board.setRe_lev(rs.getInt("re_lev"));
				board.setRe_seq(rs.getInt("re_seq"));
				board.setReadcount(rs.getInt("readcount"));
				board.setIp(rs.getString("ip"));
				board.setReg_date(rs.getTimestamp("reg_date"));
				
				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return list;
	}//getBoards()
	
	//board 테이블 전체 레코드(행) 개수 구하기
	public int getBoardCount() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		int count=0;
		
		try {
			con=H2DBUtil.getConnection();
			pstmt=con.prepareStatement("SELECT COUNT(*) FROM sboard ");
			//실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return count;
	}//getBoardCount()
	
	//board 테이블 전체 레코드(행) 개수 구하기
		public int getBoardCount(String search) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			int count=0;
			
			try {
				con=H2DBUtil.getConnection();
				pstmt=con.prepareStatement("SELECT COUNT(*) FROM sboard WHERE subject LIKE ? ");
				pstmt.setString(1, "%"+search+"%");
				//실행
				rs=pstmt.executeQuery();
				if(rs.next()) {
					count=rs.getInt(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}
			return count;
		}//getBoardCount()
		
		//글 조회수 1 증가시키기
		public void updateReadcount(int num) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				con=H2DBUtil.getConnection();
				
				String sql="UPDATE sboard SET readcount = readcount+1 WHERE num=?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				//실행
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}			
		}//updateReadcount()
		
		//글 한개 레코드 가져오기
		public Sboard getBoard(int num) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			Sboard board=null;
			
			try {
				con=H2DBUtil.getConnection();
				
				String sql="SELECT * FROM sboard WHERE num = ? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				//실행
				rs = pstmt.executeQuery();
				System.out.println("pstmt:: "+pstmt);
				if(rs.next()) {
					board=new Sboard();
					board.setNum(rs.getInt("num"));
					board.setName(rs.getString("name"));
					board.setPass(rs.getString("pass"));
					board.setSubject(rs.getString("subject"));
					board.setContent(rs.getString("content"));
					board.setFilename(rs.getString("filename"));
					board.setRe_ref(rs.getInt("re_ref"));
					board.setRe_lev(rs.getInt("re_lev"));
					board.setRe_seq(rs.getInt("re_seq"));
					board.setReadcount(rs.getInt("readcount"));
					board.setIp(rs.getString("ip"));
					board.setReg_date(rs.getTimestamp("reg_date"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}
			return board;
		}//getBoard()
		
		public boolean updateBoard(Sboard board) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			boolean isSuccess=false;
			String sql="";
			
			try {
				con=H2DBUtil.getConnection();
				//num에 해당하는 pass 가져오기
				sql="SELECT pass FROM sboard WHERE num=? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, board.getNum());
				// select문 실행
				rs=pstmt.executeQuery();
				//rs데이터 있으면 글비밀번호 비교, 맞으면 update문 수행, isSuccess=true 
				if(rs.next()) {
					if(board.getPass().equals(rs.getString("pass"))) {
//						rs.close();	//rs 객체 먼저 닫기
						pstmt.close(); //select문 가진 pstmt객체 닫기
						
						sql="UPDATE sboard ";
						sql+="SET name=?, subject=?, content=? ";
						sql+="WHERE num=? ";
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, board.getName());
						pstmt.setString(2, board.getSubject());
						pstmt.setString(3, board.getContent());
						pstmt.setInt(4, board.getNum());
						//update문 실행
						pstmt.executeUpdate();
						
						isSuccess=true;
					} else {
						isSuccess=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}				
			return isSuccess;
		}//updateBoard()
		
		public boolean deleteBoard(int num, String pass) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			boolean isSuccess=false;
			String sql="";
			
			try {
				con=H2DBUtil.getConnection();
				//num에 해당하는 pass 가져오기
				sql="SELECT pass FROM sboard WHERE num=? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				// select문 실행
				rs=pstmt.executeQuery();
				//rs데이터 있으면 글비밀번호 비교, 맞으면 delete문 수행, isSuccess=true
				if(rs.next()) {
					if(pass.equals(rs.getString("pass"))) {
						pstmt.close(); //select문 가진 pstmt객체 닫기
						
						sql="DELETE FROM sboard WHERE num=? ";
						pstmt=con.prepareStatement(sql);
						pstmt.setInt(1, num);
						//delete문 실행
						pstmt.executeUpdate();
						
						isSuccess = true;
					} else {
						isSuccess = false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}
	
			return isSuccess;
		}//deleteBoard()
		
		//답글쓰기 메소드
//		답글쓰기 단계
//		1) 답글쓰는 대상글의  re_seq보다 큰 행들에 대해서 1씩 증가
//		2) 글번호 MAX(num)+1 로 글번호 생성
//		3) 같은 글그룹, 들여쓰기 1증가, 그룹 내 순번 1증가
		
		// 답글쓰기 메소드는 트랜잭션 처리가 필요한 메소드이다.
		public void replyInsert(Sboard board) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;						
			
			try {
				// DB연결작업으로 커넥션객체 가져옴.
				// 가져온 커넥션 객체는 autocommit(자동커밋)이 설정되있다.
				con = H2DBUtil.getConnection();
				// 자동커밋을 수동커밋으로 직접 트랜잭션 처리해야한다.
				con.setAutoCommit(false);
				
				StringBuilder sb=new StringBuilder();
				
				// 1)기존 답글들의 순서(re_seq) 재배치
				sb.append("UPDATE sboard ");
				sb.append("SET re_seq=re_seq+1 ");
				sb.append("WHERE re_ref = ? ");
				sb.append("AND re_seq > ? ");
				
				pstmt = con.prepareStatement(sb.toString());
				pstmt.setInt(1, board.getRe_ref());
				pstmt.setInt(2, board.getRe_seq());
				//UPDATE문 실행
				pstmt.executeUpdate(); //commit 자동처리 안함!
				
				// 2) 글번호 만들기
				pstmt.close(); //UPDATE문을 가진 pstmt 객체 닫기
				
				sb.setLength(0); //기존 StringBuilder 객체 초기화
				sb.append("SELECT MAX(num) FROM sboard ");
				pstmt=con.prepareStatement(sb.toString());
				//SELECT문 실행
				rs=pstmt.executeQuery();
				if(rs.next()) {
					//답글 글번호 생성해서 board 글번호 필드에 저장
					board.setNum(rs.getInt("MAX(num)")+1); 
				}
				
				// 3) 답글 INSERT
				pstmt.close(); // SELECT문을 가진 pstmt 객체 닫기
				
				sb.setLength(0); //기존 StringBuilder 객체 초기화
				
				sb.append("INSERT INTO sboard (num, name, pass, subject, content, filename, re_ref, re_lev, re_seq, readcount, ip, reg_date) ");
				sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
				//INSERT문을 가진 pstmt 객체 생성.
				pstmt=con.prepareStatement(sb.toString());
				pstmt.setInt(1, board.getNum());
				pstmt.setString(2, board.getName());
				pstmt.setString(3, board.getPass());
				pstmt.setString(4, board.getSubject());
				pstmt.setString(5, board.getContent());
				pstmt.setString(6, board.getFilename());
				pstmt.setInt(7, board.getRe_ref()); // re_ref 는 [답글을 다는 대상글]의 글그룹번호와 동일.
				pstmt.setInt(8, board.getRe_lev()+1); //re_lev 는 [답글을 다는 대상글]의 들여쓰기값 + 1
				pstmt.setInt(9, board.getRe_seq()+1); //re_seq 는 [답글을 다는 대상글]의 글그룹 내 순번값 + 1
				pstmt.setInt(10, 0); //readcount 조회수
				pstmt.setString(11, board.getIp()); //IP주소
				//INSERT문 실행
				pstmt.executeUpdate();
				
				// 수동 커밋처리 해준다.
				con.commit();
				
				//자동커밋으로 다시 초기화
				con.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();				
				try {
					con.rollback(); //try블럭 중간에 예외발생시 롤백처리함!
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}
			
		}//replyInsert()
		
		public boolean checkBoard(int num, String pass) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			boolean result=false;
			
			try {
				con=H2DBUtil.getConnection();
				
				String sql="SELECT pass FROM sboard WHERE num=? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				//select문 실행
				rs=pstmt.executeQuery();
				if(rs.next()) {
					if(pass.equals(rs.getString("pass"))) {
						result=true;
					} else {
						result=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}
			
			return result;
		}//checkBoard()	
		
//		public boolean deleteAllBoard(int num, String pass) {
//			Connection con=null;
//			PreparedStatement pstmt=null;
//			ResultSet rs=null;
//			
//			boolean isSuccess=false;
//			String sql="";
//			
//			try {
//				con=H2DBUtil.getConnection();
//				sql="SELECT * FROM smember WHERE id='admin' ";
//				pstmt=con.prepareStatement(sql);
//				// select문 실행
//				rs=pstmt.executeQuery();
//				//관리자 비밀번호랑 비교해서 맞으면 삭제되게, isSuccess=true
//				if(rs.next()) {
//					if() {
//						pstmt.close(); //select문 가진 pstmt객체 닫기
//						sql="DELETE FROM sboard WHERE num=? ";
//						pstmt=con.prepareStatement(sql);
//						pstmt.setInt(1, num);
//						//delete문 실행
//						pstmt.executeUpdate();
//						
//						isSuccess = true;
//					} else {
//						isSuccess = false;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				H2DBUtil.closeJDBC(con, pstmt, rs);
//			}
//	
//			return isSuccess;
//		}//deleteAllBoard()
		
		
		
}

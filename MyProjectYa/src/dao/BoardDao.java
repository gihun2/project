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
		int rowCount=0; //INSERT �� ��(���ڵ�)�� ����
		
		
		try {
			con=H2DBUtil.getConnection();
			//�۹�ȣ ����� �۾�
			int num=0; //������ �۹�ȣ�� ������ ����
			StringBuilder sb=new StringBuilder();
			sb.append("SELECT MAX(num) FROM sboard ");
			
			pstmt=con.prepareStatement(sb.toString());
			//SELECT ����
			rs=pstmt.executeQuery();
			//rs ������ ������ num=�ִ밪+1, ������ num=1
			if(rs.next()) {
				num=rs.getInt(1)+1;
			} else {
				num=1;
			}
			
			sb.setLength(0); //���� ���۳��� ����. ������ü ����
			pstmt.close(); //SELECT���� ���� psmt ��ü �ݱ�
			//�۾��� �۾�	
			sb.append("INSERT INTO sboard (num, name, pass, subject, content, filename, re_ref, re_lev, re_seq, readcount, ip, reg_date) ");
			sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
			//INSERT���� ���� pstmt ��ü ����.
			pstmt=con.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getName());
			pstmt.setString(3, board.getPass());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			pstmt.setString(6, board.getFilename());
			pstmt.setInt(7, num); // re_ref==num �ֱ��϶��� �۱׷��ȣ�� �۹�ȣ�� ����.
			pstmt.setInt(8, 0); //re_lev �鿩���� ����
			pstmt.setInt(9, 0); //re_seq �۱׷� �������� �������� ����. �ֻ�ܿ� �ֱ��� ��.
			pstmt.setInt(10, 0); //readcount ��ȸ��
			pstmt.setString(11, board.getIp()); //IP�ּ�
			//INSERT�� ����
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
			
		if(search != null) {//�˻�� ������
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, pageSize);
		} else {//�˻�� ������
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, pageSize);
		}
			//����
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
	
	//board ���̺� ��ü ���ڵ�(��) ���� ���ϱ�
	public int getBoardCount() {
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		int count=0;
		
		try {
			con=H2DBUtil.getConnection();
			pstmt=con.prepareStatement("SELECT COUNT(*) FROM sboard ");
			//����
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
	
	//board ���̺� ��ü ���ڵ�(��) ���� ���ϱ�
		public int getBoardCount(String search) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			int count=0;
			
			try {
				con=H2DBUtil.getConnection();
				pstmt=con.prepareStatement("SELECT COUNT(*) FROM sboard WHERE subject LIKE ? ");
				pstmt.setString(1, "%"+search+"%");
				//����
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
		
		//�� ��ȸ�� 1 ������Ű��
		public void updateReadcount(int num) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			try {
				con=H2DBUtil.getConnection();
				
				String sql="UPDATE sboard SET readcount = readcount+1 WHERE num=?";
				
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				//����
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				H2DBUtil.closeJDBC(con, pstmt, rs);
			}			
		}//updateReadcount()
		
		//�� �Ѱ� ���ڵ� ��������
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
				//����
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
				//num�� �ش��ϴ� pass ��������
				sql="SELECT pass FROM sboard WHERE num=? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, board.getNum());
				// select�� ����
				rs=pstmt.executeQuery();
				//rs������ ������ �ۺ�й�ȣ ��, ������ update�� ����, isSuccess=true 
				if(rs.next()) {
					if(board.getPass().equals(rs.getString("pass"))) {
//						rs.close();	//rs ��ü ���� �ݱ�
						pstmt.close(); //select�� ���� pstmt��ü �ݱ�
						
						sql="UPDATE sboard ";
						sql+="SET name=?, subject=?, content=? ";
						sql+="WHERE num=? ";
						pstmt=con.prepareStatement(sql);
						pstmt.setString(1, board.getName());
						pstmt.setString(2, board.getSubject());
						pstmt.setString(3, board.getContent());
						pstmt.setInt(4, board.getNum());
						//update�� ����
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
				//num�� �ش��ϴ� pass ��������
				sql="SELECT pass FROM sboard WHERE num=? ";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, num);
				// select�� ����
				rs=pstmt.executeQuery();
				//rs������ ������ �ۺ�й�ȣ ��, ������ delete�� ����, isSuccess=true
				if(rs.next()) {
					if(pass.equals(rs.getString("pass"))) {
						pstmt.close(); //select�� ���� pstmt��ü �ݱ�
						
						sql="DELETE FROM sboard WHERE num=? ";
						pstmt=con.prepareStatement(sql);
						pstmt.setInt(1, num);
						//delete�� ����
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
		
		//��۾��� �޼ҵ�
//		��۾��� �ܰ�
//		1) ��۾��� ������  re_seq���� ū ��鿡 ���ؼ� 1�� ����
//		2) �۹�ȣ MAX(num)+1 �� �۹�ȣ ����
//		3) ���� �۱׷�, �鿩���� 1����, �׷� �� ���� 1����
		
		// ��۾��� �޼ҵ�� Ʈ����� ó���� �ʿ��� �޼ҵ��̴�.
		public void replyInsert(Sboard board) {
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;						
			
			try {
				// DB�����۾����� Ŀ�ؼǰ�ü ������.
				// ������ Ŀ�ؼ� ��ü�� autocommit(�ڵ�Ŀ��)�� �������ִ�.
				con = H2DBUtil.getConnection();
				// �ڵ�Ŀ���� ����Ŀ������ ���� Ʈ����� ó���ؾ��Ѵ�.
				con.setAutoCommit(false);
				
				StringBuilder sb=new StringBuilder();
				
				// 1)���� ��۵��� ����(re_seq) ���ġ
				sb.append("UPDATE sboard ");
				sb.append("SET re_seq=re_seq+1 ");
				sb.append("WHERE re_ref = ? ");
				sb.append("AND re_seq > ? ");
				
				pstmt = con.prepareStatement(sb.toString());
				pstmt.setInt(1, board.getRe_ref());
				pstmt.setInt(2, board.getRe_seq());
				//UPDATE�� ����
				pstmt.executeUpdate(); //commit �ڵ�ó�� ����!
				
				// 2) �۹�ȣ �����
				pstmt.close(); //UPDATE���� ���� pstmt ��ü �ݱ�
				
				sb.setLength(0); //���� StringBuilder ��ü �ʱ�ȭ
				sb.append("SELECT MAX(num) FROM sboard ");
				pstmt=con.prepareStatement(sb.toString());
				//SELECT�� ����
				rs=pstmt.executeQuery();
				if(rs.next()) {
					//��� �۹�ȣ �����ؼ� board �۹�ȣ �ʵ忡 ����
					board.setNum(rs.getInt("MAX(num)")+1); 
				}
				
				// 3) ��� INSERT
				pstmt.close(); // SELECT���� ���� pstmt ��ü �ݱ�
				
				sb.setLength(0); //���� StringBuilder ��ü �ʱ�ȭ
				
				sb.append("INSERT INTO sboard (num, name, pass, subject, content, filename, re_ref, re_lev, re_seq, readcount, ip, reg_date) ");
				sb.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)");
				//INSERT���� ���� pstmt ��ü ����.
				pstmt=con.prepareStatement(sb.toString());
				pstmt.setInt(1, board.getNum());
				pstmt.setString(2, board.getName());
				pstmt.setString(3, board.getPass());
				pstmt.setString(4, board.getSubject());
				pstmt.setString(5, board.getContent());
				pstmt.setString(6, board.getFilename());
				pstmt.setInt(7, board.getRe_ref()); // re_ref �� [����� �ٴ� ����]�� �۱׷��ȣ�� ����.
				pstmt.setInt(8, board.getRe_lev()+1); //re_lev �� [����� �ٴ� ����]�� �鿩���Ⱚ + 1
				pstmt.setInt(9, board.getRe_seq()+1); //re_seq �� [����� �ٴ� ����]�� �۱׷� �� ������ + 1
				pstmt.setInt(10, 0); //readcount ��ȸ��
				pstmt.setString(11, board.getIp()); //IP�ּ�
				//INSERT�� ����
				pstmt.executeUpdate();
				
				// ���� Ŀ��ó�� ���ش�.
				con.commit();
				
				//�ڵ�Ŀ������ �ٽ� �ʱ�ȭ
				con.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();				
				try {
					con.rollback(); //try�� �߰��� ���ܹ߻��� �ѹ�ó����!
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
				//select�� ����
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
//				// select�� ����
//				rs=pstmt.executeQuery();
//				//������ ��й�ȣ�� ���ؼ� ������ �����ǰ�, isSuccess=true
//				if(rs.next()) {
//					if() {
//						pstmt.close(); //select�� ���� pstmt��ü �ݱ�
//						sql="DELETE FROM sboard WHERE num=? ";
//						pstmt=con.prepareStatement(sql);
//						pstmt.setInt(1, num);
//						//delete�� ����
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

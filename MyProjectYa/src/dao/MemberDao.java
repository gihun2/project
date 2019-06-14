package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import vo.Smember;

public class MemberDao {

	private static MemberDao instance;

	private MemberDao() {
	}

	public static MemberDao getInstance() {
		if (instance == null)
			instance = new MemberDao();
		return instance;
	}

	public int insert(Smember member) {
		int insertedRowCount = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";

		try {
			con = H2DBUtil.getConnection();
			sql = "INSERT INTO smember (id,password,name,birthday,gender,email,address,tel,mtel,reg_date) ";
			sql += "VALUES (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setTimestamp(4, member.getBirthday());
			pstmt.setString(5, member.getGender());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getTel());
			pstmt.setString(9, member.getMtel());
			// 실행
			insertedRowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, null);
		}
		return insertedRowCount;
	}// insert()

	public int[] batchInsert(List<Smember> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";

		int[] resultArray = null;

		try {
			con = H2DBUtil.getConnection();

			sql = "INSERT INTO smember (id, password, name, reg_date) ";
			sql += "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
			pstmt = con.prepareStatement(sql);

			for (Smember member : list) {
				pstmt.setString(1, member.getId());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());

//				pstmt.executeUpdate(); // DB에 문장 전송
				pstmt.addBatch(); // DB에 전송할 문장을 버퍼에 저장
			}
			resultArray = pstmt.executeBatch(); // DB에 대량의 SQL문 전송한다.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, null);
		}

		return resultArray;
	}// batchInsert()

	public int countById(String id) {
		int count = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			con = H2DBUtil.getConnection();
			sql = "SELECT COUNT(*) FROM smember WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 실행
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}

		return count;
	}// countById()

	public List<Smember> getMembers(int startRow, int pageSize, String search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<Smember> list = new ArrayList<>();

		try {
			con = H2DBUtil.getConnection();

			StringBuilder sb = new StringBuilder();
			sb.append("SELECT * FROM smember ");
			if (search != null) {
				sb.append("WHERE id LIKE ? ");
			}
			sb.append("ORDER BY name ASC, tel, mtel ");
			sb.append("OFFSET ? LIMIT ? ");

			pstmt = con.prepareStatement(sb.toString());

			if (search != null) {// 검색어가 있을때
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, pageSize);
			} else {// 검색어가 없을때
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, pageSize);
			}
			// 실행
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Smember member = new Smember();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setBirthday(rs.getTimestamp("birthday"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setTel(rs.getString("tel"));
				member.setMtel(rs.getString("mtel"));
				member.setReg_date(rs.getTimestamp("reg_date"));

				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return list;
	}// getMembers()

	// member 테이블 전체 레코드(행) 개수 구하기
	public int getMemberCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = H2DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM smember ");
			// 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return count;
	}// getMemberCount()

	// member 테이블 전체 레코드(행) 개수 구하기
	public int getMemberCount(String search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = H2DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM smember WHERE id LIKE ? ");
			pstmt.setString(1, "%" + search + "%");
			// 실행
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return count;
	}// getMemberCount()

	// 멤버 한개 레코드 가져오기
	public Smember getMember(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Smember member = new Smember();

		try {
			con = H2DBUtil.getConnection();

			String sql = "SELECT * FROM smember WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			// 실행
			rs = pstmt.executeQuery();
			System.out.println("pstmt:: " + pstmt);
			if (rs.next()) {
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setBirthday(rs.getTimestamp("birthday"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setTel(rs.getString("tel"));
				member.setMtel(rs.getString("mtel"));
				member.setReg_date(rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return member;
	}// getMember()

	public int loginCheck(String id, String pass) {
		// * 반환값 의미
		// ID 불일치 : -1, Password 불일치 : 0, 모두 일치 : 1
		int check = -1;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = H2DBUtil.getConnection();
			sql = "SELECT password FROM smember WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) { // ID 일치
				if (pass.equals(rs.getString("password"))) {
					// Password 일치
					check = 1;
				} else {
					check = 0; // Password 불일치
				}
			} else {
				check = -1; // ID 불일치
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		return check;
	}// loginCheck()

	public List<Smember> getAllMembers(int startRow, int pageSize, String search) {
		List<Smember> list = new ArrayList<>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		try {
			con = H2DBUtil.getConnection();
			sql = "SELECT * FROM smember ";
			if (search != null) {
				sql += "WHERE id LIKE ? ";
			}
			sql += "ORDER BY id ";
			sql += "OFFSET ? LIMIT ? ";
			pstmt = con.prepareStatement(sql);

			if (search != null) {// 검색어가 있을때
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, pageSize);
			} else {// 검색어가 없을때
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, pageSize);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Smember member = new Smember();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setBirthday(rs.getTimestamp("birthday"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setTel(rs.getString("tel"));
				member.setMtel(rs.getString("mtel"));
				member.setReg_date(rs.getTimestamp("reg_date"));

				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}

		return list;

	}// getAllMembers()

	// JSONArray로 회원목록 가져오기
	public JSONArray getAllMembersJSONArray() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";

		JSONArray jsonArray = new JSONArray();

		try {
			con = H2DBUtil.getConnection();
			sql = "SELECT * FROM smember ORDER BY id";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", rs.getString("id"));
				jsonObject.put("password", rs.getString("password"));
				jsonObject.put("name", rs.getString("name"));
				jsonObject.put("email", rs.getString("email"));

				jsonArray.add(jsonObject);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}

		return jsonArray;

	}// getAllMembersJSONArray()

	public JSONArray getCountPerAddress() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		JSONArray jsonArray = new JSONArray();

		JSONArray colNameArray = new JSONArray();
		colNameArray.add("거주지");
		colNameArray.add("인원수");

		jsonArray.add(colNameArray);

		try {
			con = H2DBUtil.getConnection();

			sb.append("SELECT SUBSTRING(address, 1,2) AS addr, COUNT(*) AS cnt ");
			sb.append("FROM smember ");
			sb.append("GROUP BY  SUBSTRING(address, 1,2) ");
			sb.append("ORDER BY addr ");

			pstmt = con.prepareStatement(sb.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				JSONArray rowArray = new JSONArray();
				rowArray.add(rs.getString("addr"));
				rowArray.add(rs.getInt("cnt"));

				jsonArray.add(rowArray);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, rs);
		}
		System.out.println(jsonArray);
		return jsonArray;
	}// getCountPerAddress()

	public Smember getUserInfo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Smember member = null;

		try {
			// 쿼리
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM smember WHERE id=? ");

			conn = H2DBUtil.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) // 회원정보를 DAO에 담는다.
			{
				// 정보를 담는다.
				member = new Smember();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setBirthday(rs.getTimestamp("birthday"));
				member.setGender(rs.getString("gender"));
				member.setEmail(rs.getString("email"));
				member.setAddress(rs.getString("address"));
				member.setTel(rs.getString("tel"));
				member.setMtel(rs.getString("mtel"));
				member.setReg_date(rs.getTimestamp("reg_date"));
			}
			return member;
		} catch (Exception sqle) {
			throw new RuntimeException(sqle.getMessage());
		} finally {
			// Connection, PreparedStatement를 닫는다.
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	} // getUserInfo()

	public boolean updateMember(Smember member) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		boolean isSuccess = false;
		String sql = "";

		try {
			con = H2DBUtil.getConnection();
			// id에 해당하는 password 가져오기
			sql = "SELECT password FROM smember WHERE id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			// select문 실행
			rs = pstmt.executeQuery();

			// rs데이터 있으면 비밀번호 비교, 맞으면 update문 수행, isSuccess=true
			if (rs.next()) {
				if (member.getPassword().equals(rs.getString("password"))) {
					pstmt.close(); // select문 가진 pstmt객체 닫기

					sql = "UPDATE smember ";
					sql += "SET password=?, name=?, birthday=?, gender=?, email=?, address=?, tel=?, mtel=? ";
					sql += "WHERE id=? ";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, member.getPassword());
					pstmt.setString(2, member.getName());
					pstmt.setTimestamp(3, member.getBirthday());
					pstmt.setString(4, member.getGender());
					pstmt.setString(5, member.getEmail());
					pstmt.setString(6, member.getAddress());
					pstmt.setString(7, member.getTel());
					pstmt.setString(8, member.getMtel());
					pstmt.setString(9, member.getId());
					// update문 실행
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
	} // end updateMember

	@SuppressWarnings("resource")
	public int deleteMember(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String dbpw = ""; // DB상의 비밀번호를 담아둘 변수
		int x = -1;

		try {
			// 비밀번호 조회
			StringBuffer query1 = new StringBuffer();
			query1.append("SELECT password FROM smember WHERE id=? ");

			// 회원 삭제
			StringBuffer query2 = new StringBuffer();
			query2.append("DELETE FROM smember WHERE id=? ");

			conn = H2DBUtil.getConnection();

			// 자동 커밋을 false로 한다.
			conn.setAutoCommit(false);

			// 1. 아이디에 해당하는 비밀번호를 조회한다.
			pstmt = conn.prepareStatement(query1.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbpw = rs.getString("password");
				if (dbpw.equals(pw)) // 입력된 비밀번호와 DB비번 비교
				{
					// 같을경우 회원삭제 진행
					pstmt = conn.prepareStatement(query2.toString());
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					conn.commit();
					x = 1; // 삭제 성공
				} else {
					x = 0; // 비밀번호 비교결과 - 다름
				}
			}

			return x;

		} catch (Exception sqle) {
			try {
				conn.rollback(); // 오류시 롤백
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException(sqle.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	} // end deleteMember

	public int deleteChoiceMember(String[] id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";

		int[] checkArray = null;
		
		int result = 0;
		try {
			con = H2DBUtil.getConnection();

			con.setAutoCommit(false);

			sql = "DELETE FROM smember WHERE id = ? ";
			pstmt = con.prepareStatement(sql);

			for (String str : id) {
				pstmt.setString(1, str);

				pstmt.addBatch();
			}
			checkArray = pstmt.executeBatch();

			for (int i : checkArray) {
				if (i == 0) {
					result = -1;
					break;
				}
			}
			if (result == -1) {
				con.rollback();
			} else {
				con.commit();
				result = 1;
			}

			con.setAutoCommit(true);

		} catch (Exception e) {
			System.out.println("batchDelete 조땜.");
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, null);
		}
		
		return result;
	} // deleteChoiceMember

}// MemberDao

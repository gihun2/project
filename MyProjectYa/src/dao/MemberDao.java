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
			// ����
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

//				pstmt.executeUpdate(); // DB�� ���� ����
				pstmt.addBatch(); // DB�� ������ ������ ���ۿ� ����
			}
			resultArray = pstmt.executeBatch(); // DB�� �뷮�� SQL�� �����Ѵ�.

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
			// ����
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

			if (search != null) {// �˻�� ������
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, pageSize);
			} else {// �˻�� ������
				pstmt.setInt(1, startRow);
				pstmt.setInt(2, pageSize);
			}
			// ����
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

	// member ���̺� ��ü ���ڵ�(��) ���� ���ϱ�
	public int getMemberCount() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = H2DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM smember ");
			// ����
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

	// member ���̺� ��ü ���ڵ�(��) ���� ���ϱ�
	public int getMemberCount(String search) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;

		try {
			con = H2DBUtil.getConnection();
			pstmt = con.prepareStatement("SELECT COUNT(*) FROM smember WHERE id LIKE ? ");
			pstmt.setString(1, "%" + search + "%");
			// ����
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

	// ��� �Ѱ� ���ڵ� ��������
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

			// ����
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
		// * ��ȯ�� �ǹ�
		// ID ����ġ : -1, Password ����ġ : 0, ��� ��ġ : 1
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

			if (rs.next()) { // ID ��ġ
				if (pass.equals(rs.getString("password"))) {
					// Password ��ġ
					check = 1;
				} else {
					check = 0; // Password ����ġ
				}
			} else {
				check = -1; // ID ����ġ
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

			if (search != null) {// �˻�� ������
				pstmt.setString(1, "%" + search + "%");
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, pageSize);
			} else {// �˻�� ������
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

	// JSONArray�� ȸ����� ��������
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
		colNameArray.add("������");
		colNameArray.add("�ο���");

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
			// ����
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM smember WHERE id=? ");

			conn = H2DBUtil.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			if (rs.next()) // ȸ�������� DAO�� ��´�.
			{
				// ������ ��´�.
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
			// Connection, PreparedStatement�� �ݴ´�.
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
			// id�� �ش��ϴ� password ��������
			sql = "SELECT password FROM smember WHERE id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			// select�� ����
			rs = pstmt.executeQuery();

			// rs������ ������ ��й�ȣ ��, ������ update�� ����, isSuccess=true
			if (rs.next()) {
				if (member.getPassword().equals(rs.getString("password"))) {
					pstmt.close(); // select�� ���� pstmt��ü �ݱ�

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
					// update�� ����
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

		String dbpw = ""; // DB���� ��й�ȣ�� ��Ƶ� ����
		int x = -1;

		try {
			// ��й�ȣ ��ȸ
			StringBuffer query1 = new StringBuffer();
			query1.append("SELECT password FROM smember WHERE id=? ");

			// ȸ�� ����
			StringBuffer query2 = new StringBuffer();
			query2.append("DELETE FROM smember WHERE id=? ");

			conn = H2DBUtil.getConnection();

			// �ڵ� Ŀ���� false�� �Ѵ�.
			conn.setAutoCommit(false);

			// 1. ���̵� �ش��ϴ� ��й�ȣ�� ��ȸ�Ѵ�.
			pstmt = conn.prepareStatement(query1.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dbpw = rs.getString("password");
				if (dbpw.equals(pw)) // �Էµ� ��й�ȣ�� DB��� ��
				{
					// ������� ȸ������ ����
					pstmt = conn.prepareStatement(query2.toString());
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					conn.commit();
					x = 1; // ���� ����
				} else {
					x = 0; // ��й�ȣ �񱳰�� - �ٸ�
				}
			}

			return x;

		} catch (Exception sqle) {
			try {
				conn.rollback(); // ������ �ѹ�
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
			System.out.println("batchDelete ����.");
			e.printStackTrace();
		} finally {
			H2DBUtil.closeJDBC(con, pstmt, null);
		}
		
		return result;
	} // deleteChoiceMember

}// MemberDao

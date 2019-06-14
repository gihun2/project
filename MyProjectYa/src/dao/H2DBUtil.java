package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

public class H2DBUtil {
	
	private static JdbcConnectionPool jdbcConnectionPool;
	
	// DB����
		public static Connection getConnection() throws Exception {
			String url = "jdbc:h2:tcp://localhost/~/������";
			String user = "sa";
			String passwd = "";
			Connection con = null;

			// 1) JDBC ����̹� �ε�
			Class.forName("org.h2.Driver");
			
			//H2DB�� JDBC Ŀ�ؼ� Ǯ ����
			if(jdbcConnectionPool==null) {
				jdbcConnectionPool=JdbcConnectionPool.create(url, user, passwd);
				jdbcConnectionPool.setMaxConnections(100);
			}
			con=jdbcConnectionPool.getConnection();
			
			return con;
		}// getConnection()
		
		//JDBC �ڿ��ݱ�
		public static void closeJDBC(Connection con, PreparedStatement pstmt, ResultSet rs) {
				//JDBC �ڿ��ݱ�
				if(rs!=null) {
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(con!=null) {
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}//closeJDBC()
}//OracleDB

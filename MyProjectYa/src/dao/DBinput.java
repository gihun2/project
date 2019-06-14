package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;
//��� ���� ������ �ؾ� �Ѵ�, truncate table board;
public class DBinput {
   static Random random = new Random();
   
   public static void main(String[] args) {
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      try {
         con = H2DBUtil.getConnection();
         
         for(int i=1; i<21; i++) {
            String sql = 
                  "insert into sboard(num, name, pass, subject, content,"
                  + " filename, re_ref, re_lev, re_seq, readcount, ip, reg_date)"
                  + "values (?,?,?,?,?,?,?,?,?,?,?,current_timestamp)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, i);
            pstmt.setString(2, "NAME("+random.nextInt(999999999)+")");
            pstmt.setString(3, "aa");
            pstmt.setString(4, "SUBJECT("+random.nextInt(999999999)+")");
            pstmt.setString(5, "CONTENT("+random.nextInt(999999999)+")\n"+
                  "content("+random.nextInt(999999999)+")\n"+
                  "content("+random.nextInt(999999999)+")\n"+
                  "content("+random.nextInt(999999999)+")\n"
                  );
            pstmt.setString(6, null);
            pstmt.setInt(7, i); // re_ref == num �ֱ��϶��� �� �׷� ��ȣ�� ����.
            pstmt.setInt(8, 0);   //�ֱ��� ������ lev 0 �̴�.
            pstmt.setInt(9, 0); //�� �׷�  �������� �������� ����, �ֻ�ܿ� �ֱ��� �´�.
            pstmt.setInt(10, 0); //��ȸ��
            pstmt.setString(11, null);
            
            pstmt.executeUpdate();
            pstmt.close();
            }
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         H2DBUtil.closeJDBC(con, pstmt, rs);
      }
         
   }
   
   
}
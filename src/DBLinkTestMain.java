import java.sql.*;

public class DBLinkTestMain {

  public static void main(String args[]) {
    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=uuid;integratedSecurity=true;";

    String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=uuid;user=hanzhonghao;password=hkhn5261";//sa身份连接

    // Declare the JDBC objects.
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection.
      System.out.println("con begin.");
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(url);
      System.out.println("database opetation begin!.");

      int num = 2000000;

      String NMAID = "NMDPTRIAL_dirce_richards_nuance_com20150904142852";
      String insertSql = "INSERT INTO UUIDTab1(UUID,NMAID) VALUES (?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(insertSql);
      for (int i = 0; i < num; i++) {
        String UUID = UUIDTest.getUUID();
        addData(preparedStatement,rs, con, UUID, NMAID);
      }
      preparedStatement.executeBatch();
      con.commit();
      queryData(stmt, rs, con);
      //      deleteALL(stmt,rs,con);
      //      queryData(stmt,rs,con);
    }

    // Handle any errors that may have occurred.
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception e) {
        }
      if (stmt != null)
        try {
          stmt.close();
        } catch (Exception e) {
        }
      if (con != null)
        try {
          con.close();
        } catch (Exception e) {
        }
    }
  }

  public static void addData(PreparedStatement preparedStatement,ResultSet rs, Connection con, String uuid, String NMAID) throws SQLException {

    /**
     * add value to sqlserver
     */

    preparedStatement.setString(1, uuid);
    preparedStatement.setString(2, NMAID);
    preparedStatement.addBatch();

  }

  public static void deleteALL(Statement stmt, ResultSet rs, Connection con) throws SQLException {
    String insertSql = "DELETE FROM UUIDTab1 where 1=1";
    stmt = con.createStatement();
    boolean isDelete = stmt.execute(insertSql);
    if (isDelete) {
      System.out.println("delete failed");
    } else {
      System.out.println("delete successful");
    }

  }

  public static void queryData(Statement stmt, ResultSet rs, Connection con) throws SQLException {
    // Create and execute an SQL statement that returns some data.
    String sql = "SELECT  * FROM UUIDTab1";
    stmt = con.createStatement();
    rs = stmt.executeQuery(sql);
    // Iterate through the data in the result set and display it.
    while (rs.next()) {
      System.out.println("UUID:" + rs.getString(1));
      System.out.println("NMAID:" + rs.getString(2));
      System.out.println("                                     ");
    }
  }
}

import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {
  public static void main(String[] args) {
    addDataIntoDB();
    queryData();
  }

  private static void addDataIntoDB() {
    // 创建一个线程池对象，控制要创建几个线程对象。
    ExecutorService pool = Executors.newFixedThreadPool(4);

    // 可以执行Runnable对象或者Callable对象代表的线程
    for (int i = 0; i < 4; i++) {
      pool.submit(new MyRunnable());
    }
    //结束线程池
    pool.shutdown();
  }

  private static void queryData() {
    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=uuid;integratedSecurity=true;";
    String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=uuid;user=hanzhonghao;password=hkhn5261";//sa身份连接
    // Declare the JDBC objects.
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection.
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(url);
      System.out.println("database query begin!.");
      DBLinkTestMain.queryData(stmt, rs, con);
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

}

class MyRunnable implements Runnable {
  @Override public void run() {

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

      int num = 125000;

//      String NMAID = "NMDPTRIAL_dirce_richards_nuance_com20150904142852";
      String NMAID = "ALIPH_NMDP_NMAID_20100916";
      String insertSql = "INSERT INTO UUIDTab1(UUID,NMAID) VALUES (?,?)";
      PreparedStatement preparedStatement = con.prepareStatement(insertSql);
      for (int i = 0; i < num; i++) {
        String UUID = UUIDTest.getUUID();
        DBLinkTestMain.addData(preparedStatement, rs, con, UUID, NMAID);
      }
      preparedStatement.executeBatch();
      con.commit();
      DBLinkTestMain.queryData(stmt, rs, con);
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
}
package com.hellobike.base.tunnel;

import java.sql.*;

public class TestSQLServer {
    private static Connection conn;
    private static Statement stmt;
//    private static String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=test_db";
    private static String url = "jdbc:sqlserver://localhost:1433;DatabaseName=test_db";
//    private static String classforname = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
    private static String classforname = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String uid = "sa";
    private static String pwd = "dev@123,";

    public static void main(String[] args) {
        try {
            Class.forName(classforname);
            if (conn == null || conn.isClosed()){
                conn = DriverManager.getConnection(url, uid, pwd);
            }

            ResultSet rs=conn.createStatement().executeQuery("select * from sys.databases");
            while (rs.next()){
                int cc=rs.getMetaData().getColumnCount();
                for(int i=1;i<=cc;i++){
                    System.out.print(rs.getString(i));
                    System.out.print("\t");
                }
                System.out.println("");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
//        return conn;
    }
}

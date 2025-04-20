package connectSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSQL {
        public static Connection con = null;
        private static ConnectSQL instance = new ConnectSQL();
        public static ConnectSQL getInstance(){
            return instance;
        }
        public void connect(){
            String url = "jdbc:sqlserver://localhost:1433;databaseName=QLV;encrypt=true;trustServerCertificate=true;";
            String user = "sa";
            String password = "sapassword";
            try{
                con = DriverManager.getConnection(url , user , password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    public  void disconnect(){
        if(con != null){
            try{
                con.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static Connection getConnection(){
        return con;
    }
}

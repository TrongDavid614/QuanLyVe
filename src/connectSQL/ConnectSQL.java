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
        try {
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            System.out.println("Kết nối cơ sở dữ liệu thất bại!");
            e.printStackTrace();
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class ConnectDB {

    static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String DB_URL="jdbc:sqlserver://localhost:1433;databaseName=AntiLazy";
    public static String AZURE_URL;
    public static String DATABASENAME;
    public static String USER="sa";
    public static String PASS="12345";
    public static String SMTP_SERVER="smtp.gmail.com";
    public static String SMTP_PORT="587";
    public static String EMAIL="karobest3@gmail.com";
    public static String EMAIL_PASS="KKkk0902";

    public ConnectDB() {
    }

    public static Connection connectSQLServer() throws ClassNotFoundException, SQLException {
        //Setting SQLServer JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your SQLServer JDBC Driver?");
            //e.printStackTrace();
            //throw e;
        }
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }
}

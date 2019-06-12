/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Commons.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author ASUS
 */
public class DAOLoginRegis {

    // Insert Emloyees
    public static void AddNewEmployee(InfoEmployee Emp) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String exm = "Insert into Employees(EmployeeID,EmployeeFirstName,EmployeeMidName,EmployeeLastName,PhoneNumber,"
                    + "Address,IdNumber,Sex,Birthday,Email,Active) values(?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pt = connection.prepareStatement(exm);
            pt.setString(1, Emp.getEmployee_ID());
            pt.setString(2, Emp.getFirst_Name());
            pt.setString(3, Emp.getMid_Name());
            pt.setString(4, Emp.getLast_Name());
            pt.setString(5, Emp.getPhone_No());
            pt.setString(6, Emp.getAddress());
            pt.setString(7, Emp.getId_number());
            pt.setBoolean(8, Emp.getSex());
            pt.setString(9, Emp.getBirthdate());
            pt.setString(10, Emp.getGmail());
            pt.setBoolean(11, Emp.getActive());
            pt.execute();
            pt.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAOLoginRegis.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
    }

    //Add Users
    public static void AddUser(InfoEmployee Emp) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String ex = "Insert into Users(EmployeeID,Username,Password,Active,Secret_Question,Secret_Answer,Check_Login,Check_Time) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setString(1, Emp.getEmployee_ID());
            pts.setString(2, Emp.getUserName());
            pts.setString(3, Emp.getPassWord());
            pts.setBoolean(4, Emp.getActive());
            pts.setString(5, Emp.getSerect_Question());
            pts.setString(6, Emp.getSerect_Answer());
            pts.setString(7, Emp.getCheck_Login());
            pts.setString(8, Emp.getCheck_Time());
            pts.execute();
            pts.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAOLoginRegis.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }

    }

    // Check Create Account Admin
    public static Boolean checkFirstLogin() {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String exp = "select count(*) Count from Users";
            PreparedStatement pt = connection.prepareStatement(exp);
            ResultSet rs;
            rs = pt.executeQuery();
            while (rs.next()) {
                if (!rs.getString("Count").equals("0")) {
                    return false;
                }
            }
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAOLoginRegis.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
        return true;
    }

    // Lấy ra thông tin của tài khoản đăng nhập
    public static InfoEmployee getListCheckLogin(String User) {
        InfoEmployee Emp = new InfoEmployee();
        try {
            Connection connection = ConnectDB.connectSQLServer();
            // Tạo đối tượng Statement.

            String sql = "select * from Users where Username = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, User);
            ResultSet rs = pt.executeQuery();
            if (!rs.next()) {
                pt.close();
                connection.close();
                return null;
            } else {
                Emp.setActive(rs.getBoolean("Active"));
                Emp.setPassWord(rs.getString("Password"));
                pt.close();
                connection.close();
                rs.close();
            }
        } catch (SQLException | ClassNotFoundException ex1) {
            Logger.getLogger(DAOLoginRegis.class
                    .getName()).log(Level.SEVERE, null, ex1);
        }
        return Emp;
    }
    //    check Invalid Mac

    public static boolean check_invalid(String MACAddress) throws ClassNotFoundException, SQLException {
        Connection connection = ConnectDB.connectSQLServer();
        String sql = "select * from CheckBlockMacAddress where MACAddress=?";
        PreparedStatement pp = connection.prepareStatement(sql);
        pp.setString(1, MACAddress);
        ResultSet rsp;
        rsp = pp.executeQuery();
        if (rsp.next()) {
            rsp.close();
            pp.close();
            connection.close();
            return true;
        }
        rsp.close();
        pp.close();
        connection.close();
        return false;
    }
//    checkMacAddress

    public static void check_MacAddress(String Time, String MACAddress, String user) {
        try {
            if (!check_invalid(MACAddress)) {
                Connection connection = ConnectDB.connectSQLServer();
                String sql = "Insert Into CheckBlockMacAddress(MACAddress,Times,Active) values(?,?,?)";
                PreparedStatement pp = connection.prepareStatement(sql);
                pp.setString(1, MACAddress);
                pp.setInt(2, 0);
                pp.setBoolean(3, true);
                pp.execute();
                connection.close();
                pp.close();
            }
            Connection connection = ConnectDB.connectSQLServer();
            String exp = "select Times from CheckBlockMacAddress where MACAddress = ?";
            PreparedStatement pt = connection.prepareStatement(exp);
            pt.setString(1, MACAddress);
            ResultSet rs;
            rs = pt.executeQuery();
            int Count = 0;
            while (rs.next()) {
                Count = rs.getInt("Times");
            }
            Count++;
            rs.close();
            pt.close();
            String ex = "UPDATE CheckBlockMacAddress SET Times = ? WHERE MACAddress = ?";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setInt(1, Count);
            pts.setString(2, MACAddress);
            pts.execute();
            pts.close();
            if (Count == 10) {
                String e = "UPDATE CheckBlockMacAddress SET Active = ? WHERE MACAddress = ?";
                PreparedStatement p = connection.prepareStatement(e);
                p.setInt(1, 0);
                p.setString(2, MACAddress);
                p.execute();
                p.close();
                DAOLoginRegis.setUserLogs_With_MAC(MACAddress, "MacAddress is Locked by USERNAME: " + user,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")), MACAddress);
                p.close();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Your device has been blocked from using this program !!!");
                alert.setContentText("Please contact the administrator for reuse !!!");
                alert.showAndWait();
                Platform.exit();
            }
            connection.close();
            pts.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Write to UserLogs
    public static void setUserLogs_With_MAC(String User, String Content, String Logtime, String macAddress) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String ex = "Insert Into UserLogs(UserName,LogContent,LogTime,Active,MACAddress) Values (?,?,?,?,?)";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setString(1, User);
            pts.setString(2, Content);
            pts.setString(3, Logtime);
            pts.setInt(4, 1);
            pts.setString(5, macAddress);
            pts.execute();
            pts.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Check time reset
    public static String check_Time(String User) {
        String Count = null;
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String exp = "select Check_Time from Users where UserName = ?";
            PreparedStatement pt = connection.prepareStatement(exp);
            pt.setString(1, User);
            ResultSet rs;
            rs = pt.executeQuery();
            while (rs.next()) {
                Count = rs.getString("Check_Time");
            }
            pt.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Count;
    }

    //reset check login 
    public static void reset_CheckLogin(String User, String Logtime) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String ex = "UPDATE Users SET Check_Login = ?,Check_Time = ? WHERE UserName = ?";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setInt(1, 0);
            pts.setString(2, Logtime);
            pts.setString(3, User);
            pts.execute();
            pts.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Xử lý gõ pass sai thì tăng biến check lên 1 nếu check = 5 block account

    public static void check_Login(String User, String Time) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String exp = "select Check_Login from Users where UserName = ?";
            PreparedStatement pt = connection.prepareStatement(exp);
            pt.setString(1, User);
            ResultSet rs;
            rs = pt.executeQuery();
            int Count = 0;
            while (rs.next()) {
                Count = rs.getInt("Check_Login");
            }
            Count++;

            pt.close();
            String ex = "UPDATE Users SET Check_Login = ?,Check_Time = ? WHERE UserName = ?";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setInt(1, Count);
            pts.setString(2, Time);
            pts.setString(3, User);
            pts.execute();
            if (Count == 7) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                String logtime;
                logtime = dateFormat.format(cal.getTime());
                String e = "UPDATE Users SET Active = ?,ReActive=0 WHERE UserName = ?";
                PreparedStatement p = connection.prepareStatement(e);
                p.setInt(1, 0);
                p.setString(2, User);
                p.execute();
                p.close();
                setUserLogs(User, "Account is Locked", logtime);
            }
            pts.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Insert to UserLogs
    public static void setUserLogs(String User, String Content, String Logtime) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String ex = "Insert Into UserLogs(UserName,LogContent,LogTime,Active) Values (?,?,?,?)";
            PreparedStatement pts = connection.prepareStatement(ex);
            pts.setString(1, User);
            pts.setString(2, Content);
            pts.setString(3, Logtime);
            pts.setInt(4, 1);
            pts.execute();
            pts.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObservableList<String> GetAllClassName() {
        ObservableList<String> list_name = FXCollections.observableArrayList();
        try {
            Connection connection = ConnectDB.connectSQLServer();
            String sql = "select NameClass from ClassName WHERE active = 1";
            PreparedStatement pt = connection.prepareStatement(sql);
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {
                list_name.add(rs.getString("NameClass"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list_name;
    }

    // Check Username Already exist
    public static Boolean CheckUsername(String Username) {
        try {
            Connection connection = ConnectDB.connectSQLServer();
            // Tạo đối tượng Statement.
            String sql = "select Username from Users where Username = ?";
            // Thực thi câu lệnh SQL trả về đối tượng ResultSet.
            PreparedStatement pt = connection.prepareStatement(sql);
            pt.setString(1, Username);
            ResultSet rs = pt.executeQuery();
            if (rs.next()) {
                pt.close();
                connection.close();
                rs.close();
                return true;
            }
            pt.close();
            connection.close();
            rs.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DAOLoginRegis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

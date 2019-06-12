/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import Commons.Email;
import Commons.MD5Encrypt;
import Models.DAOLoginRegis;
import Models.InfoEmployee;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class Login extends Application {

    @Override
    public void start(Stage stage) throws IOException {    
        if (DAOLoginRegis.checkFirstLogin()) {
            InfoEmployee emp = new InfoEmployee();
            emp.setEmployee_ID("EMP-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
            emp.setAddress("admin");
            emp.setBirthdate("1994-03-11");
            emp.setFirst_Name("admin");
            emp.setLast_Name("admin");
            emp.setMid_Name("admin");
            emp.setId_number("admin");
            emp.setPhone_No("admin");
            emp.setGmail("karobest3@gmail.com");
            emp.setSerect_Question("Which animal do you like best?");
            emp.setSerect_Answer("dog");
            emp.setSex(true);
            emp.setActive(true);
            emp.setUserName("admin");
            emp.setCheck_Login("0");
            emp.setCheck_Time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            String list_pass = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            String random_pass = "";
            Random rand = new Random();
            for (int i = 0; i < 8; i++) {
                int randomIndex = rand.nextInt(list_pass.length());
                random_pass = random_pass + list_pass.charAt(randomIndex);
            }
            MD5Encrypt m = new MD5Encrypt();
            String Password = m.hashPass(random_pass);
            emp.setPassWord(Password);
            DAOLoginRegis.AddNewEmployee(emp);
            DAOLoginRegis.AddUser(emp);
            String content = "Username: " + emp.getUserName() + ", Password: "+ random_pass;
            Email.send_Email_Without_Attach(emp.getGmail(), "Username and password", content);
        }
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
        stage.setTitle("Login");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(Boolean.FALSE);
       // stage.getIcons().add(new Image("/images/KAN Logo.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Commons.GetInetAddress;
import Commons.MD5Encrypt;
import Commons.Notification;
import Commons.StageLoader;
import Models.DAOLoginRegis;
import Models.InfoEmployee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private AnchorPane formLogin;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private HBox hBoxPassword;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private HBox hboxContent;
    @FXML
    private JFXButton btnLogin;
    private InfoEmployee Emp = new InfoEmployee();
    public static String User_Login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUserName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    hboxContent.getChildren().clear();
                    txtUserName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleLoginAction();
                }
            }
        });

        txtPassword.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    hboxContent.getChildren().clear();
                    txtPassword.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleLoginAction();
                }
            }
        });
        btnLogin.setOnAction((event) -> {
            handleLoginAction();
        });
    }

    @FXML
    private void handleLoginAction() {
        btnLogin.setDisable(true);
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Loading");
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        hboxContent.getChildren().clear();
                    }
                });
                loginAction();
                return null;
            }
        };
        loadOverview.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    btnLogin.setDisable(false);
                    stageLoader.closeStage();
                    stageLoader.stopTimeline();
                });
            }
        });

        new Thread(loadOverview).start();

    }

    @FXML
    private void handleForgetPass(MouseEvent event) {
    }

    public void loginAction() {
        if (txtUserName.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(txtUserName, hboxContent, "USER MUST NOT EMPTY !!!");
            });
        } else if (txtPassword.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notificationPassword(txtPassword, hboxContent, "PASSWORD MUST NOT EMPTY !!!");
            });
        } else {
            MD5Encrypt m = new MD5Encrypt();
            String hashPass = m.hashPass(txtPassword.getText());
            String user = txtUserName.getText();
            Emp = DAOLoginRegis.getListCheckLogin(txtUserName.getText());
            if (Emp == null) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        DAOLoginRegis.check_MacAddress(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                                GetInetAddress.getMacAddress(), txtUserName.getText());
                        Notification.notification(txtUserName, hboxContent, "USER OR PASSWORD WRONG !!!");
                    }
                });
            } else if (!Emp.getActive()) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Notification.notification(txtUserName, hboxContent, "ACCOUNT IS LOCKED !!!");
                    }
                });
            } else if (!hashPass.equals(Emp.getPassWord())) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        DAOLoginRegis.check_MacAddress(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")),
                                GetInetAddress.getMacAddress(), user);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar cal = Calendar.getInstance();
                        String logtime;
                        logtime = dateFormat.format(cal.getTime());
//                    Xử lý reset lại Check Login nếu qua 1 ngày mới
                        if (!DAOLoginRegis.check_Time(txtUserName.getText()).equals(logtime)) {
                            DAOLoginRegis.reset_CheckLogin(txtUserName.getText(), logtime);
                        }
                        Notification.notification(txtUserName, hboxContent, "USER OR PASSWORD WRONG!!!");
                        DAOLoginRegis.check_Login(user, logtime);
                    }
                });
            } else {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        User_Login = txtUserName.getText();
                        try {
                            Stage stage = (Stage) btnLogin.getScene().getWindow();
                            stage.close();
                            Stage stageEdit = new Stage();
                            stageEdit.resizableProperty().setValue(Boolean.FALSE);
                            Parent rootAdd = rootAdd = FXMLLoader.load(FXMLLoginController.this.getClass().getResource("/FXML/FXMLAccount.fxml"));
                            stageEdit.setTitle("Set Password");
                            Scene scene1;
                            scene1 = new Scene(rootAdd);
                            stageEdit.setScene(scene1);
                            stageEdit.show();
                        } catch (IOException ex) {
                            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        }
    }

    @FXML
    private void handleCreateAccount(MouseEvent event) throws IOException {
        Stage stageCreate = new Stage();
        stageCreate.initModality(Modality.APPLICATION_MODAL);
        // make its owner the existing window:
        stageCreate.resizableProperty().setValue(Boolean.FALSE);
        Parent rootAdd = FXMLLoader.load(getClass().getResource("/FXML/FXMLInfoEmployee.fxml"));
        Scene sceneCreate;
        sceneCreate = new Scene(rootAdd);
        stageCreate.setTitle("Create Account");
        //stageCreate.getIcons().add(new Image("/images/iconmanagement.png"));
        stageCreate.setScene(sceneCreate);
        stageCreate.show();
    }
}

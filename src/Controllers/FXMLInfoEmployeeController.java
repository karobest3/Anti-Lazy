/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import Commons.Email;
import Commons.FormatCalender;
import Commons.FormatName;
import Commons.MD5Encrypt;
import Commons.Notification;
import Commons.PatternValided;
import Commons.StageLoader;
import Models.DAOLoginRegis;
import Models.InfoEmployee;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class FXMLInfoEmployeeController implements Initializable {

    @FXML
    private AnchorPane anchorPaneInfoEmployee;
    @FXML
    private VBox vBox_Employee_Info;
    @FXML
    private HBox HboxHeader;
    @FXML
    private HBox hBox_Info_Parent;
    @FXML
    private VBox vBox_Info_Left;
    @FXML
    private JFXTextField Username;
    @FXML
    private JFXPasswordField Password;
    @FXML
    private JFXPasswordField PasswordConfirm;
    @FXML
    private JFXComboBox<String> boxQuestion;
    @FXML
    private JFXPasswordField Answer;
    @FXML
    private JFXPasswordField AnswerConfirm;
    @FXML
    private JFXDatePicker birthday;
    @FXML
    private VBox vBox_Info_Right;
    @FXML
    private JFXTextField FirstName;
    @FXML
    private JFXTextField MidName;
    @FXML
    private JFXTextField LastName;
    @FXML
    private JFXTextField Gmail;
    @FXML
    private JFXComboBox<String> BoxClassName;
    @FXML
    private JFXTextField Address;
    @FXML
    private JFXTextField IdNumber;
    @FXML
    private JFXTextField newPhone;
    @FXML
    private JFXRadioButton Male;
    @FXML
    private ToggleGroup Sex;
    @FXML
    private JFXRadioButton Female;
    @FXML
    private HBox HboxContent;
    @FXML
    private HBox Hboxbtn;
    @FXML
    private JFXButton btnInfo;
    @FXML
    private JFXButton btnCancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList list_question = FXCollections.observableArrayList();
        list_question.add("What is the first phone number you use?");
        list_question.add("What is your first girlfriend's name?");
        list_question.add("Which animal do you like best?");
        list_question.add("Which subject do you like best?");
        list_question.add("Which car company do you like best?");
        boxQuestion.setItems(list_question);
        BoxClassName.setItems(DAOLoginRegis.GetAllClassName());
        birthday.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.ofYearDay(LocalDate.now().getYear() - 10, LocalDate.now().getDayOfYear());
                setDisable(empty || date.compareTo(today) > 0);

            }
        });
        birthday.setValue(LocalDate.ofYearDay(LocalDate.now().getYear() - 16, LocalDate.now().getDayOfYear()));
        String pattern = "dd-MM-yyyy";
        FormatCalender.format(pattern, birthday);
        birthday.getEditor().setText("Date Of Birth");
        birthday.setPromptText(null);
        birthday.getStyleClass().add("jfx-date-picker-fix");

        Username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    Username.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        boxQuestion.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> {
                        System.out.println("Enter pressed");
                        handleInfoAction();
                    });
                }
            }
        });
        BoxClassName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> {
                        System.out.println("Enter pressed");
                        handleInfoAction();
                    });
                }
            }
        });
        birthday.valueProperty().addListener((obs, oldItem, newItem) -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    birthday.setStyle("-jfx-default-color: #6447cd;");
                    HboxContent.getChildren().clear();
                }
            });
        });
        birthday.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                HboxContent.getChildren().clear();
                if (event.getCode() == KeyCode.ENTER) {
                    Platform.runLater(() -> {
                        handleInfoAction();
                    });
                }
            }
        });
        Password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    Password.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        PasswordConfirm.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    PasswordConfirm.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        FirstName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    FirstName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        MidName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    MidName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        LastName.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    LastName.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        Gmail.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    Gmail.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        Answer.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    Answer.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });

        AnswerConfirm.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    AnswerConfirm.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        Address.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    Address.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        IdNumber.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    IdNumber.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        newPhone.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Platform.runLater(() -> {
                    newPhone.setStyle("-jfx-focus-color: -fx-primarycolor;-jfx-unfocus-color: -fx-primarycolor;");
                    HboxContent.getChildren().clear();
                });
                if (event.getCode() == KeyCode.ENTER) {
                    handleInfoAction();
                }
            }
        });
        btnInfo.setOnAction((event) -> {
            handleInfoAction();
        });
    }

    @FXML

    public void handleInfoAction() {
        btnInfo.setDisable(true);
        StageLoader stageLoader = new StageLoader();
        stageLoader.loadingIndicator("Loading");
        Task loadOverview = new Task() {
            @Override
            protected Object call() throws Exception {
                Platform.runLater(() -> {
                    HboxContent.getChildren().clear();
                });
                enter_Submit_Action();
                return null;
            }
        };
        loadOverview.setOnSucceeded(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("Finished");
                Platform.runLater(() -> {
                    btnInfo.setDisable(false);
                    stageLoader.closeStage();
                    stageLoader.stopTimeline();

                });
            }
        });
        new Thread(loadOverview).start();
    }

    @FXML
    private void Format_Show_Calender() {
        String pattern = "dd-MM-yyyy";
        FormatCalender.format(pattern, birthday);
        birthday.setPromptText("Date Of Birth");
        birthday.getStyleClass().remove("jfx-date-picker-fix");
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.hide();
    }

    public void enter_Submit_Action() {
        if (Username.getText() == null || Username.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(Username, HboxContent, "USERNAME MUST NOT EMPTY !!!");
            });

        } else if (Password.getText() == null || Password.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notificationPassword(Password, HboxContent, "PASSWORD MUST NOT EMPTY !!!");
            });

        } else if (PasswordConfirm.getText() == null || PasswordConfirm.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notificationPassword(PasswordConfirm, HboxContent, "PASSWORD CONFIRM MUST NOT EMPTY !!!");
            });

        } else if (boxQuestion.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("SECRET QUESTION MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                boxQuestion.getStyleClass().removeAll();
                boxQuestion.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                boxQuestion.requestFocus();
            });
        } else if (Answer.getText() == null || Answer.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notificationPassword(Answer, HboxContent, "ANSWER MUST NOT EMPTY !!!");
            });

        } else if (AnswerConfirm.getText() == null || AnswerConfirm.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notificationPassword(AnswerConfirm, HboxContent, "ANSWER CONFIRM MUST NOT EMPTY !!!");
            });
        } else if (birthday.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setAlignment(Pos.CENTER_LEFT);
                label.setText("BIRTHDAY MUST NOT EMPTY !!!");
                birthday.setStyle("-jfx-default-color: RED;");
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.setSpacing(10);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                birthday.requestFocus();
            });
        } else if ((FirstName.getText() == null || FirstName.getText().equals(""))) {
            Platform.runLater(() -> {
                Notification.notification(IdNumber, HboxContent, "FIRST NAME MUST NOT EMPTY !!!");
            });

        } else if ((LastName.getText() == null || LastName.getText().equals(""))) {
            Platform.runLater(() -> {
                Notification.notification(LastName, HboxContent, "LAST NAME MUST NOT EMPTY !!!");
            });

        } else if ((Gmail.getText() == null || Gmail.getText().equals(""))) {
            Platform.runLater(() -> {
                Notification.notification(Gmail, HboxContent, "EMAIL MUST NOT EMPTY !!!");
            });

        } else if (BoxClassName.getValue() == null) {
            Platform.runLater(() -> {
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CLOSE);
                icon.setSize("16");
                icon.setStyleClass("jfx-glyhp-icon");
                Label label = new Label();
                label.setStyle("-fx-text-fill: red; -fx-font-size : 11px;-fx-font-weight: bold;");
                label.setPrefSize(350, 35);
                label.setText("CLASS MUST NOT EMPTY !!!");
                label.setAlignment(Pos.CENTER_LEFT);
                BoxClassName.getStyleClass().removeAll();
                BoxClassName.getStyleClass().add("jfx-combo-box-fault");
                HboxContent.setSpacing(10);
                HboxContent.setAlignment(Pos.CENTER_LEFT);
                HboxContent.getChildren().clear();
                HboxContent.getChildren().add(icon);
                HboxContent.getChildren().add(label);
                BoxClassName.requestFocus();
            });

        } else if (Address.getText() == null || Address.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(Address, HboxContent, "ADDRESS NUMBER MUST NOT EMPTY !!!");
            });

        } else if (IdNumber.getText() == null || IdNumber.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(IdNumber, HboxContent, "ID NUMBER MUST NOT EMPTY !!!");
            });

        } else if (newPhone.getText() == null || newPhone.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(newPhone, HboxContent, "PHONE NUMBER MUST NOT EMPTY !!!");
            });

        } else if (!PatternValided.PatternID(Username.getText())) {
            Platform.runLater(() -> {
                Notification.notification(Username, HboxContent, "USERNAME IS INCORRECT !!!");
            });

        } else if (!PatternValided.PatternPassword(Password.getText())) {
            Notification.notificationPasswordAccount(Password, HboxContent, "PASSWORD INCORRECT (EXAM:Abc12345,...(8-20 CHARACTERS) !!!");

        } else if (!Password.getText().equals(PasswordConfirm.getText())) {
            Notification.notificationPasswordAccount(Password, HboxContent, "PASSWORD AND PASSWORD CONFIRM DID NOT MATCH !!!");

        } else if (!PatternValided.PatternAnswer(Answer.getText())) {
            Notification.notificationPasswordAccount(Answer, HboxContent, "ANSWER INCORRECT (ANSWER MUST HAVE 4-20 CHARACTER) !!!");

        } else if (!Answer.getText().equals(AnswerConfirm.getText())) {
            Notification.notificationPasswordAccount(Answer, HboxContent, "ANSWER AND ANSWER CONFIRM DID NOT MATCH !!!");

        } else if (!PatternValided.PatternName(FirstName.getText())) {
            Platform.runLater(() -> {
                Notification.notification(FirstName, HboxContent, "FIRSTNAME INVALID (Example: Nguyễn, Lê,...) !!!");
            });

        } else if (!PatternValided.PatternName(MidName.getText()) && !MidName.getText().equals("")) {
            Platform.runLater(() -> {
                Notification.notification(MidName, HboxContent, "MIDNAME INVALID (Example: Thị, Văn,...) !!!");
            });

        } else if (!PatternValided.PatternName(LastName.getText())) {
            Platform.runLater(() -> {
                Notification.notification(LastName, HboxContent, "LASTNAME INVALID (Example: Nguyễn, Trần,...) !!!");
            });

        } else if (!PatternValided.PatternEmail(Gmail.getText())) {
            Platform.runLater(() -> {
                Notification.notification(Gmail, HboxContent, "EMAIL INVALID !!!");
            });
        } else if (!PatternValided.PatternCMND(IdNumber.getText())) {
            Platform.runLater(() -> {
                Notification.notification(IdNumber, HboxContent, "ID NUMBER IS INCORRECT !!!");
            });

        } else if (!PatternValided.PatternPhoneNumber(newPhone.getText())) {
            Platform.runLater(() -> {
                Notification.notification(newPhone, HboxContent, "PHONE NUMBER IS INCORRECT !!!");
            });

        } else if (DAOLoginRegis.CheckUsername(Username.getText())) {
            Platform.runLater(() -> {
                Notification.notification(Username, HboxContent, "USERNAME ALREADY EXIST !!!");
            });
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String list_pass;
                    String random_pass = "";
                    list_pass = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
                    Random rand = new Random();
                    for (int i = 0; i < 8; i++) {
                        int randomIndex = rand.nextInt(list_pass.length());
                        random_pass = random_pass + list_pass.charAt(randomIndex);
                    }
                    Boolean Sex;
                    Sex = Male.selectedProperty().getValue();
                    InfoEmployee Emp = new InfoEmployee();
                    Emp.setEmployee_ID("EMP-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss-SSSSS")));
                    Emp.setFirst_Name(FormatName.format(FirstName.getText()));
                    Emp.setMid_Name(FormatName.format(MidName.getText()));
                    Emp.setLast_Name(FormatName.format(LastName.getText()));
                    Emp.setGmail(Gmail.getText());
                    Emp.setSex(Sex);
                    Emp.setSerect_Question(boxQuestion.getValue());
                    Emp.setClassName(BoxClassName.getValue());
                    Emp.setSerect_Answer(Answer.getText());
                    Emp.setUserName(Username.getText());
                    Emp.setAddress(Address.getText());
                    Emp.setPhone_No(newPhone.getText());
                    Emp.setId_number(IdNumber.getText());
                    Emp.setActive(false);
                    Emp.setCheck_Time(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    Emp.setCheck_Login("0");
                    MD5Encrypt m = new MD5Encrypt();
                    String Password = m.hashPass(random_pass);
                    Emp.setPassWord(Password);
                    Emp.setBirthdate(birthday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    DAOLoginRegis.AddNewEmployee(Emp);
                    DAOLoginRegis.AddUser(Emp);
                    String content = "Username: " + Emp.getUserName() + ", Password: " + random_pass;
                    Email.send_Email_Without_Attach(Emp.getGmail(), "Username and password", content);
                    Stage stage = (Stage) btnInfo.getScene().getWindow();
                    // do what you have to do
                    stage.close();
                }
            });
        }
    }
}

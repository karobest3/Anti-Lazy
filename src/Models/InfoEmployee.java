/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class InfoEmployee {

    private StringProperty EmployeeID;
    private StringProperty UserName;
    private StringProperty PassWord;
    private BooleanProperty Active;
    private StringProperty Serect_Question;
    private StringProperty Serect_Answer;
    private StringProperty First_Name;
    private StringProperty Mid_Name;
    private StringProperty Last_Name;
    private StringProperty Address;  
    private StringProperty PhoneNumber;
    private BooleanProperty Sex;
    private StringProperty Birthdate;
    private StringProperty Gmail;
    private StringProperty IDNumber;
    private StringProperty ClassName;
     private StringProperty Check_Time;
     private StringProperty Check_Login;
      public String getCheck_Time() {
        return Check_Time.get();
    }

    public void setCheck_Time(String Check_Time) {
        this.Check_Time = new SimpleStringProperty(Check_Time);
    }

     public String getCheck_Login() {
        return Check_Login.get();
    }

    public void setCheck_Login(String Check_Login) {
        this.Check_Login = new SimpleStringProperty(Check_Login);
    }

    public String getClassName() {
        return ClassName.get();
    }

    public void setClassName(String ClassName) {
        this.ClassName = new SimpleStringProperty(ClassName);
    }

    public String getGmail() {
        return Gmail.get();
    }

    public void setGmail(String Gmail) {
        this.Gmail = new SimpleStringProperty(Gmail);
    }

    public String getId_number() {
        return IDNumber.get();
    }

    public void setId_number(String IDNumber) {
        this.IDNumber = new SimpleStringProperty(IDNumber);
    }

    public String getAddress() {
        return Address.get();
    }

    public void setAddress(String Address) {
        this.Address = new SimpleStringProperty(Address);
    }

    public String getEmployee_ID() {
        return EmployeeID.get();
    }

    public void setEmployee_ID(String EmployeeID) {
        this.EmployeeID = new SimpleStringProperty(EmployeeID);
    }

    public String getUserName() {
        return UserName.get();
    }

    public void setUserName(String UserName) {
        this.UserName = new SimpleStringProperty(UserName);
    }

    public String getPassWord() {
        return PassWord.get();
    }

    public void setPassWord(String PassWord) {
        this.PassWord = new SimpleStringProperty(PassWord);
    }

    public Boolean getActive() {
        return Active.get();
    }

    public void setActive(Boolean Active) {
        this.Active = new SimpleBooleanProperty(Active);
    }

    public String getSerect_Question() {
        return Serect_Question.get();
    }

    public void setSerect_Question(String Serect_Question) {
        this.Serect_Question = new SimpleStringProperty(Serect_Question);
    }

    public String getSerect_Answer() {
        return Serect_Answer.get();
    }

    public void setSerect_Answer(String Serect_Answer) {
        this.Serect_Answer = new SimpleStringProperty(Serect_Answer);
    }

    public String getFirst_Name() {
        return First_Name.get();
    }

    public void setFirst_Name(String First_Name) {
        this.First_Name = new SimpleStringProperty(First_Name);
    }

        public String getMid_Name() {
        return Mid_Name.get();
    }

    public void setMid_Name(String Mid_Name) {
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
    }

    public String getLast_Name() {
        return Last_Name.get();
    }

    public void setLast_Name(String Last_Name) {
        this.Last_Name = new SimpleStringProperty(Last_Name);
    }

    

    public String getPhone_No() {
        return PhoneNumber.get();
    }

    public void setPhone_No(String PhoneNumber) {
        this.PhoneNumber = new SimpleStringProperty(PhoneNumber);
    }

    public Boolean getSex() {
        return Sex.get();
    }

    public void setSex(Boolean Sex) {
        this.Sex = new SimpleBooleanProperty(Sex);
    }

    public String getBirthdate() {
        return Birthdate.get();
    }

    public void setBirthdate(String Birthdate) {
        this.Birthdate = new SimpleStringProperty(Birthdate);
    }


    public InfoEmployee() {
    }

    public InfoEmployee(String EmployeeID, String UserName, String PassWord, String IDNumber, String Address, Boolean Active, String Serect_Question, String Serect_Answer, String First_Name, String Mid_Name, String Last_Name, String Work_Dept, String PhoneNumber,
           String Birthdate, String Gmail,String ClassName) {
        this.EmployeeID = new SimpleStringProperty(EmployeeID);
        this.UserName = new SimpleStringProperty(UserName);
        this.PassWord = new SimpleStringProperty(PassWord);
        this.IDNumber = new SimpleStringProperty(IDNumber);
        this.Address = new SimpleStringProperty(Address);
        this.Active = new SimpleBooleanProperty(Active);
        this.Serect_Question = new SimpleStringProperty(Serect_Question);
        this.Serect_Answer = new SimpleStringProperty(Serect_Answer);
        this.First_Name = new SimpleStringProperty(First_Name);
        this.Mid_Name = new SimpleStringProperty(Mid_Name);
        this.Last_Name = new SimpleStringProperty(Last_Name);
        this.PhoneNumber = new SimpleStringProperty(PhoneNumber);
        this.Birthdate = new SimpleStringProperty(Birthdate);
        this.Gmail = new SimpleStringProperty(Gmail);
        this.ClassName = new SimpleStringProperty(ClassName);
    }
}

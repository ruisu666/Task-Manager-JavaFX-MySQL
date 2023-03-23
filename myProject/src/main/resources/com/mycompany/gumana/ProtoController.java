/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gumana;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Luis
 */
public class ProtoController implements Initializable {


    @FXML
    private TextField lbl_username;
    @FXML
    private PasswordField lbl_password;
    @FXML
    private Button btnLogn;
    @FXML
    private Button btn_balik;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    //Database Tools
    private Connection conn = null;
    private PreparedStatement statement;
    private ResultSet result;
    
    public Connection connectDB(){
        
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost/myproject_db","root","");
            return conn;
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }
    @FXML
    public void btn_login (ActionEvent event){
        conn = connectDB();
        System.out.println("umabot hanggang dito");
        try{
            String sql = "SELECT * FROM tbl_accounts WHERE username = ? and password = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1,lbl_username.getText());
            statement.setString(2,lbl_password.getText());
            result = statement.executeQuery();
            
            if(result.next()){
                
                new Alert(Alert.AlertType.INFORMATION,"Succesfully logged in!").show();
                App.setCurrUser(new UserModel(result.getString("firstName")));
                App.setRoot("FXML_Dashboard");
                
            } else {
                new Alert(Alert.AlertType.ERROR,"Invalid Username or Password!").show();
                App.setRoot("FXML_Login");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void btn_balik(ActionEvent event) throws IOException {
        App.setRoot("FXML_FirstScreen");
    }


}
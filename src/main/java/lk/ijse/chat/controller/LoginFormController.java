package lk.ijse.chat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController {

    @FXML
    private JFXButton btnLogin;

    @FXML
    private TextField txtName;

    public static String name="";


    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        if (!txtName.getText().isEmpty()){
            name = txtName.getText();
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.setTitle(name+"'s Chat Room");
            stage.getIcons().add(new Image("img/Screenshot (160).png"));
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/clientForm.fxml"))));
            stage.setOnCloseRequest(windowEvent -> ClientFormController.leaveChat());
            stage.show();


        }else{
            new Alert(Alert.AlertType.WARNING,"Please enter the username").show();
        }
    }

    @FXML
    void txtNameOnAction(ActionEvent event) throws IOException {
        btnLoginOnAction(event);
    }

}

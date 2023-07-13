package lk.ijse.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Stage stage1 = new Stage();
        stage1.setTitle("Login Form");
        stage1.centerOnScreen();
        stage1.setScene(new Scene(root1));
        stage1.show();
    }
}

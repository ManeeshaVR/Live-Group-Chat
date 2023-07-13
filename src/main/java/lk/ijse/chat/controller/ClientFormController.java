package lk.ijse.chat.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {

    @FXML
    private Label lblName;

    @FXML
    private JFXButton btnImoji;

    @FXML
    private JFXButton btnPhoto;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextField txtMsg;

    @FXML
    private VBox vBox;

    @FXML
    private AnchorPane emojiPane;

    private BufferedReader bufferedReader;

    private static PrintWriter writer;

    private static String username;

    private File file;

    private String finalName;

    private ImageView clickedImage;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username = LoginFormController.name;
        lblName.setText(username);

        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",4002);

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(),true);

                writer.println("joi"+username+"~joining");

                while (true){
                    String receive = bufferedReader.readLine();
                    String[] split = receive.split("~");
                    String name = split[0];
                    String message = split[1];

                    String firstChars = "";
                    if (name.length() > 3) {
                        firstChars = name.substring(0, 3);
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        String[] imgs = name.split("img");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("joi")){
                        String[] imgs = name.split("joi");
                        finalName = imgs[1];
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        String[] imgs = name.split("lef");
                        finalName = imgs[1];
                    }
                    if (firstChars.equalsIgnoreCase("img")){
                        if (finalName.equalsIgnoreCase(username)){

                            File receiveFile = new File(message);
                            Image image = new Image(receiveFile.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                            });

                            Text text = new Text(" Me");
                            text.getStyleClass().add("send-text");

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setAlignment(Pos.CENTER_LEFT);
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            File receives = new File(message);
                            Image image = new Image(receives.toURI().toString());
                            ImageView imageView = new ImageView(image);
                            imageView.setFitHeight(170);
                            imageView.setFitWidth(200);
                            imageView.setOnMouseClicked(mouseEvent -> {
                                clickedImage.setImage(imageView.getImage());
                            });

                            Text text = new Text(" "+finalName);
                            text.getStyleClass().add("receive-text");

                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(text, imageView);

                            HBox hBox = new HBox(10);
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxHeight(190);
                            hBox.setMaxWidth(220);
                            hBox.getChildren().add(vbox);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("joi")) {
                        if (finalName.equalsIgnoreCase(username)){
                            Label text = new Label("You have joined the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else{
                            Label text = new Label(finalName+" has joined the chat");
                            text.getStyleClass().add("join-text");
                            HBox hBox = new HBox();
                            hBox.getChildren().add(text);
                            hBox.setAlignment(Pos.CENTER);

                            Platform.runLater(() -> {
                                vBox.getChildren().add(hBox);

                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }else if(firstChars.equalsIgnoreCase("lef")){
                        Label text = new Label(finalName+" has left the chat");
                        text.getStyleClass().add("left-text");
                        HBox hBox = new HBox();
                        hBox.getChildren().add(text);
                        hBox.setAlignment(Pos.CENTER);

                        Platform.runLater(() -> {
                            vBox.getChildren().add(hBox);

                            HBox hBox1 = new HBox();
                            hBox1.setPadding(new Insets(5, 5, 5, 10));
                            vBox.getChildren().add(hBox1);
                        });
                    } else{
                        if(name.equalsIgnoreCase(username)){
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: #000000");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(150);

                            Text nameText = new Text("~ Me");
                            nameText.getStyleClass().add("send-text");
                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(nameText, tempFlow);

                            HBox hBox = new HBox(12);
                            hBox.getStyleClass().add("send-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);
                            StackPane stackPane = new StackPane(hBox);
                            stackPane.setAlignment(Pos.CENTER_RIGHT);
                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(stackPane);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }else {
                            TextFlow tempFlow = new TextFlow();
                            Text text = new Text(message);
                            text.setStyle("-fx-fill: #000000");
                            text.setWrappingWidth(200);
                            tempFlow.getChildren().add(text);
                            tempFlow.setMaxWidth(150);

                            Text nameText = new Text("~ "+name);
                            nameText.getStyleClass().add("receive-text");
                            VBox vbox = new VBox(10);
                            vbox.getChildren().addAll(nameText, tempFlow);

                            HBox hBox = new HBox();
                            hBox.getStyleClass().add("receive-box");
                            hBox.setMaxWidth(220);
                            hBox.setMaxHeight(50);
                            hBox.getChildren().add(vbox);

                            Platform.runLater(() -> {
                                vBox.getChildren().addAll(hBox);
                                scrollPane.layout();
                                scrollPane.setVvalue(2.0);

                                //adding space between messages
                                HBox hBox1 = new HBox();
                                hBox1.setPadding(new Insets(5, 5, 5, 10));
                                vBox.getChildren().add(hBox1);
                            });
                        }
                    }
                    file = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        if (!txtMsg.getText().isEmpty()){
            if (file != null){
                writer.println("img"+username+"~"+file.getPath());
            }else {
                writer.println(username + "~" + txtMsg.getText());
            }
            txtMsg.setEditable(true);
            txtMsg.clear();
        }
    }

    @FXML
    void txtMsgOnActon(ActionEvent event) throws IOException {
        btnSendOnAction(event);
    }

    @FXML
    void btnImojiOnAction(ActionEvent event) {
        emojiPane.setVisible(!emojiPane.isVisible());
    }

    @FXML
    void btnPhotoOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the image");
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        file = fileChooser.showOpenDialog(txtMsg.getScene().getWindow());
        if (file != null){
            txtMsg.setText("1 image selected");
            txtMsg.setEditable(false);
        }
    }

    public static void leaveChat(){
        writer.println("lef"+username + "~leave");
    }

    @FXML
    void grinningFaceEmojiOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE00");
        emojiPane.setVisible(false);
    }

    @FXML
    void grinningSquintingOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE06");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithOpenHandsOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83E\uDD17");
        emojiPane.setVisible(false);
    }

    @FXML
    void grinningFaceWithSweatOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE05");
        emojiPane.setVisible(false);
    }

    @FXML
    void faceWithTearsOfJoyOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE02");
        emojiPane.setVisible(false);
    }

    @FXML
    void cryingFaceOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE22");
        emojiPane.setVisible(false);
    }

    @FXML
    void sunglassesFaceOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE0E");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithHeartEyesOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE0D");
        emojiPane.setVisible(false);
    }

    @FXML
    void smilingFaceWithHornsOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDE08");
        emojiPane.setVisible(false);
    }

    @FXML
    void thumbsUpOnAction(MouseEvent event) {
        txtMsg.appendText("\uD83D\uDC4D");
        emojiPane.setVisible(false);
    }

}

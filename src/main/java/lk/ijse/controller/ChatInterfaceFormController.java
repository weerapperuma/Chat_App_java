package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import lk.ijse.util.Navigation;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ChatInterfaceFormController implements Initializable {

    @FXML
    private TextField txtSendMsg;

    @FXML
    private VBox vboxChatLoad;
    @FXML
    private Pane chatPane;
    @FXML
    private Pane emojiPane;
    @FXML
    private VBox emojiBox;
    public VBox imageBox;

    @FXML
    private ScrollPane emojiScrollPane;


    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket clientSocket;
    String message="";
    String unicode2;
    private String filePath;

    public static VBox mainVbox;
    @FXML
    void btnAddEmojiOnAction(ActionEvent event) throws IOException {
        emojiPane.setVisible(true);
        //createSendMsg();
    }

    @FXML
    void btnAddImageOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            filePath = selectedFile.getAbsolutePath();
            dataOutputStream.writeUTF(UserLogingChekFormController.name);
        }


        dataOutputStream.writeUTF("image"+ filePath);
        try {


            File inputFile = new File(filePath);
            Image image = new Image(inputFile.toURI().toString());
            ImageView imageView = new ImageView(image);

            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            imageView.setOnMouseClicked(event1 -> {
                // Open the image when clicked
                try {
                    File file = new File(filePath);
                    Desktop.getDesktop().open(file);
                } catch (IOException e) {
                    e.printStackTrace();    }
            });
            TextFlow textFlow = new TextFlow();
            textFlow.getChildren().add(imageView);
            textFlow.setStyle("-fx-background-color:  #005C4B; -fx-background-radius: 10 10 0 10");
            HBox vBox = new HBox(10);
            vBox.setAlignment(Pos.BOTTOM_RIGHT);



            vBox.getChildren().add(textFlow);
            imageBox.getChildren().add(vBox);

        }catch (Exception e){}
        dataOutputStream.flush();
    }

    @FXML
    void txtSendMsgOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(UserLogingChekFormController.name);
        dataOutputStream.writeUTF(txtSendMsg.getText());
        createSendMsg(txtSendMsg.getText());
        //txtSendMsg.clear();
        dataOutputStream.flush();
    }

    private void createInboxMsg(String message) throws IOException {
        FXMLLoader loader=new FXMLLoader(ChatInterfaceFormController.class.getResource("/view/RecivedMsgLoadPane.fxml"));
        Parent root=loader.load();
        ReceivedMsgLoadPaneController controller1=loader.getController();

        controller1.setData(message);

        mainVbox.getChildren().add(root);
    }

    private void createSendMsg(String text) throws IOException {
        FXMLLoader loader=new FXMLLoader(ChatInterfaceFormController.class.getResource("/view/SendMsgLoadPane.fxml"));
        Parent root=loader.load();
        SendMsgLoadPaneController controller2=loader.getController();

        controller2.setData(text);

        mainVbox.getChildren().add(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainVbox=vboxChatLoad;
        setEmojis();
        emojis();

        emojiPane.setVisible(false);

        new Thread(()->{
            try {
                clientSocket=new Socket("localhost",3008);
                dataInputStream=new DataInputStream(clientSocket.getInputStream());
                dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());

                while (!message.equals("end")){
                    message=dataInputStream.readUTF();
                    Platform.runLater(()->{
                        try {
                            String trimmedMessage=message.substring(message.indexOf(":")+1).trim();
                            if(!txtSendMsg.getText().equals(trimmedMessage)){
                                createInboxMsg(message);
                                txtSendMsg.clear();
                            }txtSendMsg.clear();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void emojis() {
        String emoji = " ";

        VBox vBox = new VBox(10);
        Text text = new Text(emoji);
        text.setStyle("-fx-font-size: 50px; -fx-fill: #FAD22D; -fx-border-width: 10");

        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.BOTTOM_RIGHT);
        imageBox.setAlignment(Pos.BOTTOM_RIGHT);
        imageBox.getChildren().add(vBox);
    }

    private void setEmojis() {
        List<String> emojiUnicodes = Arrays.asList(

                "\u263A", "\uD83D\uDE00", "\uD83D\uDE01", "\uD83D\uDE02", "\uD83D\uDE03",
                "\uD83D\uDE04", "\uD83D\uDE05", "\uD83D\uDE06", "\uD83D\uDE07", "\uD83D\uDE08",
                "\uD83D\uDE09", "\uD83D\uDE0A", "\uD83D\uDE0B", "\uD83D\uDE0C", "\uD83D\uDE0D",
                "\uD83D\uDE0E", "\uD83D\uDE0F", "\uD83D\uDE10", "\uD83D\uDE11", "\uD83D\uDE12",
                "\uD83D\uDE13", "\uD83D\uDE14", "\uD83D\uDE15", "\uD83D\uDE16", "\uD83D\uDE17",
                "\uD83D\uDE18", "\uD83D\uDE19", "\uD83D\uDE1A", "\uD83D\uDE1B", "\uD83D\uDE1C",
                "\uD83D\uDE1D", "\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE20", "\uD83D\uDE21",
                "\uD83D\uDE22", "\uD83D\uDE23", "\uD83D\uDE24", "\uD83D\uDE25", "\uD83D\uDE26",
                "\uD83D\uDE27", "\uD83D\uDE28", "\uD83D\uDE29", "\uD83D\uDE2A", "\uD83D\uDE2B",
                "\uD83D\uDE2C", "\uD83D\uDE2D", "\uD83D\uDE2E", "\uD83D\uDE2F", "\uD83D\uDE30",
                "\uD83D\uDE31", "\uD83D\uDE32", "\uD83D\uDE33", "\uD83D\uDE34", "\uD83D\uDE35",
                "\uD83D\uDE36", "\uD83D\uDE37", "\uD83D\uDE38", "\uD83D\uDE39", "\uD83D\uDE3A",
                "\uD83D\uDE3B", "\uD83D\uDE3C", "\uD83D\uDE3D", "\uD83D\uDE3E", "\uD83D\uDE3F",
                "\uD83D\uDE40", "\uD83D\uDE41", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE44",
                "\uD83D\uDE45", "\uD83D\uDE46", "\uD83D\uDE47", "\uD83D\uDE48", "\uD83D\uDE49",
                "\uD83D\uDE4A", "\uD83D\uDE4B", "\uD83D\uDE4C", "\uD83D\uDE4D", "\uD83D\uDE4E",
                "\uD83D\uDE4F",

                "\uD83C\uDF30", "\uD83C\uDF31", "\uD83C\uDF32", "\uD83C\uDF33", "\uD83C\uDF34",
                "\uD83C\uDF35", "\uD83C\uDF36", "\uD83C\uDF37", "\uD83C\uDF38", "\uD83C\uDF39",
                "\uD83C\uDF3A", "\uD83C\uDF3B", "\uD83C\uDF3C", "\uD83C\uDF3D", "\uD83C\uDF3E",
                "\uD83C\uDF3F", "\uD83C\uDF40", "\uD83C\uDF41", "\uD83C\uDF42", "\uD83C\uDF43",
                "\uD83C\uDF44", "\uD83C\uDF45", "\uD83C\uDF46", "\uD83C\uDF47", "\uD83C\uDF48",
                "\uD83C\uDF49", "\uD83C\uDF4A", "\uD83C\uDF4B", "\uD83C\uDF4C", "\uD83C\uDF4D",
                "\uD83C\uDF4E", "\uD83C\uDF4F", "\uD83C\uDF50", "\uD83C\uDF51", "\uD83C\uDF52",
                "\uD83C\uDF53", "\uD83C\uDF54", "\uD83C\uDF55", "\uD83C\uDF56", "\uD83C\uDF57",
                "\uD83C\uDF58", "\uD83C\uDF59", "\uD83C\uDF5A", "\uD83C\uDF5B", "\uD83C\uDF5C",
                "\uD83C\uDF5D", "\uD83C\uDF5E", "\uD83C\uDF5F", "\uD83C\uDF60", "\uD83C\uDF61",
                "\uD83C\uDF62", "\uD83C\uDF63", "\uD83C\uDF64", "\uD83C\uDF65", "\uD83C\uDF66",
                "\uD83C\uDF67", "\uD83C\uDF68", "\uD83C\uDF69", "\uD83C\uDF6A", "\uD83C\uDF6B",
                "\uD83C\uDF6C", "\uD83C\uDF6D", "\uD83C\uDF6E", "\uD83C\uDF6F", "\uD83C\uDF70",
                "\uD83C\uDF71", "\uD83C\uDF72", "\uD83C\uDF73", "\uD83C\uDF74", "\uD83C\uDF75",
                "\uD83C\uDF76", "\uD83C\uDF77", "\uD83C\uDF78", "\uD83C\uDF79", "\uD83C\uDF7A",
                "\uD83C\uDF7B", "\uD83C\uDF7C", "\uD83C\uDF7D", "\uD83C\uDF7E", "\uD83C\uDF7F",

                "\uD83D\uDC2C", "\uD83D\uDC2D", "\uD83D\uDC2E", "\uD83D\uDC2F", "\uD83D\uDC30",
                "\uD83D\uDC31", "\uD83D\uDC32", "\uD83D\uDC33", "\uD83D\uDC34", "\uD83D\uDC35",
                "\uD83D\uDC36", "\uD83D\uDC37", "\uD83D\uDC38", "\uD83D\uDC39", "\uD83D\uDC3A",
                "\uD83D\uDC3B", "\uD83D\uDC3C", "\uD83D\uDC3D", "\uD83D\uDC3E", "\uD83D\uDC3F",
                "\uD83D\uDC40", "\uD83D\uDC41", "\uD83D\uDC42", "\uD83D\uDC43", "\uD83D\uDC44",
                "\uD83D\uDC45", "\uD83D\uDC46", "\uD83D\uDC47", "\uD83D\uDC48", "\uD83D\uDC49",
                "\uD83D\uDC4A", "\uD83D\uDC4B", "\uD83D\uDC4C", "\uD83D\uDC4D", "\uD83D\uDC4E",
                "\uD83D\uDC4F", "\uD83D\uDC50", "\uD83D\uDC51", "\uD83D\uDC52", "\uD83D\uDC53",
                "\uD83D\uDC54", "\uD83D\uDC55", "\uD83D\uDC56", "\uD83D\uDC57", "\uD83D\uDC58",
                "\uD83D\uDC59", "\uD83D\uDC5A", "\uD83D\uDC5B", "\uD83D\uDC5C", "\uD83D\uDC5D",
                "\uD83D\uDC5E", "\uD83D\uDC5F", "\uD83D\uDC60", "\uD83D\uDC61", "\uD83D\uDC62",
                "\uD83D\uDC63", "\uD83D\uDC64", "\uD83D\uDC65", "\uD83D\uDC66", "\uD83D\uDC67",
                "\uD83D\uDC68", "\uD83D\uDC69", "\uD83D\uDC6A", "\uD83D\uDC6B", "\uD83D\uDC6C",
                "\uD83D\uDC6D", "\uD83D\uDC6E", "\uD83D\uDC6F", "\uD83D\uDC70", "\uD83D\uDC71",
                "\uD83D\uDC72", "\uD83D\uDC73", "\uD83D\uDC74", "\uD83D\uDC75", "\uD83D\uDC76",
                "\uD83D\uDC77", "\uD83D\uDC78", "\uD83D\uDC79", "\uD83D\uDC7A", "\uD83D\uDC7B",
                "\uD83D\uDC7C", "\uD83D\uDC7D", "\uD83D\uDC7E", "\uD83D\uDC7F"

        );

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        int columns = 6;
        int rows = (int) Math.ceil(emojiUnicodes.size() / (double) columns);

        for (int row = 0; row < rows; row++) {
            HBox hbox = new HBox();
            hbox.setSpacing(30);

            for (int col = 0; col < columns; col++) {
                int index = row * columns + col;
                if (index < emojiUnicodes.size()) {
                    String unicode = emojiUnicodes.get(index);
                    unicode2=unicode;

                    Text emojiText = new Text(unicode);
                    hbox.getChildren().add(emojiText);
                    hbox.setStyle("-fx-font-size: 24px; -fx-fill: black; -fx-border-width: 10");
                    emojiText.setOnMouseClicked(event -> {
                        try {
                            handleEmojiSelection(unicode);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
            vBox.getChildren().add(hbox);

        }

        emojiBox.getChildren().add(vBox);
        emojiBox.setVisible(true);
    }
    private void handleEmojiSelection(String unicode) throws IOException {
        Text emojiText = new Text(unicode);
        emojiText.setStyle("-fx-font-size: 50px; -fx-fill: #FAD22D; -fx-border-width: 10");

        TextFlow textFlow = new TextFlow();
        textFlow.getChildren().add(emojiText);
        textFlow.setStyle("-fx-background-color:  #005C4B; -fx-background-radius: 10 10 0 10");
        textFlow.setPadding(new Insets(2,10,2,10));
        HBox vBox= new HBox(10);
        vBox.setAlignment(Pos.BOTTOM_RIGHT);
        dataOutputStream.writeUTF(UserLogingChekFormController.name);
        dataOutputStream.writeUTF(unicode);



        vBox.getChildren().add(textFlow);
        emojiPane.setVisible(false);
        createSendMsg(unicode);


        imageBox.getChildren().add(vBox);

        dataOutputStream.flush();
    }


}

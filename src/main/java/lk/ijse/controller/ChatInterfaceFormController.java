package lk.ijse.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatInterfaceFormController {

    @FXML
    private TextField txtSendMsg;

    @FXML
    private VBox vboxChatLoad;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket clientSocket;
    String message="";

    public static VBox mainVbox;
    @FXML
    void btnAddEmojiOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddImageOnAction(ActionEvent event) {

    }

    @FXML
    void txtSendMsgOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(UserLogingChekFormController.name);
        dataOutputStream.writeUTF(txtSendMsg.getText());
        createSendMsg(txtSendMsg.getText());
        //txtSendMsg.clear();
        dataOutputStream.flush();
    }



    public void initialize(){
        mainVbox=vboxChatLoad;
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

}

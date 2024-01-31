package lk.ijse.controller;

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

    @FXML
    void btnAddEmojiOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddImageOnAction(ActionEvent event) {

    }

    @FXML
    void txtSendMsgOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtSendMsg.getText());
        txtSendMsg.clear();
        dataOutputStream.flush();
    }

    public void initialize(){
        new Thread(()->{
            try {
                clientSocket=new Socket("localhost",3008);
                dataInputStream=new DataInputStream(clientSocket.getInputStream());
                dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());

                while (!message.equals("end")){
                    dataInputStream.readUTF();
                    createInboxMsg(message);
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

        vboxChatLoad.getChildren().add(root);
    }

}

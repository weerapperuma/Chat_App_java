package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientFormController {

    @FXML
    private TextArea txtAreaClient;

    @FXML
    private TextField txtSendMessage;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket clientSocket;
    String message="";

    public void initialize(){
        new Thread(()->{
            try {
                clientSocket=new Socket("localhost",3008);

                dataInputStream=new DataInputStream(clientSocket.getInputStream());
                dataOutputStream=new DataOutputStream(clientSocket.getOutputStream());

                while (!message.equals("exit")){
                    message=dataInputStream.readUTF();
                    txtAreaClient.appendText("\nServer :"+message);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtSendMessage.getText().trim());
        txtSendMessage.clear();
        dataOutputStream.flush();
    }

}

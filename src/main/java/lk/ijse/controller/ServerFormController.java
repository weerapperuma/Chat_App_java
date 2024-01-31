package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {

    @FXML
    private TextArea txtAreaServer;

    @FXML
    private TextField txtSendMessage;

    ServerSocket serverSocket;
    Socket localSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";


    public void initialize(){
        new Thread(()->{
            try {
                serverSocket=new ServerSocket(3008);
                txtAreaServer.appendText("Server Started Successfully");

                localSocket=serverSocket.accept();
                txtAreaServer.appendText("\nClient Connected");

                dataInputStream = new DataInputStream(localSocket.getInputStream());
                dataOutputStream=new DataOutputStream(localSocket.getOutputStream());

                while (!message.equals("end")){
                    message=dataInputStream.readUTF();
                    txtAreaServer.appendText("\nClient message :"+message);
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

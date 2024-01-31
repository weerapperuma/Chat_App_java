package lk.ijse;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import lk.ijse.controller.ChatInterfaceFormController;
import lk.ijse.controller.ReceivedMsgLoadPaneController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class MultiClientServer implements Runnable{
    Socket localSocket;
    List<MultiClientServer> multiClientServers;
    String message="";

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    public MultiClientServer(Socket localSocket, List<MultiClientServer> multiClientServers) throws IOException {
        this.localSocket=localSocket;
        this.multiClientServers=multiClientServers;
        this.dataInputStream=new DataInputStream(localSocket.getInputStream());
        this.dataOutputStream=new DataOutputStream(localSocket.getOutputStream());
    }

    @Override
    public void run() {
        System.out.println("client connected");
        while (!message.equals("end")){
            try {
                message=dataInputStream.readUTF();
                System.out.println("Server : "+message);
                Platform.runLater(() -> {
                    try {
                        System.out.println("tyr ad badhd");
                        createInboxMsg(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void createInboxMsg(String message) throws IOException {
        try {
            System.out.println("Inside create inbox");
                FXMLLoader loader = new FXMLLoader(ChatInterfaceFormController.class.getResource("/view/RecivedMsgLoadPane.fxml"));
                Parent root = loader.load();
                ReceivedMsgLoadPaneController controller1 = loader.getController();

                controller1.setData(message);

                ChatInterfaceFormController.mainVbox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
    }

}

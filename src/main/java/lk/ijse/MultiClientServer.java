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
    private String name;

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
                name=dataInputStream.readUTF();
                message=dataInputStream.readUTF();
                System.out.println("Server : "+message);

                for (MultiClientServer multiClientServer:multiClientServers){
                    multiClientServer.dataOutputStream.writeUTF(name+": "+message);
                    dataOutputStream.flush();
                }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}

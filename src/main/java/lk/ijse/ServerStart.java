package lk.ijse;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerStart extends Application {
    private static ServerSocket serverSocket;
    private static Socket localSocket;
    private static List<MultiClientServer> multiClientServers=new ArrayList<>();



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            serverSocket=new ServerSocket(3008);
            System.out.println("Server Started Successfully");

            while (true){
                localSocket=serverSocket.accept();

                MultiClientServer multiClientServer=new MultiClientServer(localSocket,multiClientServers);
                Thread newClient = new Thread(multiClientServer);
                multiClientServers.add(multiClientServer);
                newClient.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

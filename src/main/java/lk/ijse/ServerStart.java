package lk.ijse;

import lk.ijse.controller.ServerFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerStart{
    private static ServerSocket serverSocket;
    private static Socket localSocket;
    private static List<MultiClientServer> multiClientServers=new ArrayList<>();



    public static void main(String[] args) {
        try {
            serverSocket=new ServerSocket(3008);
            System.out.println("Server Started Successfully");

            while (true){
                localSocket=serverSocket.accept();

                MultiClientServer multiClientServer=new MultiClientServer(localSocket,multiClientServers);
                Thread newClient = new Thread((Runnable) multiClientServer);
                multiClientServers.add(multiClientServer);
                newClient.start();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

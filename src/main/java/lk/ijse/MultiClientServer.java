package lk.ijse;

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
        while (!message.equals("end")){
            try {
                System.out.println("client connected");
                message=dataInputStream.readUTF();
                System.out.println(message);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

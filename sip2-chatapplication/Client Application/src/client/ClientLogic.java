package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientLogic {
    private Socket clientSocket;
    private ObjectOutputStream clientWriter;
    private String username;

    public ClientLogic(Socket clientSocket, String username){
        try {
            this.clientSocket = clientSocket;
            clientWriter = new ObjectOutputStream(clientSocket.getOutputStream());
            this.username = username;
        }catch(IOException IOe){
            IOe.getMessage();
        }
    }

    //sends a message to the server
    public void send(String message) throws IOException{
        clientWriter.writeObject(username + ": " + message);
    }
}

package threads;

import server.Account;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RegisterThread extends AppThread implements Runnable{

    public RegisterThread(int port, String hostname){
        super(port, hostname);
    }

    public void run(){
        try {
            InetAddress IPaddr = InetAddress.getByName(hostname);
            //start new account server to listen for incoming connections
            serverSocket = new ServerSocket(port, 50, IPaddr);
            connected = true;
            while(connected){
                Socket socket = serverSocket.accept();
                FileWriter writer = new FileWriter(accountFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //reads objects sent over the Object Input Stream
                Account myAccount = (Account) ois.readObject();
                bufferedWriter.write( myAccount.getUserName() + " " + myAccount.getPassword());
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        }catch(IOException IOe){
            System.out.println("IOException: " + IOe.getMessage());
        }catch(ClassNotFoundException CNFe){
            System.out.println("Class not found exception: " + CNFe.getMessage());
        }
    }
}

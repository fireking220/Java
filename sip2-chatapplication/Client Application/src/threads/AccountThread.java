package threads;

import server.Account;
import server.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class AccountThread extends AppThread implements Runnable{
    private Server serverRef;

    public AccountThread(int port, String hostname, Server serverRef){
        super(port,hostname);
        this.serverRef = serverRef;
    }

    //this thread checks to see if the account associated with the username and password exists
    public void run(){
        try {
            InetAddress IPaddr = InetAddress.getByName(hostname);
            //start new account server to listen for incoming connections
            serverSocket = new ServerSocket(port, 50, IPaddr);
            connected = true;
            //loops endlessly as long as server is up
            while(connected){
                Socket socket = serverSocket.accept();
                //listeners for objects coming in on this socket
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream((socket.getOutputStream()));
                //reads objects sent over the Object Input Stream
                Account myAccount = (Account) ois.readObject();
                //open up Account file
                FileReader fileReader = new FileReader(accountFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                //line to be read from bufferedReader
                String line;
                boolean match = false;
                //try and find a match for our given account
                //grabs the first line from the accounts file, splits it up into different strings
                //and then compares each accountInformation string with the username and password in myAccount
                while((line = bufferedReader.readLine()) != null) {
                    String[] accountInformation = line.split(" ", 2);
                    //if currently not online
                        //if username and password match
                    if (accountInformation[0].equals(myAccount.getUserName())) {
                        if (accountInformation[1].equals(myAccount.getPassword())) {
                            if(!serverRef.checkOnline(accountInformation[0])) {
                                System.out.println("Match found!");
                                match = true;
                                break;
                            }
                        }
                    }
                }
                //if match was found, write true back to the client login
                if(match){
                    oos.writeObject(true);
                }
                //if not, write back false instead
                else {
                    oos.writeObject(false);
                }
                bufferedReader.close();
            }
        }catch(IOException IOe){
            System.out.println("IOException: " + IOe.getMessage());
        }catch(ClassNotFoundException CNFe) {
            System.out.println("Class not found exception: " + CNFe.getMessage());
        }
    }
}

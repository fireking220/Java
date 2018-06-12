package server;

import exceptions.InValidPortException;
import threads.AccountThread;
import threads.RegisterThread;
import threads.ServerThread;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket chatServerSocket;
    private Thread accountThread;
    private Thread registerThread;
    private List<User> users;
    private boolean connected;
    private int chatPort;
    private String chatHostname;
    private String logFile;
    FileWriter logWriter;

    public Server() throws IOException{
        chatPort = 0;
        accountThread = null;
        chatServerSocket = null;
        connected = false;
        chatHostname = null;
        users = new ArrayList<>();
        logFile = "resources\\log.txt";
    }

    public static void main(String[] args) {
        try {
            Server server;
            server = new Server();
            //runs our server given an input from args for the port
            if(server.serverChecks(args[0])){
                server.serverChecks(args[0]);
                server.runServer();
            }
        }catch(NumberFormatException NFe){
            System.out.println("Number format exception " + NFe.getMessage().toLowerCase());
        }catch(InValidPortException IPe){
            System.out.println("Invalid port exception: " + IPe.getMessage().toLowerCase());
        }catch(IOException IOe) {
            System.out.println("IOException: " + IOe.getMessage().toLowerCase());
        }catch(ClassNotFoundException CNFe){
            CNFe.getMessage();
        }
    }

    //run the server
    public void runServer() throws IOException, ClassNotFoundException{
        chatHostname = "localhost";
        InetAddress IPaddr = InetAddress.getByName(chatHostname);
        chatServerSocket = new ServerSocket(chatPort, 50, IPaddr);
        connected = true;
        System.out.println("Now listening on port " + chatPort + " with hostname " + IPaddr.getHostName());
        runAccountServer(1001, chatHostname);
        //run forever, accepting incoming connections
        while (connected) {
            Socket client = chatServerSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            //cast object to an account object
            Account clientAccount = (Account) ois.readObject();
            User newUser = new User(clientAccount, client);
            //add user to out user list
            users.add(newUser);
            //creates a new thread to listen for incoming messages from the user
            //REMEMBER TO START THREADS
            new Thread(new ServerThread(this, newUser)).start();
            broadcastMessage(clientAccount.getUserName() + " has entered the room");
        }
    }

    //check to see if given port is correct
    public boolean serverChecks(String port)throws NumberFormatException, InValidPortException{
        boolean state = false;
        chatPort = Integer.parseInt(port);
        if(chatPort >= 0 && chatPort <= 65535){
            state = true;
        }
        else {
            throw new InValidPortException(String.valueOf(chatPort));
        }
        return state;
    }

    //runs the account server for fetching accounts
    private void runAccountServer(int port, String hostname){
        accountThread = new Thread(new AccountThread(port, hostname,this));
        accountThread.start();
        runRegisterServer(900, hostname);
    }

    //runs the register server for creating new accounts
    private void runRegisterServer(int port, String hostname){
        registerThread = new Thread(new RegisterThread(port, hostname));
        registerThread.start();
    }

    //broadcast incoming message to all users currently connected
    public void broadcastMessage(String message) throws IOException{
        logWriter = new FileWriter(logFile, true);
        for (User curUser : users) {
            curUser.getWriter().writeObject(message);
        }
        logWriter.write(message + "\n");
        logWriter.close();
    }

    //broadcast all users to all currently connected clients
    public void broadcastUsers()throws IOException{
        for (User curUser : users) {
            //this resets the output stream to avoid sending old data, without this, the correct version of users
            //will not be sent
            //IMPORTANT!
            curUser.getWriter().reset();
            curUser.getWriter().writeObject(users);
        }
    }

    //function for removing a user from our users and updating the current online users for all connected clients
    public void removeUser(User userToRemove) throws IOException{
        users.remove(userToRemove);
        broadcastUsers();
    }

    //checks if a given user is online through username matching
    public boolean checkOnline(String usernameToCheck){
        boolean exists = false;
        for (User curUser: users) {
            if(curUser.getUserName().equals(usernameToCheck)){
                exists = true;
            }
        }
        return exists;
    }
}

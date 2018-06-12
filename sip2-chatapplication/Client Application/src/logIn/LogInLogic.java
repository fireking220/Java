package logIn;
import server.Account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LogInLogic {
    private Account myAccount;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket;
    private boolean loggedIn;

    public LogInLogic(){
        myAccount = null;
        ois = null;
        oos = null;
        socket = null;
        loggedIn = false;
    }

    //allows the user to attempt to sign into an account
    public boolean logIn(String userName, String password) throws IOException, ClassNotFoundException{
        boolean found = false;
        //create a temporary account to search with
        Account tempAccount = new Account(userName, password);
        //attempts to listen to the account server
        socket = new Socket("localhost", 1001);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
        //writes the account to the account server for checking
        oos.writeObject(tempAccount);
        //if we received a true back from the Object input stream, we found our account that we can log into
        if((boolean) ois.readObject()){
            found = true;
            loggedIn = true;
            //copy tempAccount into myAccount
            myAccount = new Account(tempAccount);
        }
        return found;
    }

    //get myAccount
    public Account getMyAccount() {
        return myAccount;
    }

    //check to see if logged in
    public boolean isLoggedIn(){
        return loggedIn;
    }

    //log out of account by freeing the resources attached to myAccount
    public void logOut(){
        myAccount = null;
        loggedIn = false;
    }

    //attempt to connect to the server designated by hostname and port
    public Socket connectToServer(String hostname, String port) throws IOException, NumberFormatException{
        Socket client = new Socket(hostname, Integer.parseInt(port));
        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
        //write the account to the main server
        oos.writeObject(myAccount);
        return client;
    }
}

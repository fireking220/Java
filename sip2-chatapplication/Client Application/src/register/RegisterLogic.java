package register;

import server.Account;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RegisterLogic {
    Socket socket;
    ObjectOutputStream oos;

    public void register(String username, String password) throws IOException {
        Account myAccount = new Account(username, password);
        socket = new Socket("localhost", 900);
        oos = new ObjectOutputStream(socket.getOutputStream());
        //writes the account to the account server for checking
        oos.writeObject(myAccount);
    }
}

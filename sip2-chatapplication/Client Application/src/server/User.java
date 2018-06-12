package server;

import java.io.*;
import java.net.Socket;

public class User extends Account implements Serializable{
    private transient ObjectOutputStream writer;
    private transient ObjectInputStream reader;

    public User(Account account, Socket client) throws IOException {
        super(account);
        writer = new ObjectOutputStream(client.getOutputStream());
        reader = new ObjectInputStream((client.getInputStream()));
    }

    public ObjectOutputStream getWriter() {
        return writer;
    }

    public ObjectInputStream getReader() {
        return reader;
    }

    @Override
    public String toString(){
        return userName;
    }
}

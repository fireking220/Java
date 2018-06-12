package threads;

import server.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread implements Runnable{
    DefaultListModel<String> messagesModel;
    DefaultListModel<User> userModel;
    private ObjectInputStream ois;

    public ClientThread(JList messageList, JList onlineUsers, Socket clientSocket){
        try {
            messagesModel = new DefaultListModel();
            userModel = new DefaultListModel<>();
            onlineUsers.setModel(userModel);
            messageList.setModel(messagesModel);
            ois = new ObjectInputStream(clientSocket.getInputStream());
        }catch (IOException IOe){
            IOe.getMessage();
        }
    }

    //listens for any incoming message from the server and display it in the text area
    public void run(){
        try {
            //listen for messages or Users
            Object curObject;
            while((curObject = ois.readObject()) != null) {
                if(curObject instanceof String){
                    messagesModel.addElement((String) curObject);
                }
                else if(curObject instanceof ArrayList) {
                    ArrayList<User> curUsers = (ArrayList<User>) curObject;
                    userModel.clear();
                    for (User curUser: curUsers) {
                        userModel.addElement(curUser);
                    }
                }
            }
        }catch (IOException IOe){
            IOe.getMessage();
        }catch (ClassNotFoundException CNFe){
            CNFe.getMessage();
        }
    }
}

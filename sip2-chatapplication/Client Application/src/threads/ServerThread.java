package threads;

import server.Server;
import server.User;

import java.io.IOException;

public class ServerThread implements Runnable{
    private Server serverRef;
    private User user;

    public ServerThread(Server server, User user) throws IOException{
        this.user = user;
        serverRef = server;
        serverRef.broadcastUsers();
    }

    //listens fo any message coming in and calls broadcast message to broadcast it to all users
    public void run(){
        try{
            String message;
            while((message = (String) user.getReader().readObject()) != null){
                serverRef.broadcastMessage(message);
            }
        }catch (IOException IOe){
            try {
                //when a client exits, remove the user from the users list in the server class
                //then broadcast a message to everyone that the user left
                serverRef.removeUser(user);
                serverRef.broadcastMessage(user.getUserName() + " signed out");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(ClassNotFoundException CNFe){
            CNFe.getMessage();
        }
    }
}

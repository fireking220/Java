package threads;

import java.net.ServerSocket;

public class AppThread {
    protected ServerSocket serverSocket;
    protected int port;
    protected String hostname;
    protected String accountFile;
    protected boolean connected;

    public AppThread(int port, String hostname){
        serverSocket = null;
        this.port = port;
        this.hostname = hostname;
        accountFile = "resources\\Accounts.txt";
        connected = false;
    }
}

package client;

import threads.ClientThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class ClientGUI {
    private JPanel mainPanel;
    private JTextField messageTextField;
    private JButton sendButton;
    private JList onlineList;
    private JTextField hostTextField;
    private JButton viewLogsButton;
    private JLabel welcomeLabel;
    private JList messageList;

    public ClientGUI(Socket clientSocket, String username) {
        ClientLogic clientLogicRef = new ClientLogic(clientSocket, username);
        hostTextField.setText(clientSocket.getInetAddress().getHostName());
        welcomeLabel.setText("Welcome " + username);
        //start thread for listening for incoming messages to populate our text area with
        //REMEMBER TO START THREADS
        new Thread(new ClientThread(messageList, onlineList, clientSocket)).start();

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    clientLogicRef.send(messageTextField.getText());
                }catch(IOException IOe){
                    IOe.getMessage();
                }
            }
        });
        viewLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void createClientFrame() {
        JFrame frame = new JFrame("Client");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

package logIn;

import client.ClientGUI;
import register.RegisterGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LogInGUI {
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private JButton logInButton;
    private JButton registerButton;
    private JTextField hostnameTextField;
    private JTextField portTextField;
    private JButton connectToServerButton;
    private JButton logOutButton;
    private JLabel signedInLabel;
    private JFrame logInFrame;

    public LogInGUI(JFrame frame) {
        LogInLogic logInLogicRef;
        logInLogicRef = new LogInLogic();
        RegisterGUI registerGUIRef = new RegisterGUI();
        logInFrame = frame;

        //log into an account
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //if account exists
                    if(logInLogicRef.logIn(userNameTextField.getText(), passwordField.getText())){
                        //update label
                        signedInLabel.setText("Signed in as " + logInLogicRef.getMyAccount().getUserName());
                        //disable/enable buttons
                        logInButton.setEnabled(false);
                        userNameTextField.setEnabled(false);
                        passwordField.setEnabled(false);
                        logOutButton.setEnabled(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "Account not found or account currently signed in");
                    }
                }catch(IOException IOe){
                    System.out.println(IOe.getMessage());
                }catch(ClassNotFoundException CNFe){
                    System.out.println(CNFe.getMessage());
                }
            }
        });

        //log out of an already logged in account
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logInLogicRef.logOut();
                signedInLabel.setText("Not signed in");
                //disable/enable buttons
                logInButton.setEnabled(true);
                userNameTextField.setEnabled(true);
                passwordField.setEnabled(true);
                logOutButton.setEnabled(false);
            }
        });

        //register for a new account
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerGUIRef.createRegisterFrame();
            }
        });

        //connect to the server
        connectToServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //check if logged in
                    if (logInLogicRef.isLoggedIn()) {
                        //check if text boxes are empty
                        if (!hostnameTextField.getText().isEmpty() && !portTextField.getText().isEmpty()) {
                            //if not, connect to server and create client GUI
                            ClientGUI clientGUIRef;
                            clientGUIRef = new ClientGUI(logInLogicRef.connectToServer(hostnameTextField.getText(),
                                    portTextField.getText()),logInLogicRef.getMyAccount().getUserName());
                            clientGUIRef.createClientFrame();
                            logInFrame.dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(new JFrame(), "Please enter in hostname and port");
                        }
                    }
                    //if not display error
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "Please log in");
                    }
                }catch(IOException IOe){
                    IOe.getMessage();
                }catch(NumberFormatException NFe){
                    NFe.getMessage();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Log In");
        frame.setContentPane(new LogInGUI(frame).mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

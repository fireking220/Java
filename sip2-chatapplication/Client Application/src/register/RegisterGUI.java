package register;

import logIn.LogInLogic;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class RegisterGUI {
    private JFrame registerFrame;
    private JPanel mainPanel;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JPasswordField reEnterPasswordField;
    private JButton registerButton;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private boolean passwordsEqual = false;
    private boolean usernameEntered = false;


    public RegisterGUI() {
        RegisterLogic registerLogicRef;
        registerLogicRef = new RegisterLogic();
        LogInLogic logInLogicRef = new LogInLogic();

        userNameTextField.getDocument().addDocumentListener(new UsernameListener());
        reEnterPasswordField.getDocument().addDocumentListener(new RePasswordListener());
        passwordField.getDocument().addDocumentListener(new PasswordListener());

        //register for a new account
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //check if all necessary requirements are met first
                    if (passwordsEqual && usernameEntered) {
                        //check to see if the account the user is trying to register for is taken
                        if (!logInLogicRef.logIn(userNameTextField.getText(), reEnterPasswordField.getText())) {
                            //register the account
                            registerLogicRef.register(userNameTextField.getText(), reEnterPasswordField.getText());
                            JOptionPane.showMessageDialog(new JFrame(), "Account registered");
                            //close the register frame
                            registerFrame.dispose();
                        }
                        else {
                            JOptionPane.showMessageDialog(new JFrame(), "Account already exists");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(new JFrame(), "Please fill in all the fields properly");
                    }
                }catch(IOException IOe){
                    IOe.getMessage();
                }catch(ClassNotFoundException CNFe){
                    CNFe.getMessage();
                }
            }
        });
    }

    //Document listener class for usernameTextField
    private class UsernameListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e){
                usernameLabel.setText("Username entered");
                usernameEntered = true;
        }
        public void removeUpdate(DocumentEvent e){
            if(userNameTextField.getText().isEmpty()){
                usernameLabel.setText("No Username Detected");
                usernameEntered = false;
            }
        }
        public void changedUpdate(DocumentEvent e){
            //not implemented
        }
    }

    //Document listener class for reEnterPasswordField
    private class RePasswordListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e){
            if(reEnterPasswordField.getText().equals(passwordField.getText())) {
                passwordLabel.setText("Passwords equal");
                passwordsEqual = true;
            }
            else {
                passwordLabel.setText("Passwords not equal");
                passwordsEqual = false;
            }
        }
        public void removeUpdate(DocumentEvent e){
            if(reEnterPasswordField.getText().equals(passwordField.getText())) {
                passwordLabel.setText("Passwords equal");
                passwordsEqual = true;
            }
            else {
                passwordLabel.setText("Passwords not equal");
                passwordsEqual = false;
            }
        }
        public void changedUpdate(DocumentEvent e){
            //not implemented
        }
    }

    //Document listener class for passwordField
    private class PasswordListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e){
            if(passwordField.getText().equals(reEnterPasswordField.getText())) {
                passwordLabel.setText("Passwords equal");
                passwordsEqual = true;
            }
            else {
                passwordLabel.setText("Passwords not equal");
                passwordsEqual = false;
            }
        }
        public void removeUpdate(DocumentEvent e){
            if(passwordField.getText().equals(reEnterPasswordField.getText())) {
                passwordLabel.setText("Passwords equal");
                passwordsEqual = true;
            }
            else {
                passwordLabel.setText("Passwords not equal");
                passwordsEqual = false;
            }
        }
        public void changedUpdate(DocumentEvent e){
            //not implemented
        }
    }

    //method to create the register frame
    public void createRegisterFrame() {
        RegisterGUI RGUI = new RegisterGUI();
        JFrame frame = new JFrame("Register");
        frame.setContentPane(RGUI.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //assign the registerFrame to be closed later
        RGUI.registerFrame = frame;
    }
}

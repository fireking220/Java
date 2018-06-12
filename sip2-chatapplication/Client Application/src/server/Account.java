package server;

import java.io.Serializable;

public class Account implements Serializable {
    protected String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Account(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    public Account(Account accountToCopy){
        this.userName = accountToCopy.userName;
        this.password = accountToCopy.password;
    }
}

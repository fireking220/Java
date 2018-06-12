package exceptions;

public class InValidPortException extends Exception{
    public InValidPortException(String message){
        super(message + " is not a valid port");
    }
}

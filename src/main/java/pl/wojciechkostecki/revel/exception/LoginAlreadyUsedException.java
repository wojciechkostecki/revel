package pl.wojciechkostecki.revel.exception;

public class LoginAlreadyUsedException extends RuntimeException{
    public LoginAlreadyUsedException(String message) {
        super(message);
    }
}

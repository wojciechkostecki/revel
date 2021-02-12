package pl.wojciechkostecki.revel.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String message, Object firstObject, Object secondObject) {
    }
}

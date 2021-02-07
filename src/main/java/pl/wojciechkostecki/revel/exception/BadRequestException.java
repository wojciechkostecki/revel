package pl.wojciechkostecki.revel.exception;

import java.text.MessageFormat;

public class BadRequestException extends RuntimeException{
    public BadRequestException(final  Long menuId, final Long localId){
        super(MessageFormat.format("Menu: {0} is already assigned to local: {1}", menuId,localId));
    }
}

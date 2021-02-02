package pl.wojciechkostecki.revel.exception;

import java.text.MessageFormat;

public class MenuIsAlreadyAssignedException extends RuntimeException{
    public MenuIsAlreadyAssignedException(final  Long menuId, final Long localId){
        super(MessageFormat.format("Menu: {0} is already assigned to local: {1}", menuId,localId));
    }
}

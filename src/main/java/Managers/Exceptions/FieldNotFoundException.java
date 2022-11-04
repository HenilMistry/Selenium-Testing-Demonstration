package Managers.Exceptions;

public class FieldNotFoundException extends Exception {

    public FieldNotFoundException() {
        super("The field, Provided in Keys is not found in WebFields.");
    }


    public FieldNotFoundException(String message) {
        super(message);
    }
}

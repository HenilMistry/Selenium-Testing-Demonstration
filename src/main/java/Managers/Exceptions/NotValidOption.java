package Managers.Exceptions;

public class NotValidOption extends Exception {

    public NotValidOption() {
        System.out.println("This is not the valid option for this property!");
    }


    public NotValidOption(String message) {
        super("This is not the valid option for "+message+" property.");
    }
}

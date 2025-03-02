package at.backend.CRM.CommonClasses.Exceptions;

public class InvalidInputException extends BaseException {

    public InvalidInputException(String message) {
        super(message, "INVALID_INPUT");
    }
}
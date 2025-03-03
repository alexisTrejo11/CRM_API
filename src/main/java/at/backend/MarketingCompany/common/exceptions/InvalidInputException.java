package at.backend.MarketingCompany.common.exceptions;

public class InvalidInputException extends BaseException {

    public InvalidInputException(String message) {
        super(message, "INVALID_INPUT");
    }
}
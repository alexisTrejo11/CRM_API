package at.backend.MarketingCompany.marketing.activity.domain.Exception;


import at.backend.MarketingCompany.common.exceptions.BaseException;

public class ActivityException extends BaseException {
    public ActivityException(String message) {
        super(message, "Activity Exception");
    }
}


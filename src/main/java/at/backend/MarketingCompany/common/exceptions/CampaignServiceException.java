package at.backend.MarketingCompany.common.exceptions;

public class CampaignServiceException extends BaseException {

    public CampaignServiceException(String message) {
        super(message, "Campaign Service");
    }
}
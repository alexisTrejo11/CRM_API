package at.backend.MarketingCompany.marketing.campaign.domain.Exceptions;

import at.backend.MarketingCompany.common.exceptions.BaseException;

public class CampaignException extends BaseException {
    public CampaignException(String message) {
        super(message, "CampaignException");
    }
}

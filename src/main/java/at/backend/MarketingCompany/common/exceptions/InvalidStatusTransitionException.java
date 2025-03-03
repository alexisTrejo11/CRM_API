package at.backend.MarketingCompany.common.exceptions;

import at.backend.MarketingCompany.common.utils.Enums.MarketingCampaign.ActivityStatus;

public class InvalidStatusTransitionException extends BaseException {

    public InvalidStatusTransitionException(ActivityStatus currentStatus, ActivityStatus targetStatus) {
        super("Cannot transition activity from " + currentStatus + " to " + targetStatus, "INVALID_STATUS_TRANSITION");
    }
}

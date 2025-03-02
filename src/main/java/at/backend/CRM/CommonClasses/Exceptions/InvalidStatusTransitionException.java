package at.backend.CRM.CommonClasses.Exceptions;

import at.backend.CRM.MarketingProject.Utils.Enums.ActivityStatus;

public class InvalidStatusTransitionException extends BaseException {

    public InvalidStatusTransitionException(ActivityStatus currentStatus, ActivityStatus targetStatus) {
        super("Cannot transition activity from " + currentStatus + " to " + targetStatus, "INVALID_STATUS_TRANSITION");
    }
}

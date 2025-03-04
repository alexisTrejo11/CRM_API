package at.backend.MarketingCompany.marketing.activity.domain.HelperClasses;

import java.time.LocalDateTime;
import java.util.Objects;

public record ActivitySchedule(
        LocalDateTime plannedStartDate,
        LocalDateTime plannedEndDate,
        LocalDateTime actualStartDate,
        LocalDateTime actualEndDate
) {
    public ActivitySchedule {
        Objects.requireNonNull(plannedStartDate, "Planned start date is required");
        if (plannedEndDate != null && plannedEndDate.isBefore(plannedStartDate)) {
            throw new IllegalArgumentException("Planned end date must be after start date");
        }
    }

    public ActivitySchedule withActualStart(LocalDateTime start) {
        return new ActivitySchedule(
                plannedStartDate,
                plannedEndDate,
                start,
                actualEndDate
        );
    }

    public ActivitySchedule withActualEnd(LocalDateTime end) {
        return new ActivitySchedule(
                plannedStartDate,
                plannedEndDate,
                actualStartDate,
                end
        );
    }
}

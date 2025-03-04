package at.backend.MarketingCompany.marketing.attribution.domain.HelperHandlers;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class TouchTimeline {
    LocalDateTime firstTouch;
    LocalDateTime lastTouch;
    @Builder.Default Integer touchCount = 1;
    @Builder.Default List<LocalDateTime> touches = new ArrayList<>();

    public TouchTimeline(LocalDateTime firstTouch, LocalDateTime lastTouch, Integer touchCount, List<LocalDateTime> touches) {
        if (firstTouch == null || lastTouch == null) {
            throw new IllegalArgumentException("Touch timestamps cannot be null");
        }
        if (firstTouch.isAfter(lastTouch)) {
            throw new IllegalArgumentException("First touch must be before last touch");
        }
        if (touchCount == null || touchCount < 1) {
            throw new IllegalArgumentException("Touch count must be at least 1");
        }
        if (touches == null || touches.isEmpty()) {
            throw new IllegalArgumentException("Touch list cannot be null or empty");
        }
        this.firstTouch = firstTouch;
        this.lastTouch = lastTouch;
        this.touchCount = touchCount;
        this.touches = touches;
    }

    public TouchTimeline addTouch(LocalDateTime touchTime) {
        List<LocalDateTime> updatedTouches = new ArrayList<>(this.touches);
        updatedTouches.add(touchTime);

        return TouchTimeline.builder()
                .firstTouch(this.firstTouch)
                .lastTouch(touchTime)
                .touchCount(updatedTouches.size())
                .touches(updatedTouches)
                .build();
    }
}

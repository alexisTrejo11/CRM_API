package at.backend.MarketingCompany.marketing.attribution.infrastructure.DTOs;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TouchPointDTO {
    private final LocalDateTime timestamp;

    public TouchPointDTO(LocalDateTime timestamp) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
        this.timestamp = timestamp;
    }


}


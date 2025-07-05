package emergency.server.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class FacilityDto {

    @Getter
    @Builder
    public static class FacilityListDto {
        private Boolean isOpen;
        private String consultType;
        private String facilityName;
        private LocalTime weekdayStartTime;
        private LocalTime weekdayEndTime;
        private LocalTime weekendStartTime;
        private LocalTime weekendEndTime;
        private String phone;
        private String webUrl;
    }

}

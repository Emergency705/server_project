package emergency.server.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class AnnouncementResponseDTO {

    @Getter
    @Builder
    public static class AnnouncementListDto{
        Long id;
        String infoType;
        String region;
        String target;
        Integer remainPeriod;
        String title;
        String institute;
        LocalDate openDate;
    }

    @Getter
    @Builder
    public static class AnnouncementDetailsDto{
        Long id;
        String infoType;
        String region;
        String target;
        Integer remainPeriod;
        String title;
        String institute;
        String content;
        LocalDate openDate;
    }

}

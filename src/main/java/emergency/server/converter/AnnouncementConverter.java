package emergency.server.converter;

import emergency.server.domain.Announcement;
import emergency.server.dto.AnnouncementResponseDTO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AnnouncementConverter {

    // List<Announcement> -> List<AnnouncementResponseDTO.AnnouncementListDto>
    public static List<AnnouncementResponseDTO.AnnouncementListDto> toAnnouncementDtoList(List<Announcement> announcementList) {

        return announcementList.stream()
                .map(AnnouncementConverter::toAnnouncementDto)
                .toList();
    }

    // Announcement -> AnnouncementListDto
    private static AnnouncementResponseDTO.AnnouncementListDto toAnnouncementDto(Announcement announcement) {
        return AnnouncementResponseDTO.AnnouncementListDto.builder()
                .id(announcement.getAnnouncementId())
                .infoType(announcement.getType().getKorean())
                .region(announcement.getRegion().getName())
                .target(announcement.getTarget().getKorean())
                .remainPeriod((int)ChronoUnit.DAYS.between(LocalDate.now(), announcement.getCloseDate()))
                .title(announcement.getTitle())
                .institute(announcement.getInstitution())
                .openDate(announcement.getOpenDate())
                .build();

    }

    // Announcement -> AnnouncementDetailsDto
    public static AnnouncementResponseDTO.AnnouncementDetailsDto toAnnouncementDetailsDto(Announcement announcement) {
        return AnnouncementResponseDTO.AnnouncementDetailsDto.builder()
                .id(announcement.getAnnouncementId())
                .infoType(announcement.getType().getKorean())
                .region(announcement.getRegion().getName())
                .target(announcement.getTarget().getKorean())
                .remainPeriod((int)ChronoUnit.DAYS.between(LocalDate.now(), announcement.getCloseDate()))
                .title(announcement.getTitle())
                .institute(announcement.getInstitution())
                .openDate(announcement.getOpenDate())
                .content(announcement.getContent())
                .build();

    }

}

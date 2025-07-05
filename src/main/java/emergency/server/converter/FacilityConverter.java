package emergency.server.converter;

import emergency.server.domain.Facility;
import emergency.server.dto.FacilityDto;

import java.util.List;

public class FacilityConverter {

    public static List<FacilityDto.FacilityListDto> toDtoList(List<Facility> facilityList) {
        return facilityList.stream()
                .map(FacilityConverter::toDto)
                .toList();
    }

    private static FacilityDto.FacilityListDto toDto(Facility facility) {
        return FacilityDto.FacilityListDto.builder()
                .isOpen(facility.getIsOpen())
                .consultType(facility.getType().getKorean())
                .facilityName(facility.getName())
                .weekdayStartTime(facility.getWeekdayStartTime())
                .weekdayEndTime(facility.getWeekdayEndTime())
                .weekendStartTime(facility.getWeekendStartTime())
                .weekendEndTime(facility.getWeekendEndTime())
                .phone(facility.getPhone())
                .webUrl(facility.getWebsite())
                .build();
    }

}

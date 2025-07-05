package emergency.server.service;

import emergency.server.converter.FacilityConverter;
import emergency.server.domain.Facility;
import emergency.server.domain.enums.FacilityType;
import emergency.server.dto.FacilityDto;
import emergency.server.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public List<FacilityDto.FacilityListDto> getFacilityList(Boolean isOpen, FacilityType type) {

        if (isOpen == null) {
            isOpen = true;
        }

        if(type == null) {
            type = FacilityType.COMMUNITY_CENTER;
        }

        List<Facility> facility = facilityRepository.findByTypeAndIsOpen(type, isOpen);
        return FacilityConverter.toDtoList(facility);

    }

}

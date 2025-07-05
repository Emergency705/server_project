package emergency.server.service;

import emergency.server.converter.AnnouncementConverter;
import emergency.server.domain.Announcement;
import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Target;
import emergency.server.dto.AnnouncementResponseDTO;
import emergency.server.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementResponseDTO.AnnouncementListDto> getAnnouncementList(
            AnnouncementType type, Boolean isRecruiting, Long regionId, Target target
    ) {
        // 공고 유형 값 검사
        // 올바르지 않은 유형 & null 검사
        // -> ExceptionAdvice에서 예외처리 진행함

        // 쿼리 조회
        List<Announcement> findByFilter = announcementRepository.findByFilter(type, isRecruiting, regionId, target);

        // 변환
        return AnnouncementConverter.toAnnouncementDtoList(findByFilter);
    }

}

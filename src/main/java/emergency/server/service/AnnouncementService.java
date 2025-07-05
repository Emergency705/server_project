package emergency.server.service;

import emergency.server.converter.AnnouncementConverter;
import emergency.server.domain.Announcement;
import emergency.server.domain.enums.AnnouncementType;
import emergency.server.domain.enums.Region;
import emergency.server.domain.enums.Target;
import emergency.server.dto.AnnouncementResponseDTO;
import emergency.server.global.common.apiPayload.code.status.ErrorStatus;
import emergency.server.global.common.apiPayload.exception.handler.ErrorHandler;
import emergency.server.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<AnnouncementResponseDTO.AnnouncementListDto> getAnnouncementList(
            AnnouncementType type, Boolean isRecruiting, Region region, Target target
    ) {
        // 공고 유형 값 검사
        // 올바르지 않은 유형 & null 검사
        // -> ExceptionAdvice에서 예외처리 진행함

        // 쿼리 조회
        List<Announcement> findByFilter = announcementRepository.findByFilter(type, isRecruiting, region, target);

        // 변환
        return AnnouncementConverter.toAnnouncementDtoList(findByFilter);
    }

    public AnnouncementResponseDTO.AnnouncementDetailsDto getAnnouncementDetails(Long announcementId) {

        if(announcementId == null){
            throw new ErrorHandler(ErrorStatus.ANNOUNCEMENT_NULL);
        }

        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new ErrorHandler(ErrorStatus.ANNOUNCEMENT_NOT_FOUND));

        return AnnouncementConverter.toAnnouncementDetailsDto(announcement);
    }

}

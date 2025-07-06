package emergency.server.global.common.apiPayload.code.status;

import emergency.server.global.common.apiPayload.code.BaseErrorCode;
import emergency.server.global.common.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러"),


    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "사용자가 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "MEMBER4002", "잘못된 비밀번호입니다."),
    REGION_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4003","잘못된 지역입니다." ),
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER4004", "이름은 2-20자 사이여야 하며 공백일 수 없습니다"),
    INVALID_DISABLE_TYPE(HttpStatus.BAD_REQUEST, "MEMBER4005", "존재하지 않은 장애 유형입니다."),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "MEMBER4006", "이미 사용중인 로그인 ID입니다."),
    INVALID_LOGIN_ID_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER4007", "로그인 ID는 4-20자의 영문, 숫자만 가능합니다."),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER4008", "비밀번호는 8-20자의 영문, 숫자, 특수문자를 포함해야 합니다."),
    INVALID_BIRTH_DATE(HttpStatus.BAD_REQUEST, "MEMBER4009", "올바르지 않은 생년월일입니다."),
    FUTURE_BIRTH_DATE(HttpStatus.BAD_REQUEST, "MEMBER4010", "생년월일은 현재 날짜보다 이전이어야 합니다."),
    MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, "MEMBER4011", "필수 입력 항목이 누락되었습니다."),

    // JWT 관련 에러
    INVALID_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4001", "유효하지 않은 토큰입니다."),
    EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4002", "만료된 토큰입니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "AUTH4003", "접근 권한이 없습니다."),

    INVALID_IMAGE_FORMAT(HttpStatus.BAD_REQUEST, "IMAGE4001", "올바르지 않은 이미지 형식입니다."),
    IMAGE_TOO_LARGE(HttpStatus.BAD_REQUEST, "IMAGE4002", "이미지 크기가 너무 큽니다. (최대 5MB)"),

    ENUM_TYPE_ERROR(HttpStatus.BAD_REQUEST, "COMMON401", "올바른 enum 값이 필요합니다"),
    TYPE_ERROR(HttpStatus.BAD_REQUEST, "COMMON402", "올바른 type을 입력하세요"),
    PARAMETER_NULL(HttpStatus.BAD_REQUEST, "COMMON403", "파라미터 누락"),

    // ANNOUNCEMENT
    ANNOUNCEMENT_NULL(HttpStatus.BAD_REQUEST, "ANNOUNCEMENT401", "공고 식별자 값이 필요합니다"),
    ANNOUNCEMENT_INVALID_TYPE(HttpStatus.BAD_REQUEST, "ANNOUNCEMENT402", "올바르지 않은 공고 유형입니다"),
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "ANNOUNCEMENT403", "존재하지 않는 공고입니다"),


    // Funding
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "FUNDING4001", "아이템을 찾을 수 없습니다."),
    FUNDING_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "FUNDING4002", "이미 펀딩이 존재합니다."),
    FUNDING_NOT_FOUND(HttpStatus.NOT_FOUND, "FUNDING4003", "펀딩을 찾을 수 없습니다."),

    // tmp
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트테스트"),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }


}

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
    INVALID_NAME_FORMAT(HttpStatus.BAD_REQUEST, "MEMBER4004", "이름은 공백일 수 없습니다"),
    INVALID_DISABLE_TYPE(HttpStatus.BAD_REQUEST, "MEMBER4005", "존재하지 않은 장애 유형입니다."),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "MEMBER4006", "이미 사용중인 로그인 ID입니다."),

    ENUM_TYPE_ERROR(HttpStatus.BAD_REQUEST, "COMMON401", "올바른 enum 값이 필요합니다"),
    TYPE_ERROR(HttpStatus.BAD_REQUEST, "COMMON402", "올바른 type을 입력하세요"),
    PARAMETER_NULL(HttpStatus.BAD_REQUEST, "COMMON403", "파라미터 누락"),

    ANNOUNCEMENT_NULL(HttpStatus.BAD_REQUEST, "ANNOUNCEMENT401", "공고 유형 값이 필요합니다"),
    ANNOUNCEMENT_INVALID_TYPE(HttpStatus.BAD_REQUEST, "ANNOUNCEMENT402", "올바르지 않은 공고 유형입니다"),


    // tmp
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트테스트");

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

package emergency.server.global.common.apiPayload.exception.handler;

import emergency.server.global.common.apiPayload.code.BaseErrorCode;
import emergency.server.global.common.apiPayload.exception.GeneralException;

public class ErrorHandler extends GeneralException {

    public ErrorHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

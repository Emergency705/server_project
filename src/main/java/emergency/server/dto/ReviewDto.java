package emergency.server.dto;

import lombok.Builder;
import lombok.Data;

public class ReviewDto {

    @Data
    @Builder
    public static class Response {
        private Long id;
        private String content;
        private String userName;
    }

    @Data
    @Builder
    public static class CreateRequest {
        private Long itemId;
        private String content;
    }
}

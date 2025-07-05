package emergency.server.converter;

import emergency.server.domain.Review;
import emergency.server.dto.ReviewDto;

import java.util.List;

public class ReviewConverter {

    public static ReviewDto.Response toResponse(Review review) {
        return ReviewDto.Response.builder()
                .id(review.getReviewId())
                .content(review.getContent())
                .userName(review.getUser().getName())
                .build();
    }
    public static List<ReviewDto.Response> toListResponse(List<Review> review) {
        return review.stream()
                .map(ReviewConverter::toResponse)
                .toList();
    }
}

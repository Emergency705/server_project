package emergency.server.service;

import emergency.server.converter.ReviewConverter;
import emergency.server.domain.Item;
import emergency.server.domain.Review;
import emergency.server.domain.User;
import emergency.server.dto.ReviewDto;
import emergency.server.repository.ItemRepository;
import emergency.server.repository.ReviewRepository;
import emergency.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    public void saveReview(ReviewDto.CreateRequest dto, UserDetails user) {
        User userEntity = userRepository.findByLoginId(user.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Item itemEntity = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다."));

        reviewRepository.save(ReviewConverter.toEntity(dto, userEntity, itemEntity));
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByItemId(Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("아이템을 찾을 수 없습니다."));

        return reviewRepository.findAllByItem(item);
    }
}

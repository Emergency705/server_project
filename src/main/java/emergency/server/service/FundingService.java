package emergency.server.service;

import emergency.server.converter.FundingConverter;
import emergency.server.converter.ItemConverter;
import emergency.server.converter.ReviewConverter;
import emergency.server.converter.SellerConverter;
import emergency.server.domain.Funding;
import emergency.server.domain.Item;
import emergency.server.domain.User;
import emergency.server.dto.FundingDto;
import emergency.server.dto.ItemDto;
import emergency.server.repository.FundingRepository;
import emergency.server.repository.ItemRepository;
import emergency.server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundingService {

    private final FundingRepository fundingRepository;
    private final ItemRepository itemRepository;

    // TODO : 허유진 - 시큐리티 uesr detail 머지 이후 삭제
    /**
     * 아이템 목록 조회
     * @return List<ItemDto.ListResponse>
     */
    @Transactional(readOnly = true)
    public List<ItemDto.ListResponse> getItemList() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto.ListResponse> dtos = items.stream()
                .map(i -> {
                    int currentCount = i.getFundings().stream()
                            .mapToInt(Funding::getCount)
                            .sum();
                    int currentPrice = (i.getStartPrice()-i.getMaxPrice()) * currentCount/i.getMaxCount();
                    return ItemConverter.toListResponse(i, currentCount, currentPrice);
                })
                .toList();
        return dtos;
    }

    private final UserRepository userRepository;

    /**
     * 아이템 상세 조회
     * @param id 아이템 ID
     * @return ItemDto.Response
     */
    @Transactional(readOnly = true)
    public ItemDto.Response getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        int currentCount = item.getFundings().stream()
                .mapToInt(Funding::getCount)
                .sum();
        int currentPrice = (item.getStartPrice()-item.getMaxPrice()) * currentCount/item.getMaxCount();

        return ItemConverter.toResponse(item, currentCount, currentPrice);
    }

    // 내 펀딩 목록 조회
    public List<ItemDto.ListResponse> getUserItemList(UserDetails user) {
        User userEntity = userRepository.findByLoginId(user.getUsername()).orElseThrow(() -> new RuntimeException("사용자를 조회할 수 없습니다."));

        List<Item> items = itemRepository.findByUser(userEntity.getLoginId()).stream().toList();

        List<ItemDto.ListResponse> dtos = items.stream()
                .map(i -> {
                    int currentCount = i.getFundings().stream()
                            .mapToInt(Funding::getCount)
                            .sum();
                    int currentPrice = (i.getStartPrice()-i.getMaxPrice()) * currentCount/i.getMaxCount();
                    return ItemConverter.toListResponse(i, currentCount, currentPrice);
                })
                .toList();
        return dtos;
    }

    @Transactional
    public void saveFunding(FundingDto.SaveRequest dto, UserDetails user) {
        User userEntity = userRepository.findByLoginId(user.getUsername()).orElseThrow(() -> new RuntimeException("사용자를 조회할 수 없습니다."));
        Item itemEntity = itemRepository.findById(dto.getItemId()).orElseThrow(() -> new RuntimeException("아이템을 조회할 수 없습니다."));

        Optional<Funding> funding = fundingRepository.findByItemAndUser(itemEntity, userEntity);
        if (funding.isPresent()) {
            throw new RuntimeException("이미 펀딩한 아이템입니다.");
        }

        fundingRepository.save(FundingConverter.toEntity(dto, itemEntity, userEntity));
    }

    @Transactional
    public void deleteFunding(Long itemId, UserDetails user) {
        User userEntity = userRepository.findByLoginId(user.getUsername()).orElseThrow(() -> new RuntimeException("아이템을 조회할 수 없습니다."));
        Item itemEntity = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("아이템을 조회할 수 없습니다."));

        Funding funding = fundingRepository.findByItemAndUser(itemEntity, userEntity)
                .orElseThrow(() -> new RuntimeException("펀딩 내역이 존재하지 않습니다."));

        fundingRepository.delete(funding);
    }
}

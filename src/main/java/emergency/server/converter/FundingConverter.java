package emergency.server.converter;

import emergency.server.domain.Funding;
import emergency.server.domain.Item;
import emergency.server.domain.User;
import emergency.server.dto.FundingDto;
import emergency.server.dto.ItemDto;
import org.hibernate.Internal;

public class FundingConverter {

    public static Funding toEntity(FundingDto.SaveRequest dto, Item item, User user) {
        return Funding.builder()
                .item(item)
                .user(user)
                .count(dto.getCount())
                .build();

    }
}

package emergency.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PlaceType {
    LIVING("거실"),
    BEDROOM("침실"),
    KITCHEN("주방"),
    BATHROOM("욕실");

    private final String name;
}

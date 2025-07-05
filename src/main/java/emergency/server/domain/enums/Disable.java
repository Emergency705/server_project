package emergency.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Disable {
    NON_DISABLED("비장애인"),
    DISABLED("장애인");

    private final String name;
}
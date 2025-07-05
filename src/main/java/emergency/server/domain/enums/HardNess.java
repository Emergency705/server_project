package emergency.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HardNess {
    NONE("비장애인"),
    MILD("심하지 않음"),
    SEVERE("심함");

    private final String name;
}
package emergency.server.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DisableType {
    A(""),
    B("");

    private final String name;
}

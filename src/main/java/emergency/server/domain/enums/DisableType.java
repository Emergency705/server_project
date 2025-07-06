package emergency.server.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DisableType {
    PHYSICAL("지체장애"),
    HEARING("청각장애"),
    VISUAL("시각장애"),
    BRAIN_LESION("뇌병변장애"),
    SPEECH("언어장애"),
    FACIAL("안면장애"),
    INTELLECTUAL("지적장애"),
    AUTISM("자폐성장애"),
    MENTAL("정신장애");

    private final String name;
}
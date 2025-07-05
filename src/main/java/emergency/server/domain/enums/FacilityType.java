package emergency.server.domain.enums;

public enum FacilityType {

    COMMUNITY_CENTER("주민센터"),
    PORTAL("인터넷 포털"),
    WELFARE_CENTER("복지센터")
    ;

    private final String korean;

    FacilityType(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

}

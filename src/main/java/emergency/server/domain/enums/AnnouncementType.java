package emergency.server.domain.enums;

public enum AnnouncementType {

    HOUSE("주거"),
    LOAN("대출"),
    REPAIRMENT("수리"),
    COMFORTS("편의시설");

    private final String korean;

    AnnouncementType(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }
}

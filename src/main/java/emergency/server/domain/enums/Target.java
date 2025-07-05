package emergency.server.domain.enums;

public enum Target {

    YOUNG("청년"), ALONE("1인가구"), ELDER("65세 이상")
    ;

    private final String korean;

    Target(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

}

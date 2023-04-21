package YOUmI.util.enumeration;

public enum MBTI {
    ESFP("ESFP"),
    ESFJ("ESFJ"),
    ESTP("ESTP"),
    ESTJ("ESTJ"),
    ENFP("ENFP"),
    ENFJ("ENFJ"),
    ENTP("ENTP"),
    ENTJ("ENTJ"),
    ISFP("ISFP"),
    ISFJ("ISFJ"),
    ISTP("ISTP"),
    ISTJ("ISTJ"),
    INFP("INFP"),
    INFJ("INFJ"),
    INTP("INTP"),
    INTJ("INTJ");

    private final String mbti;

    MBTI(String mbti) {
        this.mbti = mbti;
    }

    public String getName() {
        return this.mbti;
    }
}

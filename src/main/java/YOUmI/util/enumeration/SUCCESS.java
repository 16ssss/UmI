package YOUmI.util.enumeration;

public enum SUCCESS implements RESULT {
    SUCCESS(SUCCESS_CODE, "요청을 성공적으로 처리했습니다."),
    CREATED(CREATED_CODE, "성공적으로 추가되었습니다.");

    private final String code;
    private final String message;

    SUCCESS(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return this.code;
    }

    public String getMessage(){
        return this.message;
    }
}
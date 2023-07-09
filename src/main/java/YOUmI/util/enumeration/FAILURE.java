package YOUmI.util.enumeration;

public enum FAILURE implements RESULT {

    FAILURE(FAILURE_CODE, "요청을 처리하지 못했습니다."),
    ABSENT_REQUIRED_VALUE(ABSENT_REQUIRED_VALUE_CODE, "필수 값을 확인해주세요."),
    NOT_IMAGE_FILE(RESULT.NOT_IMAGE_FILE, "이미지 파일만 저장 가능합니다.");


    private final String code;
    private final String message;

    FAILURE(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }


}

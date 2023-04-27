package YOUmI.util.enumeration;

public enum FAILURE implements RESULT {

    FAILURE(FAILURE_CODE, "요청을 처리하지 못했습니다."),
    ABSENT_REQUIRED_VALUE(ABSENT_REQUIRED_VALUE_CODE, "필수 값을 확인해주세요."),
    DUPLICATE_RESOURCE(DUPLICATE_RESOURCE_CODE, "중복된 값이 포함되어 있습니다.");

    private final String code;
    private final String message;

    FAILURE(String code, String message) {
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

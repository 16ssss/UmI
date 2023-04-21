package YOUmI.util.enumeration;

public interface RESULT {
    public String getCode();
    public String getMessage();

    public static final String FAILURE_CODE = "9999";
    public static final String ABSENT_REQUIRED_VALUE_CODE = "9998";
    public static final String SUCCESS_CODE = "0000";
}

package YOUmI.common.exception;

public class BadRequestException extends RuntimeException {
        
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable e) {
        super(message, e);
    }

}

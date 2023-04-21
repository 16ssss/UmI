package YOUmI.aop;

import YOUmI.util.enumeration.FAILURE;
import YOUmI.domain.MBTI.model.dto.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestControllerAdvice
public class RestControllerException {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(HttpServletRequest request, Exception e){
        StackTraceElement[] elements = e.getStackTrace();
        StackTraceElement element = elements != null && elements.length > 0 ? elements[0] : null;

        if(element != null) log.error("class: {}, lineNumber: {}, {} occured: {}",element.getClassName(), element.getLineNumber(), e.getClass(), e.getMessage());

        return ResponseEntity.badRequest().body(Response.builder().result(FAILURE.FAILURE).build());
    }

}

package YOUmI.domain.MBTI.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import YOUmI.util.enumeration.RESULT;
import YOUmI.util.enumeration.SUCCESS;
import lombok.Builder;


@Builder
public class Response {
    
    private RESULT result = SUCCESS.SUCCESS;

    private String resultCode;

    private String resultMessage;

    @JsonProperty(value="result")
    private Object resultObject;

    public String getResultCode(){
        return this.result.getCode();
    }
    public String getResultMessage(){
        return this.result.getMessage();
    }
    public Object getResultObject(){
        return this.resultObject;
    }

    // private Response() {
    //     this.result = SUCCESS.SUCCESS;
    //     this.resultObject = null;
    // }

    // private Response(RESULT result) {
    //     this.result = result;
    //     this.resultObject = null;
    // }

    // private Response(RESULT result, Object object) {
    //     this.result = result;
    //     this.resultObject = object;
    // }

}

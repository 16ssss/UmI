package YOUmI.common.dto;

import YOUmI.util.enumeration.RESULT;
import YOUmI.util.enumeration.SUCCESS;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class ResponseDTO {
    private RESULT result = SUCCESS.SUCCESS;

    private String resultCode;

    private String resultMessage;

    @JsonProperty(value = "result")
    private Object resultObject;

    public String getResultCode() {
        return this.result.getCode();
    }

    public String getResultMessage() {
        return this.result.getMessage();
    }

    public Object getResultObject() {
        return this.resultObject;
    }
}

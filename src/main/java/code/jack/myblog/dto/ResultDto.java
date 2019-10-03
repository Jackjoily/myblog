package code.jack.myblog.dto;

import code.jack.myblog.exception.CustomizeErrorCode;
import code.jack.myblog.exception.CustomizeException;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;

@Data
public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDto errOf(Integer code, String message) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }
    public static ResultDto errOf(CustomizeErrorCode noLogin) {
        return errOf(noLogin.getCode(), noLogin.getMessage());
    }
    public static ResultDto successOf() {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        return resultDto;
    }
    public static ResultDto errOf(CustomizeException e) {
        return errOf(e.getCode(), e.getMessage());
    }

    public static <T> ResultDto okOf(T t){
     ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("请求成功");
        resultDto.setData(t);
        return resultDto;
    }

}


package code.jack.myblog.advice;

import code.jack.myblog.dto.ResultDto;
import code.jack.myblog.exception.CustomizeErrorCode;
import code.jack.myblog.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import com.sun.org.glassfish.external.statistics.annotations.Reset;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handlerException(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response) {
        String contentType = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDto resultDto=null;
            if (ex instanceof CustomizeException) {
                resultDto= ResultDto.errOf((CustomizeException)ex);
            } else {
                resultDto= ResultDto.errOf(CustomizeErrorCode.SYS_ERRO);
            }
            try (PrintWriter pw = response.getWriter()) {
                response.setContentType("application/json");
                response.setCharacterEncoding("utf-8");
                pw.write(JSON.toJSONString(resultDto));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  null;
        } else {
            if (ex instanceof CustomizeException) {
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message", CustomizeErrorCode.SYS_ERRO.getMessage());
            }
        }
        return new ModelAndView("error");
    }

}

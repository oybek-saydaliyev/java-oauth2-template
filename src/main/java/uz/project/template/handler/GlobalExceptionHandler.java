package uz.project.template.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public Map<String, Object> exception(Exception e) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", e.getMessage());
        result.put("errorCode", 500);
        result.put("stackTrace", e.getStackTrace());
        result.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        return result;
    }

}

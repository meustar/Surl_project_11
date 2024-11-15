package com.koreait.surl_project_11.global.exceptionHandlers;

import com.koreait.surl_project_11.global.exceptions.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    // 더 광범위. 외적인 상황
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception ex) {
        log.debug("handleException 1");
        return ex.getMessage();
    }

    // Global 이지만. 한정적인 상황
    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public String handleException(GlobalException ex) {
        log.debug("handleException 2");
        return ex.getMessage();
    }

}

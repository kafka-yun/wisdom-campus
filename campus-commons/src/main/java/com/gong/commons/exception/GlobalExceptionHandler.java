package com.gong.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CampusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(CampusException e){
        log.error("系统异常:{}",e.getMessage(),e);
        return new RestErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e){
        log.error("系统异常:{}",e.getMessage(),e);
        return new RestErrorResponse(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodException(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        /*存放错误信息*/
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(i->{errors.add(i.getDefaultMessage());});
        String errorMassage = String.join( ",",errors);
        log.error("输入参数异常:{}",e.getMessage(),errorMassage);
        return new RestErrorResponse(errorMassage);
    }


}

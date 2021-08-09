package com.bitchoi.landbnbserver.exception.handler;

import com.bitchoi.landbnbserver.exception.BusinessException;
import com.bitchoi.landbnbserver.exception.error.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiError> handleBusinessException(BusinessException ex) {
        ApiError apiErr = new ApiError(ex.getStatus(), ex.getMessage(), ex.getErrorCode().getCode());
        return new ResponseEntity<>(apiErr, apiErr.getStatus());
    }
}

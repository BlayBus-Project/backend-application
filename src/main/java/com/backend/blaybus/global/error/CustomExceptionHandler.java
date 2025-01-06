package com.backend.blaybus.global.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(HttpServletRequest request, CustomException ex) {
        String path = request.getRequestURI(); // 요청 URL 가져오기
        ErrorResponse errorDto = ErrorResponse.toErrorDto(ex, path);

        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(ex.getState()));
    }

    @ExceptionHandler({IllegalArgumentException.class, NoSuchElementException.class})
    public ResponseEntity<ErrorResponse> handleCommonException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI(); // 요청 URL 가져오기
        Error errorType = ex instanceof IllegalArgumentException ? Error.ILLEGAL_ARGUMENT : Error.NO_SUCH_ELEMENT;

        CustomException customException = new CustomException(errorType);
        ErrorResponse errorDto = ErrorResponse.toErrorDto(customException, path);

        return new ResponseEntity<>(errorDto, HttpStatus.valueOf(customException.getState()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request) {
        String path = request.getRequestURI();
        CustomException customException = new CustomException(Error.ACCESS_DENIED);
        ErrorResponse errorDto = ErrorResponse.toErrorDto(customException, path);

        return new ResponseEntity<>(errorDto, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        CustomException customException = new CustomException(Error.INTERNAL_SERVER_ERROR);
        ErrorResponse errorDto = ErrorResponse.toErrorDto(customException, path);

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

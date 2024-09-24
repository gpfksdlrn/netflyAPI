package com.api.project.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.api.project.CommonResponse;
import lombok.extern.log4j.Log4j2;
import org.hibernate.QueryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {
    public final static String DEFAULT_ERROR_COMMENT = "알수없는 오류, 관리자에게 문의해주세요";
    private static final String EXCEPTION_LOG_FORMAT = "Class : {}, Message : {}";

    /**
     * 일반적인 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);
        return new ResponseEntity<>(CommonResponse.responseFail(DEFAULT_ERROR_COMMENT), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 인증 클래스 비즈니스 에러 발생시 에러 핸들러
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleAuthExceptionHandler(CustomException e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);
        return new ResponseEntity<>(CommonResponse.responseFail(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Query 에러 예외처리
     */
    @ExceptionHandler(QueryException.class)
    public ResponseEntity<Object> handleQueryException(QueryException e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);
        return new ResponseEntity<>(CommonResponse.responseFail(DEFAULT_ERROR_COMMENT), HttpStatus.BAD_REQUEST);
    }

    /**
     * Json 파싱 예외처리
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);

        String message = "JSON 파싱 에러 : ";
        if (e.getCause() instanceof JsonMappingException jme) {
            String field = jme.getPath().isEmpty() ? "" : jme.getPath().get(0).getFieldName();
            message += "필드 '" + field + "'에서 문제가 발생했습니다. ";
        }
        message += "다시한번 확인해주세요.";
        return new ResponseEntity<>(CommonResponse.responseFail(message), HttpStatus.BAD_REQUEST);
    }

    /**
     * RequestParam 예외처리
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(MissingServletRequestParameterException e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);
        String name = e.getParameterName();
        return new ResponseEntity<>(CommonResponse.responseFail(name + " 은 필수값입니다."), HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청값의 valid 조건에 부합하지 않을시 리턴
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(EXCEPTION_LOG_FORMAT, e.getClass().getSimpleName(), e.getMessage(), e);
        String errorMsg = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return new ResponseEntity<>(CommonResponse.responseFail(errorMsg), HttpStatus.BAD_REQUEST);
    }
}

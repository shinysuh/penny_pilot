package com.jenna.pennypilot.core.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        logException(e);
        ErrorDTO error = buildErrorDTO(e);
        HttpStatus status = determineStatus(e);

        return new ResponseEntity<>(error, status);
    }

    // 에러 로깅
    private void logException(Exception e) {
        if (e instanceof GlobalException) {
            log.warn("Handled GlobalException: {}", e.getMessage());
        } else {
            log.error("Unhandled exception caught", e);
        }
    }

    // ErrorDTO 생성
    private ErrorDTO buildErrorDTO(Exception e) {
        HttpStatus status = determineStatus(e);
        String code = "NO_CATCH_ERROR";
        String message = e.getMessage();
        String exClassName = e.getClass().getSimpleName();

        if (e instanceof GlobalException ge) {
            code = ge.getErrorCode().getCode();
            message = ge.getErrorCode().getMessage();
        }

        return ErrorDTO.builder()
                .exception(exClassName)
                .error(status.getReasonPhrase())
                .code(code)
                .message(message)
                .status(status.value())
                .build();
    }

    // status 설정
    private HttpStatus determineStatus(Exception e) {
        if (e instanceof GlobalException ge) {
            return ge.getErrorCode().getStatus();
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return METHOD_NOT_ALLOWED;
        }
        return INTERNAL_SERVER_ERROR;
    }
}


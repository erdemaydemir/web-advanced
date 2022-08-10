package com.forguta.libs.web.core.model.response;

import com.forguta.libs.web.core.model.response.body.ErrorResponseBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class ErrorResponseEntity {

    public static <T> ResponseEntity<ErrorResponseBody<T>> notValid(String failedMessage, String exception, List<String> validations) {
        ErrorResponseBody<T> errorResponseBody = ErrorResponseBody.<T>builder()
                .failedMessage(failedMessage)
                .requestPath(getRequestPath())
                .exception(exception)
                .validations(validations)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponseBody);
    }


    public static <T> ResponseEntity<ErrorResponseBody<T>> internalError(T body, String failedMessage, String exception) {
        ErrorResponseBody<T> errorResponseBody = ErrorResponseBody.<T>builder()
                .content(body)
                .failedMessage(failedMessage)
                .requestPath(getRequestPath())
                .exception(exception)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseBody);
    }

    public static <T> ResponseEntity<ErrorResponseBody<T>> withoutBody(HttpStatus status, HttpHeaders headers, String failedMessage, String exception, List<String> validations) {
        ErrorResponseBody<T> errorResponseBody = ErrorResponseBody.<T>builder()
                .failedMessage(failedMessage)
                .requestPath(getRequestPath())
                .exception(exception)
                .validations(validations)
                .build();
        return ResponseEntity.status(status)
                .headers(headers)
                .body(errorResponseBody);
    }

    public static <T> ResponseEntity<ErrorResponseBody<T>> withBody(HttpStatus status, HttpHeaders headers, T body,
                                                                    String failedMessage, String exception, List<String> validations) {
        ErrorResponseBody<T> errorResponseBody = ErrorResponseBody.<T>builder()
                .content(body)
                .failedMessage(failedMessage)
                .requestPath(getRequestPath())
                .exception(exception)
                .validations(validations)
                .build();
        return ResponseEntity.status(status)
                .headers(headers)
                .body(errorResponseBody);
    }

    private static String getRequestPath() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI).toString();
    }

}

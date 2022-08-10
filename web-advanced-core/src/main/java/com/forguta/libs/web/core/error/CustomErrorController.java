package com.forguta.libs.web.core.error;

import com.forguta.libs.web.core.model.response.ErrorResponseEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class CustomErrorController extends AbstractErrorController {

    private final ErrorProperties properties;

    public CustomErrorController(ErrorAttributes attributes) {
        super(attributes);
        this.properties = new ErrorProperties();
    }

    @RequestMapping
    @ResponseBody
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        Throwable throwable = getError(request);
        List<String> validations = new ArrayList<>();
        if (throwable instanceof MethodArgumentNotValidException)
            validations = getValidations((MethodArgumentNotValidException) throwable);
        String exception = throwable.getClass().getSimpleName();
        String failedMessage = throwable.getMessage();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ErrorResponseEntity.withoutBody(status, headers, failedMessage, exception, validations);
    }

    public Throwable getError(HttpServletRequest request) {
        Throwable error = (Throwable) request.getAttribute(CustomHandlerExceptionResolver.ERROR_NAME);
        if (error == null) {
            error = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }

        if (error != null) {
            while (error instanceof Exception && error.getCause() != null) {
                error = error.getCause();
            }
        } else {
            String message = (String) request.getAttribute("javax.servlet.error.message");
            if (StringUtils.isNotEmpty(message)) {
                HttpStatus status = this.getStatus(request);
                message = "Unknown Exception With " + status.value() + " " + status.getReasonPhrase();
            }
            error = new Exception(message);
        }

        return error;
    }

    public List<String> getValidations(MethodArgumentNotValidException ex) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        return details;
    }
}
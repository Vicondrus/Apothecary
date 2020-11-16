package com.ds.project.apothecary.controllers;

import com.ds.project.apothecary.dtos.ApiErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import java.util.Collections;
import java.util.List;

/**
 * The type Global exception controller.
 */
@ControllerAdvice
public class GlobalExceptionController {

    /**
     * Provides handling for exceptions throughout this service. @param ex the
     * ex
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler()
    public final ResponseEntity<ApiErrorDto>
    handleException(final Exception ex,
                    final WebRequest request) {
        HttpHeaders
                headers =
                new HttpHeaders();
        List<String>
                errors =
                Collections.singletonList(ex.getMessage());
        HttpStatus
                status =
                HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(ex, new ApiErrorDto(errors), headers,
                status,
                request);
    }

    /**
     * A single place to customize the response body of all Exception types.
     *
     * @param ex      the ex
     * @param body    the body
     * @param headers the headers
     * @param status  the status
     * @param request the request
     * @return the response entity
     */
    protected ResponseEntity<ApiErrorDto> handleExceptionInternal(
            final Exception ex,
            final ApiErrorDto body,
            final HttpHeaders headers,
            final HttpStatus status,
            final WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex,
                    WebRequest.SCOPE_REQUEST);
        }

        return new ResponseEntity<>(body, headers, status);
    }
}

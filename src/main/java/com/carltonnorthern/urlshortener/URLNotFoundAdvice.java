package com.carltonnorthern.urlshortener;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class URLNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(URLNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String URLNotFoundHandler(URLNotFoundException ex) {
        return ex.getMessage();
    }
}
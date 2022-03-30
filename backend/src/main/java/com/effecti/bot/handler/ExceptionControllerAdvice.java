package com.effecti.bot.handler;

import com.effecti.bot.exception.BiddingRequestException;
import com.effecti.bot.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Date;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = BiddingRequestException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleBiddingRequestException(BiddingRequestException exception) {
        // TODO Send problem notification ...
        System.out.println("Send problem notification ...");
        return new ExceptionResponse(Collections.singletonList(exception.getMessage()),
                exception.getClass().getSimpleName(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date());
    }
}

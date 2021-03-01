package com.leovegas.wallet.controller;

import com.leovegas.wallet.exception.AccountNotExistException;
import com.leovegas.wallet.exception.InvalidRequestException;
import com.leovegas.wallet.model.dto.ProblemDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(value = InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProblemDetailsDto handleInvalidRequestException(InvalidRequestException exception) {
        return new ProblemDetailsDto(
                exception.getMessage(), "provided data are not correct", HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = AccountNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ProblemDetailsDto handleAccountNotExistException(AccountNotExistException exception) {
        return new ProblemDetailsDto(
                exception.getMessage(), "account is not exist", HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ProblemDetailsDto handleUnExpectException(RuntimeException exception) {
        return new ProblemDetailsDto(
                exception.getMessage(), "unexpected exception happened", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ProblemDetailsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(error -> errors.append(error.getDefaultMessage()));
        return new ProblemDetailsDto(
                errors.toString(), "request data are not valid", HttpStatus.BAD_REQUEST.value());
    }

}

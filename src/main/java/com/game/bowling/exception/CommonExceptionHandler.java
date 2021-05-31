package com.game.bowling.exception;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(GameNotFinishedException.class)
    public ResponseEntity<Object> notFinished(final GameNotFinishedException e) {
        logger.error("Game with provided id is not finished!", e);
        return (e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Object> gameNotFound(final GameNotFoundException e) {
        logger.error("Provided game id is not found!", e);
        return (e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @ExceptionHandler(AnotherPlayerExpectedException.class)
    public ResponseEntity<Object> notPlayersTurn(final AnotherPlayerExpectedException e) {
        logger.error("Not provided players turn!", e);
        return(e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> notPlayersTurn(final PlayerNotFoundException e) {
        logger.error("Player with provided id was not found!", e);
        return(e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFound(final NotFoundException e) {
        logger.error("The information needed not found!", e);
        return (e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(final Exception e) {
        logger.error("Exception during execution of Bowling game Application", e);
        return (e != null ? errorResponse(e.getMessage()) : errorResponse("Unknown error"));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return errorResponse(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    private ResponseEntity<Object> errorResponse(String message){
        if("Unknown error".equals(message)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(message));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message));
    }
}


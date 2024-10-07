package com.utbm.da50.freelyform.controller;

import com.utbm.da50.freelyform.dto.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Ressource inconnue / non trouvée", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleBadPayload(IllegalArgumentException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Ressource non traitable", ex.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionResponse> handleBadPayload(IllegalStateException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Requête non traitable", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleAuthErrorBadMail(UsernameNotFoundException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Erreur d'authentification", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleAuthErrorBadCreds(BadCredentialsException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Crédentiels invalides", ex.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleBadJson(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(
            new ExceptionResponse("Erreur HTTP", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleAllOthersExceptions(Exception ex) {
        return new ResponseEntity<>( 
            new ExceptionResponse("Erreur serveur", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
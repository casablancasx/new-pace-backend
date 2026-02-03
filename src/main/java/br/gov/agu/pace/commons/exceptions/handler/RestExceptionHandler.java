package br.gov.agu.pace.commons.exceptions.handler;

import br.gov.agu.pace.commons.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {



    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                404,
                "Resource Not Found",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(404).body(err);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> resourceAlreadyExists(ResourceAlreadyExistsException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                409,
                "Resource Already Exists",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(409).body(err);
    }


    @ExceptionHandler(FileInvalidException.class)
    public ResponseEntity<StandardError> fileInvalid(FileInvalidException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                400,
                "File Invalid",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(err);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<StandardError> userUnauthorized(UserUnauthorizedException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                401,
                "User Unauthorized",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(401).body(err);
    }

    @ExceptionHandler(PlanilhaException.class)
    public ResponseEntity<StandardError> planilhaMapperException(PlanilhaException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                400,
                "Planilha Exception",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(err);
    }

    @ExceptionHandler(EscalaException.class)
    public ResponseEntity<StandardError> escalaException(EscalaException e, HttpServletRequest request) {
        StandardError err = new StandardError(
                java.time.Instant.now(),
                400,
                "Escala Exception",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(400).body(err);
    }

}

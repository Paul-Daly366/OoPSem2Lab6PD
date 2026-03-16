package ie.atu.reservationapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handle validation errors globally
    @ExceptionHandler(value={MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String, String> errors = new HashMap<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName,errorMessage);
        }
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ReservationConflictException.class)
    public ResponseEntity<String> handleConflict(ReservationConflictException ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ReservationNotFoundException ex){

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
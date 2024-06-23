package med.gomez.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity dealWithError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity dealWithError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(dataValidationError::new).toList();

        return ResponseEntity.badRequest().body(errores);
    }

    private record dataValidationError(String campo, String error){

        public dataValidationError(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }


}

package med.gomez.api.infra.errors;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // Permite manejar excepciones de forma global en todos los controladores
public class ErrorHandler {

    // Maneja la excepción EntityNotFoundException devolviendo una respuesta 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity dealWithError404() {
        return ResponseEntity.notFound().build(); // Construye y devuelve una respuesta HTTP 404
    }

    // Maneja la excepción MethodArgumentNotValidException devolviendo una respuesta 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity dealWithError400(MethodArgumentNotValidException e) {
        // Convierte los errores de campo a una lista de objetos dataValidationError
        var errores = e.getFieldErrors().stream().map(dataValidationError::new).toList();

        return ResponseEntity.badRequest().body(errores); // Construye y devuelve una respuesta HTTP 400 con los errores de validación en el cuerpo
    }

    // Clase record para representar errores de validación de datos
    private record dataValidationError(String campo, String error) {

        // Constructor que convierte un FieldError en un dataValidationError
        public dataValidationError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}

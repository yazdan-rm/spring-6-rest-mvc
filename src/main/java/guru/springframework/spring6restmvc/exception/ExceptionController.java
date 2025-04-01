package guru.springframework.spring6restmvc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> handleNotFoundException(){
        System.out.println(" not found exception");
        return ResponseEntity.notFound().build();
    }
}

package gleb.blum.examensarbete.exceptions;


import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleItemFoundException(NotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }


}

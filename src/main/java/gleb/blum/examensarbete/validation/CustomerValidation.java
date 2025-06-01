package gleb.blum.examensarbete.validation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CustomerValidation {
    @NotBlank
    private String name;

    @Email
    private String email;
}
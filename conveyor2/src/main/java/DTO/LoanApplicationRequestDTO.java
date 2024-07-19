package DTO;

import DTO.NotNull;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanApplicationRequestDTO {
    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Term must not be null")
    @Min(value = 1, message = "Term must be greater than 0")
    private Integer term;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    private String middleName;

    @Email(message = "Email should be valid")
    private String email;

    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    @NotBlank(message = "Passport series must not be blank")
    private String passportSeries;

    @NotBlank(message = "Passport number must not be blank")
    private String passportNumber;
}

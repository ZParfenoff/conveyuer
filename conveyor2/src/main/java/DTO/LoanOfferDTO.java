package DTO;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanOfferDTO {
    @NotNull(message = "Application ID must not be null")
    @Positive(message = "Application ID must be positive")
    private Long applicationId;

    @NotNull(message = "Requested amount must not be null")
    @Positive(message = "Requested amount must be positive")
    private BigDecimal requestedAmount;

    @NotNull(message = "Total amount must not be null")
    @PositiveOrZero(message = "Total amount must be positive or zero")
    private BigDecimal totalAmount;

    @NotNull(message = "Term must not be null")
    @Positive(message = "Term must be positive")
    private Integer term;

    @NotNull(message = "Monthly payment must not be null")
    @PositiveOrZero(message = "Monthly payment must be positive or zero")
    private BigDecimal monthlyPayment;

    @NotNull(message = "Rate must not be null")
    @PositiveOrZero(message = "Rate must be positive or zero")
    private BigDecimal rate;

    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
}

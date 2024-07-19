package DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentScheduleElement {
    @NotNull(message = "Number must not be null")
    @Positive(message = "Number must be positive")
    private Integer number;

    @NotNull(message = "Date must not be null")
    private LocalDate date;

    @NotNull(message = "Total payment must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total payment must be greater than 0")
    private BigDecimal totalPayment;

    @NotNull(message = "Interest payment must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Interest payment must be greater than 0")
    private BigDecimal interestPayment;

    @NotNull(message = "Debt payment must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Debt payment must be greater than 0")
    private BigDecimal debtPayment;

    @NotNull(message = "Remaining debt must not be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Remaining debt must be greater than 0")
    private BigDecimal remainingDebt;
}
package DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDTO {
    @NotNull(message = "Amount must not be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "Term must not be null")
    @Positive(message = "Term must be positive")
    private Integer term;

    @NotNull(message = "Monthly payment must not be null")
    @Positive(message = "Monthly payment must be positive")
    private BigDecimal monthlyPayment;

    @NotNull(message = "Rate must not be null")
    @PositiveOrZero(message = "Rate must be positive or zero")
    private BigDecimal rate;

    @NotNull(message = "PSK must not be null")
    @PositiveOrZero(message = "PSK must be positive or zero")
    private BigDecimal psk;

    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    @NotEmpty(message = "Payment schedule must not be empty")
    private List<PaymentScheduleElement> paymentSchedule;


}

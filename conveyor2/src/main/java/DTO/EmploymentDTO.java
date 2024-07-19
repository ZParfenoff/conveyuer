package DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDTO {
    @NotNull(message = "Employment status must not be null")
    private EmploymentStatus employmentStatus;

    @NotBlank(message = "Employer INN must not be blank")
    private String employerINN;

    @NotNull(message = "Salary must not be null")
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;

    @NotNull(message = "Position must not be null")
    private Position position;

    @NotNull(message = "Total work experience must not be null")
    @PositiveOrZero(message = "Total work experience must be positive or zero")
    private Integer workExperienceTotal;

    @NotNull(message = "Current work experience must not be null")
    @PositiveOrZero(message = "Current work experience must be positive or zero")
    private Integer workExperienceCurrent;

    // Перечисление (Enum) для статуса занятости (employmentStatus)
    public enum EmploymentStatus {
        EMPLOYED,
        UNEMPLOYED,
        SELF_EMPLOYED,
        CONTRACTOR,
        STUDENT,
        RETIRED,
        OTHER
    }

    // Перечисление (Enum) для должности (position)
    public enum Position {
        MANAGER,
        DEVELOPER,
        ANALYST,
        DESIGNER,
        CONSULTANT,
        ADMINISTRATOR,
        OTHER
    }
}
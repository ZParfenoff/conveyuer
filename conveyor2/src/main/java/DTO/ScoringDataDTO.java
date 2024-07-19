package DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoringDataDTO {
    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.0", message = "Amount must be greater than or equal to 0")
    private BigDecimal amount;

    @NotNull(message = "Term must not be null")
    @Min(value = 1, message = "Term must be greater than 0")
    private Integer term;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    private String middleName;

    @NotNull(message = "Gender must not be null")
    private Gender gender;

    @NotNull(message = "Birthdate must not be null")
    @Past(message = "Birthdate must be in the past")
    private LocalDate birthdate;

    @NotBlank(message = "Passport series must not be blank")
    private String passportSeries;

    @NotBlank(message = "Passport number must not be blank")
    private String passportNumber;

    @NotNull(message = "Passport issue date must not be null")
    private LocalDate passportIssueDate;

    @NotBlank(message = "Passport issue branch must not be blank")
    private String passportIssueBranch;

    @NotNull(message = "Marital status must not be null")
    private MaritalStatus maritalStatus;

    @NotNull(message = "Dependent amount must not be null")
    @Min(value = 0, message = "Dependent amount must be greater than or equal to 0")
    private Integer dependentAmount;

    @NotNull(message = "Employment details must not be null")
    private EmploymentDTO employment;

    @NotBlank(message = "Account must not be blank")
    private String account;

    @NotNull(message = "Insurance enabled flag must not be null")
    private Boolean isInsuranceEnabled;

    @NotNull(message = "Salary client flag must not be null")
    private Boolean isSalaryClient;

    // Перечисление (Enum) для пола (Gender)
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }

    // Перечисление (Enum) для семейного положения (MaritalStatus)
    public enum MaritalStatus {
        SINGLE,
        MARRIED,
        DIVORCED,
        WIDOWED
    }
}
package offers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "loan")
public class LoanConfig {
    private BigDecimal baseRate;
    private BigDecimal insuranceCost;
    private BigDecimal insuranceRateDiscount;
    private BigDecimal salaryClientRateDiscount;

    // Геттеры и сеттеры
    public BigDecimal getBaseRate() { return baseRate; }
    public void setBaseRate(BigDecimal baseRate) { this.baseRate = baseRate; }

    public BigDecimal getInsuranceCost() { return insuranceCost; }
    public void setInsuranceCost(BigDecimal insuranceCost) { this.insuranceCost = insuranceCost; }

    public BigDecimal getInsuranceRateDiscount() { return insuranceRateDiscount; }
    public void setInsuranceRateDiscount(BigDecimal insuranceRateDiscount) { this.insuranceRateDiscount = insuranceRateDiscount; }

    public BigDecimal getSalaryClientRateDiscount() { return salaryClientRateDiscount; }
    public void setSalaryClientRateDiscount(BigDecimal salaryClientRateDiscount) { this.salaryClientRateDiscount = salaryClientRateDiscount; }
}

package DTO;

import java.math.BigDecimal;
import java.util.List;

public class CreditDTO {
    private BigDecimal amount;
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private BigDecimal psk;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;
    private List<PaymentScheduleElement> paymentSchedule;

    // Пустой конструктор
    public CreditDTO() {
    }

    // Конструктор со всеми параметрами
    public CreditDTO(BigDecimal amount, Integer term, BigDecimal monthlyPayment, BigDecimal rate,
                     BigDecimal psk, Boolean isInsuranceEnabled, Boolean isSalaryClient,
                     List<PaymentScheduleElement> paymentSchedule) {
        this.amount = amount;
        this.term = term;
        this.monthlyPayment = monthlyPayment;
        this.rate = rate;
        this.psk = psk;
        this.isInsuranceEnabled = isInsuranceEnabled;
        this.isSalaryClient = isSalaryClient;
        this.paymentSchedule = paymentSchedule;
    }

    // Геттеры и сеттеры для всех полей
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getPsk() {
        return psk;
    }

    public void setPsk(BigDecimal psk) {
        this.psk = psk;
    }

    public Boolean getIsInsuranceEnabled() {
        return isInsuranceEnabled;
    }

    public void setIsInsuranceEnabled(Boolean isInsuranceEnabled) {
        this.isInsuranceEnabled = isInsuranceEnabled;
    }

    public Boolean getIsSalaryClient() {
        return isSalaryClient;
    }

    public void setIsSalaryClient(Boolean isSalaryClient) {
        this.isSalaryClient = isSalaryClient;
    }

    public List<PaymentScheduleElement> getPaymentSchedule() {
        return paymentSchedule;
    }

    public void setPaymentSchedule(List<PaymentScheduleElement> paymentSchedule) {
        this.paymentSchedule = paymentSchedule;
    }
}

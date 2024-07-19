package org.example.conveyor2;

import DTO.CreditDTO;
import DTO.ScoringDataDTO;
import calculation.LoanScoring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;


public class LoanScoringTest {

    @InjectMocks
    private LoanScoring loanScoring;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loanScoring.baseRate = new BigDecimal("10.0");
        loanScoring.insuranceCost = new BigDecimal("100.0");
        loanScoring.insuranceRateDiscount = new BigDecimal("1.0");
        loanScoring.salaryClientRateDiscount = new BigDecimal("0.5");
    }

    @Test
    public void testCalculateCredit_WithInsuranceAndSalaryClient() {
        ScoringDataDTO request = new ScoringDataDTO();
        request.setAmount(BigDecimal.valueOf(305000));
        request.setTerm(24);
        request.setIsInsuranceEnabled(true);
        request.setIsSalaryClient(true);

        CreditDTO result = loanScoring.calculateCredit(request);

        BigDecimal expectedRate = BigDecimal.valueOf(10.0)
                .subtract(BigDecimal.valueOf(0.5))
                .subtract(BigDecimal.valueOf(0.3));


        assertEquals(BigDecimal.valueOf(305000), result.getAmount());
        assertEquals(24, result.getTerm());
        assertEquals(
                new BigDecimal("13868.53").setScale(2, BigDecimal.ROUND_HALF_UP),
                result.getMonthlyPayment().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        assertEquals(24, result.getPaymentSchedule().size());
    }

    @Test
    public void testCalculateCredit_WithoutInsuranceAndSalaryClient() {
        ScoringDataDTO request = new ScoringDataDTO();
        request.setAmount(BigDecimal.valueOf(300000));
        request.setTerm(24);
        request.setIsInsuranceEnabled(false);
        request.setIsSalaryClient(false);

        CreditDTO result = loanScoring.calculateCredit(request);

        assertEquals(BigDecimal.valueOf(10.0), result.getRate());
        assertEquals(BigDecimal.valueOf(300000), result.getAmount());
        assertEquals(24, result.getTerm());
        assertEquals(
                new BigDecimal("13843.48").setScale(2, BigDecimal.ROUND_HALF_UP),
                result.getMonthlyPayment().setScale(2, BigDecimal.ROUND_HALF_UP)
        );
        assertEquals(24, result.getPaymentSchedule().size());
    }
}

package org.example.conveyor2;
import DTO.LoanApplicationRequestDTO;
import DTO.LoanOfferDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loanService.baseRate = new BigDecimal("10.0");
        loanService.insuranceCost = new BigDecimal("100.0");
        loanService.insuranceRateDiscount = new BigDecimal("1.0");
        loanService.salaryClientRateDiscount = new BigDecimal("0.5");
    }

    @Test
    public void testCalculateLoanOffers() {
        LoanApplicationRequestDTO request = new LoanApplicationRequestDTO();
        request.setAmount(new BigDecimal("1000.0"));
        request.setTerm(12);

        List<LoanOfferDTO> offers = loanService.calculateLoanOffers(request);

        assertEquals(4, offers.size());

        for (LoanOfferDTO offer : offers) {
            assertEquals(request.getAmount(), offer.getRequestedAmount());
            assertEquals(request.getTerm(), offer.getTerm());
            assertNotNull(offer.getTotalAmount());
            assertNotNull(offer.getMonthlyPayment());
            assertNotNull(offer.getRate());
            assertNotNull(offer.getIsInsuranceEnabled());
            assertNotNull(offer.getIsSalaryClient());
        }

        assertTrue(offers.get(0).getRate().compareTo(offers.get(3).getRate()) < 0);
    }
}
package org.example.conveyor2;

import DTO.LoanApplicationRequestDTO;
import DTO.LoanOfferDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
public class LoanService {

    @Value("${base.rate}")
    public BigDecimal baseRate;

    @Value("${insurance.cost}")
    public BigDecimal insuranceCost;

    @Value("${insurance.rate.discount}")
    public BigDecimal insuranceRateDiscount;

    @Value("${salary.client.rate.discount}")
    public BigDecimal salaryClientRateDiscount;

    public List<LoanOfferDTO> calculateLoanOffers(LoanApplicationRequestDTO request) {
        List<LoanOfferDTO> offers = new ArrayList<>();

        boolean[] insuranceOptions = {false, true};
        boolean[] salaryClientOptions = {false, true};

        for (boolean isInsuranceEnabled : insuranceOptions) {
            for (boolean isSalaryClient : salaryClientOptions) {
                LoanOfferDTO offer = createLoanOffer(request, isInsuranceEnabled, isSalaryClient);
                offers.add(offer);
            }
        }

        offers.sort(Comparator.comparing(LoanOfferDTO::getRate));

        return offers;
    }

    private LoanOfferDTO createLoanOffer(LoanApplicationRequestDTO request,
                                         boolean isInsuranceEnabled,
                                         boolean isSalaryClient) {
        BigDecimal rate = baseRate;
        BigDecimal totalAmount = request.getAmount();

        if (isInsuranceEnabled) {
            totalAmount = totalAmount.add(insuranceCost);
            rate = rate.subtract(insuranceRateDiscount);
        }

        if (isSalaryClient) {
            rate = rate.subtract(salaryClientRateDiscount);
        }

        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, request.getTerm());

        LoanOfferDTO offer = new LoanOfferDTO();
        offer.setApplicationId(1L); // Placeholder for actual application ID
        offer.setRequestedAmount(request.getAmount());
        offer.setTotalAmount(totalAmount);
        offer.setTerm(request.getTerm());
        offer.setMonthlyPayment(monthlyPayment);
        offer.setRate(rate);
        offer.setIsInsuranceEnabled(isInsuranceEnabled);
        offer.setIsSalaryClient(isSalaryClient);

        return offer;
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, int term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100 * 12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal numerator = totalAmount.multiply(monthlyRate).multiply((BigDecimal.ONE.add(monthlyRate)).pow(term));
        BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(term).subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }
}

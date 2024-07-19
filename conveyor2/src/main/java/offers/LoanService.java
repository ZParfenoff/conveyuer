package offers;

import DTO.LoanApplicationRequestDTO;
import DTO.LoanOfferDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class LoanService {
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);
    private final LoanConfig loanConfig;
    BigDecimal baseRate = loanConfig.getBaseRate();
    BigDecimal insuranceCost = loanConfig.getInsuranceCost();
    BigDecimal insuranceRateDiscount = loanConfig.getInsuranceRateDiscount();
    BigDecimal salaryClientRateDiscount = loanConfig.getSalaryClientRateDiscount();
    @ApiOperation(value = "Calculate loan offers based on application request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated loan offers"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public List<LoanOfferDTO> calculateLoanOffers( LoanApplicationRequestDTO request) {

        List<LoanOfferDTO> offers = new ArrayList<>();

        boolean[] insuranceOptions = {false, true};
        boolean[] salaryClientOptions = {false, true};

        for (boolean isInsuranceEnabled : insuranceOptions) {
            for (boolean isSalaryClient : salaryClientOptions) {
                try {
                    LoanOfferDTO offer = createLoanOffer(request, isInsuranceEnabled, isSalaryClient);
                    offers.add(offer);
                } catch (Exception e) {logger.error("Error creating loan offer: {}", e.getMessage(), e);}
            }
        }

        offers.sort(Comparator.comparing(LoanOfferDTO::getRate));

        return offers;
    }

    private LoanOfferDTO createLoanOffer(LoanApplicationRequestDTO request,
                                         boolean isInsuranceEnabled,
                                         boolean isSalaryClient) throws Exception {

        if (request == null || request.getAmount() == null || request.getTerm() <= 0) {
            throw new Exception("Invalid loan application request");
        }

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
        try {
            BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100 * 12), 10, BigDecimal.ROUND_HALF_UP);
            BigDecimal numerator = totalAmount.multiply(monthlyRate).multiply((BigDecimal.ONE.add(monthlyRate)).pow(term));
            BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(term).subtract(BigDecimal.ONE);
            return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
        } catch (Exception e) {
            try {
                throw new Exception("Error calculating monthly payment", e);
            } catch (Exception ex) {
                logger.error("Error calculating monthly payment: {}", e.getMessage(), e);
                throw new RuntimeException(ex);
            }
        }
    }
}

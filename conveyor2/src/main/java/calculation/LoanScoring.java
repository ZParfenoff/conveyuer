package calculation;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import DTO.CreditDTO;
import DTO.ScoringDataDTO;
import DTO.CreditDTOMapper;
import DTO.PaymentScheduleElement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@Service

@RestController
@Api(tags = "Loan Scoring API", description = "Operations related to loan scoring")
public class LoanScoring {
    private static final Logger logger = LoggerFactory.getLogger(LoanScoring.class);
    public BigDecimal baseRate;
    public BigDecimal insuranceCost;
    public BigDecimal insuranceRateDiscount;
    public BigDecimal salaryClientRateDiscount;
    @PostMapping("/calculateCredit")
    @ApiOperation(value = "Calculate credit based on scoring data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated credit"),
            @ApiResponse(code = 400, message = "Validation error in input data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public CreditDTO calculateCredit(@RequestBody ScoringDataDTO request) {
        try {
            BigDecimal rate = baseRate;
            BigDecimal totalAmount = request.getAmount();
            if (rate == null) {
                logger.error("Base rate is null");
                throw new IllegalArgumentException("Base rate cannot be null");
            }

            if (totalAmount == null) {
                logger.error("Total amount is null");
                throw new IllegalArgumentException("Total amount cannot be null");
            }
            if (request.getIsInsuranceEnabled()) {
                totalAmount = totalAmount.add(insuranceCost);
                rate = rate.subtract(insuranceRateDiscount);
            }

            if (request.getIsSalaryClient()) {
                rate = rate.subtract(salaryClientRateDiscount);
            }

            BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, request.getTerm());
            List<PaymentScheduleElement> paymentSchedule = calculatePaymentSchedule(totalAmount, rate, request.getTerm());

            CreditDTO creditDTO = CreditDTOMapper.toCreditDTO(request, monthlyPayment, rate, paymentSchedule);

            logger.info("Credit calculation successful for request: {}", request);
            return creditDTO;
        } catch (Exception e) {
            System.err.println("Error calculating credit: " + e.getMessage());
            logger.error("Error calculating credit: {}", e.getMessage(), e);
            throw new RuntimeException("Error calculating credit", e);
        }
    }
    private void validateRequest(ScoringDataDTO request) throws IllegalArgumentException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        if (request.getTerm() <= 0) {
            throw new IllegalArgumentException("Term must be greater than zero");
        }
    }

    private BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, int term) {
        try {
            BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100 * 12), 10, BigDecimal.ROUND_HALF_UP);
            BigDecimal numerator = totalAmount.multiply(monthlyRate).multiply((BigDecimal.ONE.add(monthlyRate)).pow(term));
            BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(term).subtract(BigDecimal.ONE);
            return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
        } catch (ArithmeticException e) {
            logger.error("Error calculating monthly payment: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error calculating monthly payment: " + e.getMessage(), e);
        }
    }

    private List<PaymentScheduleElement> calculatePaymentSchedule(BigDecimal totalAmount, BigDecimal rate, int term) throws IllegalArgumentException{
       try {
           List<PaymentScheduleElement> schedule = new ArrayList<>();
           BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100 * 12), 10, BigDecimal.ROUND_HALF_UP);
           BigDecimal remainingDebt = totalAmount;

           for (int i = 1; i <= term; i++) {
               BigDecimal interestPayment = remainingDebt.multiply(monthlyRate);
               BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, term);
               BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
               remainingDebt = remainingDebt.subtract(debtPayment);

               PaymentScheduleElement element = new PaymentScheduleElement();
               element.setNumber(i);
               element.setDate(LocalDate.now().plusMonths(i));
               element.setTotalPayment(monthlyPayment);
               element.setInterestPayment(interestPayment);
               element.setDebtPayment(debtPayment);
               element.setRemainingDebt(remainingDebt);

               schedule.add(element);
           }

           return schedule;
       } catch (Exception e) {
           logger.error("Error calculating payment schedule: {}", e.getMessage(), e);
           throw new IllegalArgumentException("Error calculating payment schedule: " + e.getMessage(), e);
       }
    }

    private BigDecimal calculatePSK(List<PaymentScheduleElement> paymentSchedule) {
        try {
            return paymentSchedule.stream()
                    .map(PaymentScheduleElement::getTotalPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            logger.error("Error calculating PSK: {}", e.getMessage(), e);
            throw new IllegalArgumentException("Error calculating PSK: " + e.getMessage(), e);
        }
    }
}

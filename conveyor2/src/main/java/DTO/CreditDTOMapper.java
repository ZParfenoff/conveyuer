package DTO;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
public class CreditDTOMapper {

    public static CreditDTO toCreditDTO(ScoringDataDTO request, BigDecimal monthlyPayment,
                                        BigDecimal rate, List<PaymentScheduleElement> paymentSchedule) {
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setAmount(request.getAmount());
        creditDTO.setTerm(request.getTerm());
        creditDTO.setMonthlyPayment(monthlyPayment);
        creditDTO.setRate(rate);
        creditDTO.setPsk(calculatePSK(paymentSchedule));
        creditDTO.setIsInsuranceEnabled(request.getIsInsuranceEnabled());
        creditDTO.setIsSalaryClient(request.getIsSalaryClient());
        creditDTO.setPaymentSchedule(paymentSchedule);
        return creditDTO;
    }

    private static BigDecimal calculatePSK(List<PaymentScheduleElement> paymentSchedule) {
        return paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
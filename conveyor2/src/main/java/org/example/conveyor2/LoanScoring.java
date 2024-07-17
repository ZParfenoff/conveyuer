package org.example.conveyor2;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import DTO.CreditDTO;
import DTO.ScoringDataDTO;
import DTO.PaymentScheduleElement;
@Service
public class LoanScoring {

    public BigDecimal baseRate;
    public BigDecimal insuranceCost;
    public BigDecimal insuranceRateDiscount;
    public BigDecimal salaryClientRateDiscount;

    public CreditDTO calculateCredit(ScoringDataDTO request) {
        BigDecimal rate = baseRate;
        BigDecimal totalAmount = request.getAmount();

        if (request.getIsInsuranceEnabled()) {
            totalAmount = totalAmount.add(insuranceCost);
            rate = rate.subtract(insuranceRateDiscount);
        }

        if (request.getIsSalaryClient()) {
            rate = rate.subtract(salaryClientRateDiscount);
        }

        BigDecimal monthlyPayment = calculateMonthlyPayment(totalAmount, rate, request.getTerm());
        List<PaymentScheduleElement> paymentSchedule = calculatePaymentSchedule(totalAmount, rate, request.getTerm());

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

    private BigDecimal calculateMonthlyPayment(BigDecimal totalAmount, BigDecimal rate, int term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(100 * 12), 10, BigDecimal.ROUND_HALF_UP);
        BigDecimal numerator = totalAmount.multiply(monthlyRate).multiply((BigDecimal.ONE.add(monthlyRate)).pow(term));
        BigDecimal denominator = (BigDecimal.ONE.add(monthlyRate)).pow(term).subtract(BigDecimal.ONE);
        return numerator.divide(denominator, 2, BigDecimal.ROUND_HALF_UP);
    }

    private List<PaymentScheduleElement> calculatePaymentSchedule(BigDecimal totalAmount, BigDecimal rate, int term) {
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
    }

    private BigDecimal calculatePSK(List<PaymentScheduleElement> paymentSchedule) {
        return paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

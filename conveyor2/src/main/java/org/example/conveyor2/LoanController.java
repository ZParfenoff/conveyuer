package org.example.conveyor2;

import DTO.LoanApplicationRequestDTO;
import DTO.LoanOfferDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conveyor")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/offers")
    public ResponseEntity<List<LoanOfferDTO>> calculateLoanOffers(
            @RequestBody LoanApplicationRequestDTO request) {
        List<LoanOfferDTO> offers = loanService.calculateLoanOffers(request);
        return ResponseEntity.ok(offers);
    }
}
package org.example.conveyor2;

import DTO.CreditDTO;
import DTO.ScoringDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scoring")
public class LoanScoringController {

    private final LoanScoring loanScoring;

    @Autowired
    public LoanScoringController(LoanScoring loanScoring) {
        this.loanScoring = loanScoring;
    }

    @PostMapping("/calculate")
    public ResponseEntity<CreditDTO> calculateCredit(@RequestBody ScoringDataDTO request) {
        CreditDTO creditDTO = loanScoring.calculateCredit(request);
        return ResponseEntity.ok(creditDTO);
    }
}
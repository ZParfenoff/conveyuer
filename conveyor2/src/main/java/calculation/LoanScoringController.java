package calculation;

import DTO.CreditDTO;
import DTO.ScoringDataDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "Loan Scoring API", description = "Operations related to loan scoring")
public class LoanScoringController {
    private static final Logger logger = LoggerFactory.getLogger(LoanScoringController.class);
    private final LoanScoring loanScoring;

    @Autowired
    public LoanScoringController(LoanScoring loanScoring) {
        this.loanScoring = loanScoring;
    }

    @PostMapping("/scoring/calculate")
    @ApiOperation(value = "Calculate credit based on scoring data", response = CreditDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated credit"),
            @ApiResponse(code = 400, message = "Validation error in input data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<CreditDTO> calculateCredit(@RequestBody ScoringDataDTO request) {
        try {
            CreditDTO creditDTO = loanScoring.calculateCredit(request);
            return ResponseEntity.ok(creditDTO);
        } catch (IllegalArgumentException e) {
            logger.error("Validation error in calculateCredit: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Unexpected error in calculateCredit: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Handling IllegalArgumentException: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Handling unexpected exception: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
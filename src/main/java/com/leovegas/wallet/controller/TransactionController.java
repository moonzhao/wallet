package com.leovegas.wallet.controller;

import com.leovegas.wallet.model.dto.HistorySearchDto;
import com.leovegas.wallet.model.dto.ProblemDetailsDto;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.model.vo.Transaction;
import com.leovegas.wallet.service.TransactionService;
import com.leovegas.wallet.validation.ControllerValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "make a transaction on a Account")
@RestController
@RequestMapping(value = "/wallet/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully make a debit operation"),
            @ApiResponse(code = 400, message = "Incorrect value is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 404, message = "Account is not exist by provided accountId or duplicated transaction id is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 500, message = "Unexpected exception happened.", response = ProblemDetailsDto.class),
    })
    @ApiOperation( value = "Do a debit operation")
    @PostMapping(value = "/debit", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> debit(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.debit(transactionDto));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully make a debit operation"),
            @ApiResponse(code = 400, message = "Incorrect value is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 404, message = "Account is not exist by provided accountId or duplicated transaction id is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 500, message = "Unexpected exception happened.", response = ProblemDetailsDto.class),
    })
    @ApiOperation( value = "Do a credit operation")
    @PostMapping(value = "/credit", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<String> credit(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.credit(transactionDto));
    }

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get transaction history"),
            @ApiResponse(code = 400, message = "Incorrect value is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 404, message = "Account is not exist by provided accountId", response = ProblemDetailsDto.class),
            @ApiResponse(code = 500, message = "Unexpected exception happened.", response = ProblemDetailsDto.class),
    })
    @ApiOperation( value = "Get transaction history of a Account")
    @PostMapping(value = "/history", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<Transaction>> history(@Valid @RequestBody HistorySearchDto historySearchDto) {
        ControllerValidator.validateHistorySearchDto(historySearchDto);
        return ResponseEntity.ok(transactionService.getTransactionHistory(historySearchDto));
    }

}

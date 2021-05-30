package com.leovegas.wallet.controller;

import com.leovegas.wallet.model.dto.ProblemDetailsDto;
import com.leovegas.wallet.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Get Account information")
@RestController
@RequestMapping(value = "/wallet/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully get Account balance"),
            @ApiResponse(code = 400, message = "Incorrect value is provided", response = ProblemDetailsDto.class),
            @ApiResponse(code = 404, message = "Account is not exist by provided accountId", response = ProblemDetailsDto.class),
            @ApiResponse(code = 500, message = "Unexpected exception happened.", response = ProblemDetailsDto.class),
    })
    @ApiOperation( value = "Get Account current balance by accountId")
    @GetMapping(value = "/balance/{accountId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Double> getCurrentBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(accountService.getAccountBalance(accountId));
    }
}

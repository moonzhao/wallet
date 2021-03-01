package com.leovegas.wallet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leovegas.wallet.DataFactory;
import com.leovegas.wallet.model.dto.TransactionDto;
import com.leovegas.wallet.service.AccountService;
import com.leovegas.wallet.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.stream.Stream;

@WebMvcTest
class TransactionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountService accountService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void debit_Ok() throws Exception {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        when(transactionService.debit(transactionDto)).thenReturn(transactionDto.getTransactionId());

        // when
        mockMvc.perform(post("/wallet/transaction/debit")
                .content(mapper.writeValueAsString(transactionDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(transactionDto.getTransactionId()))
                .andExpect(status().isOk());

        // then
        verify(transactionService).debit(transactionDto);
    }

    @ParameterizedTest
    @MethodSource("invalidTransactionDto")
    void debit_whenInvalidInput_thenBadRequest(TransactionDto transactionDto) throws Exception {
        // when
        mockMvc.perform(post("/wallet/transaction/debit")
                .content(mapper.writeValueAsString(transactionDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // then
        verify(transactionService, never()).debit(any());
    }

    @Test
    void credit_Ok() throws Exception {
        // given
        var transactionDto = DataFactory.getDummyTransactionDto(DataFactory.LOW_AMOUNT);
        when(transactionService.credit(transactionDto)).thenReturn(transactionDto.getTransactionId());

        // when
        mockMvc.perform(post("/wallet/transaction/credit")
                .content(mapper.writeValueAsString(transactionDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(transactionDto.getTransactionId()))
                .andExpect(status().isOk());

        // then
        verify(transactionService).credit(transactionDto);
    }

    @ParameterizedTest
    @MethodSource("invalidTransactionDto")
    void credit_whenInvalidInput_thenBadRequest(TransactionDto transactionDto) throws Exception {
        // when
        mockMvc.perform(post("/wallet/transaction/credit")
                .content(mapper.writeValueAsString(transactionDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // then
        verify(transactionService, never()).credit(any());
    }

    private static Stream<Arguments> invalidTransactionDto() {
        return Stream.of(
                Arguments.of(TransactionDto.builder()
                        .accountId("4")
                        .amount(10)
                        .build()),
                Arguments.of(TransactionDto.builder()
                        .transactionId("1")
                        .amount(10)
                        .build()),
                Arguments.of(TransactionDto.builder()
                        .transactionId("1")
                        .accountId("4")
                        .amount(-10)
                        .build())
        );
    }
}

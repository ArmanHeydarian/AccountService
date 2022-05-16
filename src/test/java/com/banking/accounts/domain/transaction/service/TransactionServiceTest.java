package com.banking.accounts.domain.transaction.service;

import com.banking.accounts.domain.transaction.dto.TransactionDto;
import com.banking.accounts.domain.transaction.entity.EnumTransactionStatus;
import com.banking.accounts.domain.transaction.entity.EnumTransactionType;
import com.banking.accounts.domain.transaction.entity.Transaction;
import com.banking.accounts.domain.transaction.entity.WithdrawTransaction;
import com.banking.accounts.domain.transaction.repository.ITransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
class TransactionServiceTest {

    @MockBean
    ITransactionFactory mockTransactionFactory;
    @MockBean
    ITransactionRepository mockTransactionRepository;

    @Autowired
    ITransactionService transactionService;

    TransactionDto depositTransactionDto;
    TransactionDto withDrawTransactionDto;

    Transaction newDepositTransaction;
    Transaction newWithdarwTransaction;
    Transaction persistedTransaction ;
    @BeforeEach
    void setUp() {
        depositTransactionDto = new TransactionDto(EnumTransactionStatus.Posted,"Deposit to Account", EnumTransactionType.Deposit,1, BigDecimal.valueOf(20));
        withDrawTransactionDto = new TransactionDto(EnumTransactionStatus.Posted,"Deposit to Account", EnumTransactionType.WithDraw,1, BigDecimal.valueOf(20));

        TransactionFactory transactionFactory = new TransactionFactory();
        newDepositTransaction = transactionFactory.createTransaction(depositTransactionDto);
        newDepositTransaction.setId(1);
        newWithdarwTransaction = transactionFactory.createTransaction(withDrawTransactionDto);
        newWithdarwTransaction.setId(1);

    }

    @Test
    void createDepositTransaction() {
        // Arrange
        Mockito.when(mockTransactionFactory.createTransaction(Mockito.any(TransactionDto.class))).thenReturn(newDepositTransaction);
        Mockito.when(mockTransactionRepository.save(Mockito.any(Transaction.class))).thenReturn(newDepositTransaction);
        //Action
        persistedTransaction = transactionService.createTransaction(depositTransactionDto);
        //Assert
        assertEquals(EnumTransactionStatus.Posted,persistedTransaction.getStatus());
        assertEquals( depositTransactionDto.getAmount() ,persistedTransaction.getFinalAmount());
        assertEquals(1,persistedTransaction.getId() );
    }

    @Test
    void createWithDrawTransaction() {
        // Arrange
        Mockito.when(mockTransactionFactory.createTransaction(Mockito.any(TransactionDto.class))).thenReturn(newWithdarwTransaction);
        Mockito.when(mockTransactionRepository.save(Mockito.any(Transaction.class))).thenReturn(newWithdarwTransaction);
        //Action
        persistedTransaction = transactionService.createTransaction(withDrawTransactionDto);
        //Assert
        assertEquals(EnumTransactionStatus.Posted,persistedTransaction.getStatus());
        assertEquals( withDrawTransactionDto.getAmount().multiply( BigDecimal.valueOf(0.02)).negate() ,persistedTransaction.getFinalAmount());
        assertEquals(1,persistedTransaction.getId() );
    }


}
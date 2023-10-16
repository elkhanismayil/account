package com.folksdev.account.service;

import com.folksdev.account.dto.AccountDto;
import com.folksdev.account.dto.AccountDtoConverter;
import com.folksdev.account.dto.CreateAccountRequest;
import com.folksdev.account.model.Account;
import com.folksdev.account.model.Customer;
import com.folksdev.account.model.Transaction;
import com.folksdev.account.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final AccountDtoConverter converter;
    private final TransactionService transactionService;

    public AccountService(AccountRepository accountRepository,
                          CustomerService customerService,
                          AccountDtoConverter converter,
                          TransactionService transactionService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.converter = converter;
        this.transactionService = transactionService;
    }

    public AccountDto createAccount(CreateAccountRequest createAccountRequest) {
        Customer customer = customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account(customer, LocalDateTime.now(), createAccountRequest.getInitialCredit());
        if (createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = transactionService.createTransaction(account,
                    createAccountRequest.getInitialCredit());
//            Transaction transaction = new Transaction(createAccountRequest.getInitialCredit(), account);

            account.getTransactions().add(transaction);
        }
        return converter.convert(accountRepository.save(account));
    }
}

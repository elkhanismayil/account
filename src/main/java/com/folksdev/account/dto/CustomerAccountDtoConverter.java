package com.folksdev.account.dto;

import com.folksdev.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {
    private final TransactionDtoConverter transactionDtoConverter;

    public CustomerAccountDtoConverter(TransactionDtoConverter transactionDtoConverter) {
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public CustomerAccountDto converter(Account from){
        return new CustomerAccountDto(Objects.requireNonNull(from.getId()),
                from.getBalance(),
                from.getTransactions().stream().map(transactionDtoConverter::convertToTransaction).collect(Collectors.toSet()),
                Objects.requireNonNull(from.getCreationDate())
        );
    }
}

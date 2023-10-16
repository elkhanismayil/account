package com.folksdev.account.dto;

import com.folksdev.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {
    private final CustomerAccountDtoConverter converter;

    public CustomerDtoConverter(CustomerAccountDtoConverter converter) {
        this.converter = converter;
    }

    public AccountCustomerDto convertToAccountCustomer(Customer from) {
        if (from == null) {
            return new AccountCustomerDto("", "", "");
        }
        return new AccountCustomerDto(Objects.requireNonNull(from.getId()),
                Objects.requireNonNull(from.getName()),
                Objects.requireNonNull(from.getSurname()));
    }

    public CustomerDto convertToCustomerDto(Customer customerById) {
        return new CustomerDto(Objects.requireNonNull(customerById.getId()),
                Objects.requireNonNull(customerById.getName()),
                Objects.requireNonNull(customerById.getSurname()),
                Objects.requireNonNull(customerById.getAccounts()).stream()
                        .map(converter::converter).collect(Collectors.toSet())
        );
    }
}

package com.backend.blaybus.core.account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,String> {
    Optional<Account> findAccountByEmployeeId(String employeeId);
}

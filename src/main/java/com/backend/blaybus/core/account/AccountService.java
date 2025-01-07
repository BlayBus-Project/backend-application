package com.backend.blaybus.core.account;

import com.backend.blaybus.core.account.dto.AccountRequestDto;
import com.backend.blaybus.core.account.dto.TokenResponseDto;
import com.backend.blaybus.global.auth.SecurityUtil;
import com.backend.blaybus.global.auth.jwt.JwtTokenProvider;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @NotNull
    public Account createAccount(final AccountRequestDto accountRequestDto){
        Account account = Account.builder()
                .employeeId(accountRequestDto.getEmployeeId())
                .changedPassword(accountRequestDto.getPassword())
                .affiliation(Affiliation.P1)
                .build();
        accountRepository.save(account);
        return account;
    }
    @NotNull
    public void getAccount(){
        System.out.println(SecurityUtil.getCurrentUsername());

    }
    @NotNull
    public TokenResponseDto loginAccount(final AccountRequestDto accountRequestDto){
        Account account = accountRepository.findAccountByEmployeeId(accountRequestDto.getEmployeeId()).orElseThrow(RuntimeException::new);
        if(!(account.getChangedPassword().equals(accountRequestDto.getPassword()))){
            throw new RuntimeException();
        }
        System.out.printf("account.getEmployeeId() : " + account.getEmployeeId());
        return TokenResponseDto.builder()
                .accessToken(jwtTokenProvider.createToken(account.getEmployeeId(),account.getChangedPassword(), Collections.singletonList(account.getAffiliation().toString())))
                .build();
    }
}

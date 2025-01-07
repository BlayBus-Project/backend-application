package com.backend.blaybus.core.account;

import com.backend.blaybus.core.account.dto.AccountRequestDto;
import com.backend.blaybus.core.account.dto.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody final AccountRequestDto accountRequestDto){
        return ResponseEntity.ok(accountService.createAccount(accountRequestDto));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> loginAccount(@RequestBody final AccountRequestDto accountRequestDto){
        return ResponseEntity.ok(accountService.loginAccount(accountRequestDto));
    }
    @GetMapping
    public ResponseEntity<?> getAccount(){
        accountService.getAccount();
        return ResponseEntity.ok().build();
    }
}

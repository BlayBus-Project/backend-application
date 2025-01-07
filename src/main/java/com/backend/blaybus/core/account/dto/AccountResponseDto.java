package com.backend.blaybus.core.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountResponseDto {
    private String employeeId;
    private String affiliation;
    private String name;
    private String profileKey;
    private LocalDate joinedDate;
    private Long level;
}

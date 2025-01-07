package com.backend.blaybus.core.account;

import com.backend.blaybus.global.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "account")
public class Account extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String employeeId;
    private String name;
    private LocalDate joinedDate;
    private Long gid;
    private String level;
    private String accountId;
    private String oldPassword;
    private String changedPassword;

    @Enumerated(EnumType.STRING)
    private Affiliation affiliation;
    //private Image imageUrl // 추후 캐릭터 이미지 URL
}

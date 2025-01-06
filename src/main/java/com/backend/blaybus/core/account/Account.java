package com.backend.blaybus.core.account;

import com.backend.blaybus.global.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String employeeId;
    private String name;
    private LocalDate joinedDate;
    private Long gid;
    private String level;
    private String accountId;
    private String oldPassword;
    private String changedPassword;
    //private Image imageUrl // 추후 캐릭터 이미지 URL
}

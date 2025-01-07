package com.backend.blaybus.core.Quest;

import com.backend.blaybus.core.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quest")
public class Quest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questId;
    @ManyToOne  //사번	대상자
    private Account account;
    private String description;
    //사번	대상자	리더 부여 퀘스트명	달성내용	부여 경험치	비고


}

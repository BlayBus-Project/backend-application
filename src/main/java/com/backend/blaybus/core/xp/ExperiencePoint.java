package com.backend.blaybus.core.xp;

import com.backend.blaybus.core.Quest.QuestType;
import com.backend.blaybus.core.account.Account;
import com.backend.blaybus.core.xp.entity.ExperienceCategory;
import com.backend.blaybus.core.xp.entity.ExperienceGrade;
import com.backend.blaybus.global.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "experience_point")
public class ExperiencePoint extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Account account;
    private Long pointAmount;
    private String year;
    //@Enumerated(EnumType.STRING)
    //private ExperienceGrade grade; //경험치 등급


    public ExperiencePoint(Account account, Long pointAmount, String year) {
        this.account = account;
        this.pointAmount = pointAmount;
        this.year = year;
    }
}

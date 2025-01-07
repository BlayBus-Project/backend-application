package com.backend.blaybus.core.Quest;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CompanyQuest extends Quest {
    private String questName;
    private Long experience; //부여 경험치
    private LocalDate dueDate; // 마감일

    // Getter for month and day if necessary
    public int getMonth() {
        return dueDate.getMonthValue();
    }

    public int getDay() {
        return dueDate.getDayOfMonth();
    }
}

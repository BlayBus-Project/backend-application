package com.backend.blaybus.core.Quest;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leader_quest")
public class LeaderQuest extends Quest{
    private Long maxExperience;
    private Long medianExperience;
    private String questName;
    public Long getAchievementPoints(String experience) {
        if (experience.equals("Max")) {
            return maxExperience;
        } else if (experience.equals("Median")) {
            return medianExperience;
        }
        return 0L;
    }
}

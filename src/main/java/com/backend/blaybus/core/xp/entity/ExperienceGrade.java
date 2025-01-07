package com.backend.blaybus.core.xp.entity;

import lombok.Getter;

@Getter
public enum ExperienceGrade {
    S(6500), A(4500), B(3000), C(1500), D(0), // PERSONAL_REVIEW
    MAX(0), MEDIAN(0), MIN(0); // TASK_QUEST, LEADERSHIP_QUEST에서 사용

    private final int points;

    ExperienceGrade(int points) {
        this.points = points;
    }

}
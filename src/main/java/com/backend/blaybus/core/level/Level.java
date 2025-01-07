package com.backend.blaybus.core.level;

import lombok.Getter;

@Getter
public enum Level {
    F1_1(0),
    F1_2(13500),
    F2_1(27000),
    F2_2(39000),
    F3_1(51000),
    F3_2(63000),
    F4_1(78000),
    F4_2(93000),
    F5(108000);

    private final int requiredExperience;

    Level(int requiredExperience) {
        this.requiredExperience = requiredExperience;
    }

    // 현재 경험치에 따라 레벨 계산
    public static Level getLevelByExperience(int experience) {
        Level currentLevel = F1_1; // 기본 레벨 설정

        for (Level level : Level.values()) {
            if (experience >= level.getRequiredExperience()) {
                currentLevel = level;
            } else {
                break; // 경험치가 부족하면 루프 종료
            }
        }

        return currentLevel;
    }
}

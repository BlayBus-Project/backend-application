package com.backend.blaybus.core.xp.entity;

import lombok.Getter;

@Getter
public enum ExperienceCategory {
    PERSONAL_REVIEW {
        @Override
        public Long calculatePoints(ExperienceGrade grade) {
            return (long) grade.getPoints(); // S, A, B, C, D 등급에 따라 포인트 반환
        }
    },
    TASK_QUEST {
        @Override
        public Long calculatePoints(ExperienceGrade grade) {
            return switch (grade) {
                case MAX -> 4000L;
                case MEDIAN -> 2000L;
                default -> 0L;
            };
        }
    },
    LEADERSHIP_QUEST {
        @Override
        public Long calculatePoints(ExperienceGrade grade) {
            return switch (grade) {
                case MAX -> 2000L;
                case MEDIAN -> 1000L;
                default -> 0L;
            };
        }
    };

    public abstract Long calculatePoints(ExperienceGrade grade);
}

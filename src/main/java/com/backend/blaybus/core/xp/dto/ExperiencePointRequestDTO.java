package com.backend.blaybus.core.xp.dto;

import com.backend.blaybus.core.Quest.Quest;
import com.backend.blaybus.core.xp.entity.ExperienceCategory;
import com.backend.blaybus.core.xp.entity.ExperienceGrade;

public record ExperiencePointRequestDTO(
        Long QuestId,
        String employeeId,
        String experience //사용자 Max? Median? 등급?
) {
}

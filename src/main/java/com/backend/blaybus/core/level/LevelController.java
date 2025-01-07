package com.backend.blaybus.core.level;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LevelController {

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    // 현재 레벨 조회
    @GetMapping("/{employeeId}/level")
    public ResponseEntity<?> getCurrentLevel(@PathVariable String employeeId) {
        Level currentLevel = levelService.getCurrentLevel(employeeId);
        int experienceToNextLevel = levelService.getExperienceToNextLevel(employeeId);

        Map<String, Object> response = new HashMap<>();
        response.put("currentLevel", currentLevel.name());
        response.put("experienceToNextLevel", experienceToNextLevel);

        return ResponseEntity.ok(response);
    }

}

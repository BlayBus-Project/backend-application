package com.backend.blaybus.core.xp.api;

import com.backend.blaybus.core.xp.dto.ExperiencePointRequestDTO;
import com.backend.blaybus.core.xp.service.ExperiencePointService;
import com.backend.blaybus.global.dto.response.ResultResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/experience-points")
public class ExperiencePointController {

    private final ExperiencePointService experiencePointService;

    public ExperiencePointController(ExperiencePointService experiencePointService) {
        this.experiencePointService = experiencePointService;
    }

    //해당 직원 경험치에 평가에 맞는 경험치를 추가함.
    @PostMapping()
    public ResponseEntity<?> addPoint(@RequestBody ExperiencePointRequestDTO requestDTO) {
        ResultResponseDto<?> resultResponseDto = experiencePointService.calcQuestPoint(requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resultResponseDto);
    }
}

package com.backend.blaybus.core.xp.service;


import com.backend.blaybus.core.Quest.*;
import com.backend.blaybus.core.account.Account;
import com.backend.blaybus.core.account.AccountRepository;
import com.backend.blaybus.core.xp.ExperiencePoint;
import com.backend.blaybus.core.xp.dto.ExperiencePointRequestDTO;
import com.backend.blaybus.global.dto.response.ResultResponseDto;
import com.backend.blaybus.global.error.CustomException;
import com.backend.blaybus.global.error.Error;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.backend.blaybus.core.xp.repository.*;

import java.time.LocalDate;


@Service
@Transactional
public class ExperiencePointService {

    private final AccountRepository accountRepository;
    private final QuestRepository questRepository;

    public ExperiencePointService(ExperiencePointRepository experiencePointRepository, AccountRepository accountRepository, QuestRepository questRepository) {
        this.accountRepository = accountRepository;
        this.questRepository = questRepository;
    }

    String year = String.valueOf(LocalDate.now().getYear());

    public ResultResponseDto<?> calcQuestPoint(ExperiencePointRequestDTO requestDTO) {
        Quest quest = questRepository.findById(requestDTO.QuestId()).orElseThrow(() -> new CustomException(Error.NO_SUCH_ELEMENT));
        Account account = accountRepository.findByEmployeeId(requestDTO.employeeId()).orElseThrow(() -> new CustomException(Error.NO_SUCH_ELEMENT));

        // 타입에 따라 다른 로직 실행
        return switch (quest) {
            case LeaderQuest leaderQuest -> calcLeaderQuestPoint(leaderQuest, account, requestDTO.experience());
            case PerformanceReview performanceReview -> calcPerformanceReviewQuestPoint(performanceReview, account, requestDTO.experience());
            case CompanyQuest companyQuest -> calcCompanyQuestPoint(companyQuest, account, requestDTO.experience());
            case null, default ->
                    throw new CustomException(Error.NO_SUCH_ELEMENT);
        };
    }

    /* 1. 리더 포인트 지급 */
    public ResultResponseDto<?> calcLeaderQuestPoint(LeaderQuest quest, Account account, String experience) {
        Long achievementPoints = quest.getAchievementPoints(experience); //사용자 달성내용에 따른 포인트
        account.addExperiencePoint(new ExperiencePoint(account, achievementPoints, year));
        return ResultResponseDto.toResultResponseDto(200, "경험치를 성공적으로 지급하였습니다.");
    }

    /* 2. 인사평가 포인트 지급 -> 년도 저장 방식 바꿔야 함*/
    public ResultResponseDto<?> calcPerformanceReviewQuestPoint(PerformanceReview review, Account account, String experience) {
        switch (experience) {
            case "S" -> account.addExperiencePoint(new ExperiencePoint(account, 6500L, year));
            case "A" -> account.addExperiencePoint(new ExperiencePoint(account, 4500L, year));
            case "B" -> account.addExperiencePoint(new ExperiencePoint(account, 3000L, year));
            case "C" -> account.addExperiencePoint(new ExperiencePoint(account, 1500L, year));
            case "D" -> account.addExperiencePoint(new ExperiencePoint(account, 0L, year));
        }
        return ResultResponseDto.toResultResponseDto(200, "경험치를 성공적으로 지급하였습니다.");
    }

    /* 3. 전사 프로젝트 포인트 지급 */
    public ResultResponseDto<?> calcCompanyQuestPoint(CompanyQuest quest, Account account, String experience) {
        long ex = Long.parseLong(experience);
        account.addExperiencePoint(new ExperiencePoint(account, ex, year));
        return ResultResponseDto.toResultResponseDto(200, "경험치를 성공적으로 지급하였습니다.");
    }
}
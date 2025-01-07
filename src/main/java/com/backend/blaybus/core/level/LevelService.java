package com.backend.blaybus.core.level;

import com.backend.blaybus.core.account.Account;
import com.backend.blaybus.core.account.AccountRepository;
import com.backend.blaybus.core.xp.repository.ExperiencePointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LevelService {

    private final AccountRepository accountRepository;
    private final ExperiencePointRepository experiencePointRepository;

    public LevelService(AccountRepository accountRepository, ExperiencePointRepository experiencePointRepository) {
        this.accountRepository = accountRepository;
        this.experiencePointRepository = experiencePointRepository;
    }

    // 현재 레벨 계산
    public Level getCurrentLevel(Long employeeId) {
        // 사용자 총 경험치 계산
        Account account = accountRepository.findById(String.valueOf(employeeId))
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + employeeId));

        return account.getLevel(); // 현재 저장된 레벨 반환
    }

    // 2. 다음 레벨까지 필요한 경험치 계산
    public int getExperienceToNextLevel(String employeeId) {
        // 사용자 조회
        Account account = accountRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + employeeId));

        // 총 경험치 계산
        int totalExperience = experiencePointRepository.findTotalPointsByAccount(employeeId).orElse(0);

        // 현재 레벨
        Level currentLevel = account.getLevel();

        // 다음 레벨 필요 경험치
        if (currentLevel.ordinal() + 1 < Level.values().length) {
            Level nextLevel = Level.values()[currentLevel.ordinal() + 1];
            return Math.max(0, nextLevel.getRequiredExperience() - totalExperience);
        }
        // 최상위 레벨(F5)일 경우 0 반환
        return 0;
    }

    // 3. 경험치 기반으로 레벨 업데이트
    @Transactional
    public void updateLevel(String employeeId) {
        Account account = accountRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with ID: " + employeeId));

        // 총 경험치 계산
        int totalExperience = experiencePointRepository.findTotalPointsByAccount(employeeId)
                .orElse(0);

        // 새로운 레벨 계산
        Level newLevel = Level.getLevelByExperience(totalExperience);

        // 레벨 변경
        if (!newLevel.equals(account.getLevel())) {
            account.updateLevel(newLevel); // Account 엔티티에 updateLevel 메서드 추가
        }
    }

}

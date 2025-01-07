package com.backend.blaybus.core.xp.repository;

import com.backend.blaybus.core.account.Account;
import com.backend.blaybus.core.xp.ExperiencePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExperiencePointRepository extends JpaRepository<ExperiencePoint, Long> {
    List<ExperiencePoint> findByAccount(Account account);
    List<ExperiencePoint> findByYear(String year);

    @Query("SELECT SUM(e.pointAmount) FROM ExperiencePoint e WHERE e.account.employeeId = :employeeId")
    Optional<Integer> findTotalPointsByAccount(@Param("employeeId") String employeeId);

}

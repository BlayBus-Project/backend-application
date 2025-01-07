package com.backend.blaybus.core.account;

import com.backend.blaybus.core.level.Level;
import com.backend.blaybus.core.xp.ExperiencePoint;
import com.backend.blaybus.global.base.BaseTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "account")
public class Account extends BaseTime {
    @Id
    private String employeeId;
    private String name;
    private LocalDate joinedDate;
    private Long gid;
    private Level level;
    private String accountId;
    private String oldPassword;
    private String changedPassword;

    //경험치 목록
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExperiencePoint> experiencePoints = new ArrayList<>();

    public void addExperiencePoint(ExperiencePoint experiencePoint) {
        this.experiencePoints.add(experiencePoint);
    }

    public void updateLevel(Level newLevel) {
        this.level = newLevel;
    }
    //private Image imageUrl // 추후 캐릭터 이미지 URL
}

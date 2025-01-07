package com.backend.blaybus.core.Quest;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceReview extends Quest{
    private String rating;
    public void addRating(String rating) {
        this.rating = rating;
    }
}

package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResult {

    private UUID id;
    private UUID testId;
    private int minScore;
    private int maxScore;
    private String description;

}

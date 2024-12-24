package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDTO {
    private String title;
    private String description;
    private String category;
    private int maxBall;
    private List<QuestionDTO> questions;
    private List<TestResultDTO> testResults;
}


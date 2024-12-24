package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Test {

    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private String category;
    private LocalDateTime createdAt;
    private int maxBall;

}

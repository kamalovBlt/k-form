package ru.itis.model.stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.model.User;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserResultCompletedAt {
    private User user;
    private int score;
    private LocalDateTime completedAt;
}

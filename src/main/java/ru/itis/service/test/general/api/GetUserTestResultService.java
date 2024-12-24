package ru.itis.service.test.general.api;

import ru.itis.model.Result;

import java.util.UUID;

public interface GetUserTestResultService {

    Result getUserTestResult(UUID userId, UUID testId);

}

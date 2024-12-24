package ru.itis.service.test.general.api;

import ru.itis.dto.TestDTO;

import java.util.UUID;

public interface GetTestService {

    TestDTO getTestById(UUID testId);

}

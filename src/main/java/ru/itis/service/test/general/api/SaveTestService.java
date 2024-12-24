package ru.itis.service.test.general.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public interface SaveTestService {

    void save(InputStream jsonObjects, UUID userId) throws IOException;

}

package com.ds.project.apothecary;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * The type General test.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class GeneralTest {

    /**
     * Should return default message.
     */
    @Test
    public void shouldReturnDefaultMessage() {
        assert (true);
    }
}

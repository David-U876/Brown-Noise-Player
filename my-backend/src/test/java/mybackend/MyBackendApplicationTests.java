package org.example.mybackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyBackendApplicationTests {

    @Test
    void firstTest() {
        int result = 2 + 3;
        assertEquals(5, result, "2 + 3 should equal 5");
    }
}

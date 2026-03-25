package SS4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BT3 {
    static class UserProcessor {
        public String processEmail(String email) {
            if (email == null || !email.contains("@")) {
                throw new IllegalArgumentException();
            }
            String[] parts = email.split("@", -1);
            if (parts.length != 2 || parts[1].isEmpty()) {
                throw new IllegalArgumentException();
            }
            return email.toLowerCase();
        }
    }

    private UserProcessor userProcessor;

    @BeforeEach
    void setUp() {
        userProcessor = new UserProcessor();
    }

    @Test
    void testValidEmail() {
        assertEquals("user@gmail.com", userProcessor.processEmail("user@gmail.com"));
    }
    @Test
    void testMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> userProcessor.processEmail("usergmail.com"));
    }

    @Test
    void testMissingDomain() {
        assertThrows(IllegalArgumentException.class, () -> userProcessor.processEmail("user@"));
    }

    @Test
    void testEmailNormalization() {
        assertEquals("example@gmail.com", userProcessor.processEmail("Example@Gmail.com"));
    }
}
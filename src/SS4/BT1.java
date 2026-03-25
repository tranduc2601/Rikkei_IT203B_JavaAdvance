package SS4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BT1 {
    static class UserValidator {
        public boolean isValidUsername(String username) {
            if (username == null) return false;
            return username.length() >= 6 && username.length() <= 20 && !username.contains(" ");
        }
    }

    @Test
    void testValidUsername() {
        UserValidator validator = new UserValidator();
        assertTrue(validator.isValidUsername("user123"));
    }

    @Test
    void testUsernameTooShort() {
        UserValidator validator = new UserValidator();
        assertFalse(validator.isValidUsername("abc"));
    }

    @Test
    void testUsernameWithSpace() {
        UserValidator validator = new UserValidator();
        assertFalse(validator.isValidUsername("user name"));
    }
}
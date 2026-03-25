package SS4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BT4 {
    static class PasswordValidator {
        public String evaluatePasswordStrength(String password) {
            if (password == null || password.length() < 8) {
                return "Yếu";
            }
            int criteriaCount = 0;
            if (password.matches(".*[A-Z].*")) criteriaCount++;
            if (password.matches(".*[a-z].*")) criteriaCount++;
            if (password.matches(".*\\d.*")) criteriaCount++;
            if (password.matches(".*[^A-Za-z0-9].*")) criteriaCount++;

            if (criteriaCount == 4) return "Mạnh";
            if (criteriaCount == 3) return "Trung bình";
            return "Yếu";
        }
    }

    @Test
    void testPasswordStrength() {
        PasswordValidator validator = new PasswordValidator();
        assertAll(
                () -> assertEquals("Mạnh", validator.evaluatePasswordStrength("Abc123!@")),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("abc123!@")),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("ABC123!@")),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abcdef!@")),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abc12345")),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("Ab1!")),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("password")),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("ABC12345"))
        );
    }
}
package SS4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BT2 {
    static class UserService {
        public boolean checkRegistrationAge(int age) {
            if (age < 0) {
                throw new IllegalArgumentException();
            }
            return age >= 18;
        }
    }

    @Test
    void testValidAge() {
        UserService service = new UserService();
        assertEquals(true, service.checkRegistrationAge(18));
    }

    @Test
    void testInvalidAge() {
        UserService service = new UserService();
        assertEquals(false, service.checkRegistrationAge(17));
    }

    @Test
    void testNegativeAge() {
        UserService service = new UserService();
        assertThrows(IllegalArgumentException.class, () -> service.checkRegistrationAge(-1));
    }
}
package SS3;

import java.util.List;
import java.util.Optional;

public class BT3 {
    record User(String username) {}

    static class UserRepository {
        private final List<User> users = List.of(
                new User("alice"),
                new User("bob"),
                new User("charlie")
        );

        public Optional<User> findUserByUsername(String username) {
            return users.stream()
                    .filter(u -> u.username().equals(username))
                    .findFirst();
        }
    }

    public static void main(String[] args) {
        UserRepository repository = new UserRepository();

        repository.findUserByUsername("alice").ifPresentOrElse(
                user -> System.out.println("Welcome " + user.username()),
                () -> System.out.println("Guest login")
        );
    }
}
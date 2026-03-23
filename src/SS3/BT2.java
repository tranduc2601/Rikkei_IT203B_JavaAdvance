package SS3;

import java.util.List;

public class BT2 {
    record User(String username, String email) {}

    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com"),
                new User("bob", "bob@yahoo.com"),
                new User("charlie", "charlie@gmail.com")
        );

        users.stream()
                .filter(u -> u.email().endsWith("@gmail.com"))
                .map(User::username)
                .forEach(System.out::println);
    }
}
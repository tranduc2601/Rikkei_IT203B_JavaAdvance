package SS3;

import java.util.List;
import java.util.stream.Collectors;

public class BT4 {
    record User(String username, String email) {}

    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice1@gmail.com"),
                new User("bob", "bob@yahoo.com"),
                new User("alice", "alice2@gmail.com"),
                new User("charlie", "charlie@gmail.com")
        );

        List<User> uniqueUsers = users.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(User::username, u -> u, (existing, replacement) -> existing),
                        map -> List.copyOf(map.values())
                ));

        uniqueUsers.forEach(System.out::println);
    }
}
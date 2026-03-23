package SS3;

import java.util.List;

public class BT1 {
    enum Status {
        ACTIVE, INACTIVE
    }

    record User(String username, String email, Status status) {}

    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@yahoo.com", Status.INACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE)
        );

        users.forEach(System.out::println);
    }
}
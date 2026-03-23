import java.time.LocalDate;

// Phần 1: Class User gốc (Chứa dữ liệu nhạy cảm)
class User {
    private String id;
    private String email;
    private String password;
    private boolean verified;
    private LocalDate createdAt;

    public User(String id, String email, String password, boolean verified, LocalDate createdAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.createdAt = createdAt;
    }

    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public boolean isVerified() { return verified; }
    public LocalDate getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "User{id='" + id + "', email='" + email + "'}";
    }
}           

// Phần 3: Hệ thống phân hạng (Tier)
class Tier {
    protected String name;
    public Tier(String name) { this.name = name; }

    @Override
    public String toString() { return this.name; }
}

class Gold extends Tier { public Gold() { super("Gold"); } }
class Silver extends Tier { public Silver() { super("Silver"); } }
class Bronze extends Tier { public Bronze() { super("Bronze"); } }

// Phần 2: Record PublicUser (Bất biến, không chứa Password)
record PublicUser(String id, String email, Tier tier) {}
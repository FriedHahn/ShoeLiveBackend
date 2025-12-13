package htw.webtech.myapp.persistence.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class NotificationEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userEmail;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false)
    private boolean read = false;

    public NotificationEntry() {}

    public NotificationEntry(String userEmail, String message) {
        this.userEmail = userEmail;
        this.message = message;
        this.createdAt = Instant.now();
        this.read = false;
    }

    public Long getId() { return id; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }
}

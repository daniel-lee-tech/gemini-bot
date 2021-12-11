package com.geminibot.geminibot.entities.postgres;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String email;
    private String passwordDigest;

    private boolean emailVerified;

    @Column(unique = true)
    private UUID emailVerificationToken;

    public User(String email, String plainTextPassword) {
        this.email = email;
        this.setPasswordDigest(plainTextPassword);
        this.emailVerificationToken = UUID.randomUUID();
    }

    public User() {
        this.emailVerificationToken = UUID.randomUUID();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordDigest() {
        return passwordDigest;
    }

    public void setPasswordDigest(String encodedPassword) {
        this.passwordDigest = encodedPassword;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean activated) {
        this.emailVerified = activated;
    }

    public UUID getEmailVerificationToken() {
        return emailVerificationToken;
    }

    public void setEmailVerificationToken(UUID activationToken) {
        this.emailVerificationToken = activationToken;
    }

    public void redactPassword() {
        this.passwordDigest = "<REDACTED>";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}

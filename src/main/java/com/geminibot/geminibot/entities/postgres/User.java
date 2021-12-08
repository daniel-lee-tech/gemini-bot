package com.geminibot.geminibot.entities.postgres;

import com.geminibot.geminibot.datatransferobjects.RegisterDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String encodedPassword;

    private boolean activated;

    @Column(unique = true)
    private UUID activationToken;

    public User(String email, String plainTextPassword) {
        this.email = email;
        this.setEncodedPassword(plainTextPassword);
        this.activationToken = UUID.randomUUID();
    }

    public User() {
        this.activationToken = UUID.randomUUID();
    }

    public User(RegisterDTO registerDTO) throws AssertionError {
        this.email = registerDTO.getEmail();
        assert registerDTO.getPlainTextPassword().equals(registerDTO.getPlainTextPasswordConfirmation());
        this.setEncodedPassword(registerDTO.getPlainTextPassword());
        this.activationToken = UUID.randomUUID();
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

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String plainTextPassword) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.encodedPassword = encoder.encode(plainTextPassword);
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public UUID getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(UUID activationToken) {
        this.activationToken = activationToken;
    }

    public void redactPassword() {
        this.encodedPassword = "<REDACTED>";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}

package com.geminibot.geminibot.entities.postgres;

import com.geminibot.geminibot.entities.constants.ApiKeyType;

import javax.persistence.*;

@Entity
@Table(name="api_keys")
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique=true)
    private ApiKeyType type;
    private String publicKey;
    private String secretKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApiKeyType getType() {
        return type;
    }

    public void setType(ApiKeyType type) {
        this.type = type;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}

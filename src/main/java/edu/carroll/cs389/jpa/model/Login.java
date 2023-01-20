package edu.carroll.cs389.jpa.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "login")
public class Login {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String hashedPassword;

    public Login(){}

    public Login(String username, String rawPassword) {
        this.username = username;
        setRawPassword(rawPassword);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setRawPassword(String rawPassword) {
        // Mentioned elsewhere; but, this method should never be done in production software
        // (or any software for that matter)
        this.hashedPassword = Integer.toString(rawPassword.hashCode());
    }

    private static final String EOL = System.lineSeparator();
    private static final String TAB = "\t";

    @Override
    public String toString() {
        return "Login @ " + super.toString() + "[" + EOL +
                TAB + "username=" + username + EOL +
                TAB + "hashedPassword=" + "****" + EOL +
                "]" + EOL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        final Login login = (Login)o;
        return username.equals(login.username) && hashedPassword.equals(login.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, hashedPassword);
    }
}

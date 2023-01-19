package edu.carroll.cs389.jpa.repo;

import java.util.List;

import edu.carroll.cs389.jpa.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Integer> {
    // JPA errors for returning a single object that doesn't exist so return a list despite it being empty or one item
    List<Login> findByUsernameIgnoreCase(String username);
}

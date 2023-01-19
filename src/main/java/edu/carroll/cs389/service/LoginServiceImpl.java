package edu.carroll.cs389.service;

import edu.carroll.cs389.jpa.model.Login;
import edu.carroll.cs389.jpa.repo.LoginRepository;
import edu.carroll.cs389.web.form.LoginForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    private final LoginRepository loginRepository;

    public LoginServiceImpl(LoginRepository loginrepository) {
        this.loginRepository = loginrepository;
    }

    /**
     * Given a loginForm, determine if the information provided is valid, and the user exists in the system.
     *
     * @param loginForm - Data containing user login information, such as username and password.
     * @return true if data exists and matches what's on record, false otherwise
     */
    @Override
    public boolean validateUser(LoginForm loginForm) {
        // case-insensitive lookup
        List<Login> users = loginRepository.findByUsernameIgnoreCase(loginForm.getUsername());

        // if we get zero or more than 1 user it is an error we do not deal with
        if (users.size() != 1)
            return false;

        Login user = users.get(0);
        // There is a note here about NEVER including code that uses Java's hashcode in production and that has been noted.
        final String userProvidedHash = Integer.toString(loginForm.getPassword().hashCode());
        if (!user.getHashedPassword().equals(userProvidedHash)) {
            return false;
        }

        // user exists to login and the provided password matches the one on file
        return true;
    }
}

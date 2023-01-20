package edu.carroll.cs389.service;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.List;

import edu.carroll.cs389.jpa.model.Login;
import edu.carroll.cs389.jpa.repo.LoginRepository;
import edu.carroll.cs389.web.form.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginServiceImplTest {
    private static final String username = "testuser";
    private static final String password = "testpass";

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRepository loginRepository;

    private Login fakeUser = new Login(username, password);

    @BeforeEach
    public void beforeTest() {
        assertNotNull("loginRepository must be injected", loginRepository);
        assertNotNull("loginService must be injected", loginService);

        // dummy record in DB
        final List<Login> users = loginRepository.findByUsernameIgnoreCase(username);
        if (users.isEmpty()) {
            loginRepository.save(fakeUser);
        }
    }

    @Test
    public void validateUserSuccessTest() {
        final LoginForm form = new LoginForm(username, password);
        assertTrue("validateUserSuccessTest: should succeed using the same user/password info", loginService.validateUser(form));
    }

    @Test
    public void validateUserExistingUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(username, password + "extraChars");
        assertFalse("validateUserExistingUserInvalidPasswordTest: should fail using valid user but invalid password", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserValidPasswordTest() {
        final LoginForm form = new LoginForm(username + "joemama", password);
        assertFalse("validateUserInvalidUserValidPasswordTest: should fail using invalid user but valid password", loginService.validateUser(form));
    }

    @Test
    public void validateUserInvalidUserInvalidPasswordTest() {
        final LoginForm form = new LoginForm(username + "joemama", password + "extraChars");
        assertFalse("validateUserInvalidUserInvalidPasswordTest: should fail using invalid username and invalid password", loginService.validateUser(form));
    }
}

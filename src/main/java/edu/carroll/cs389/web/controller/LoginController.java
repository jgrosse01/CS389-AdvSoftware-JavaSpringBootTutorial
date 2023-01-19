package edu.carroll.cs389.web.controller;

import edu.carroll.cs389.web.form.LoginForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    // NOTE: in the tutorial it is specifically noted that if anything of this sort is used in an application our professor will hunt us down.
    // I wouldn't put it past him and I do not intend to ever use any method like this for authentication.
    private static final String validUser = "cs389user";
    private static final String validPass = "supersecret";

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, RedirectAttributes attributes) {
        System.out.println("User '" + loginForm.getUsername() + "' attempted login");
        if (result.hasErrors()) {
            return "login";
        }
        // Username does not have to case match, password is exact
        // NOTE: another interjection from the professor to similar affect as the last stating that password validation
        // should never be done by string equality. this is for the purpose of example
        if (!(validUser.equalsIgnoreCase(loginForm.getUsername()) &&
                validPass.equals(loginForm.getPassword()))) {
            result.addError(new ObjectError("globalError", "Username and password do not match any known users."));
            return "login";
        }
        attributes.addAttribute("username", loginForm.getUsername());
        return "redirect:/loginSuccess";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(String username, Model model) {
        model.addAttribute("username", username);
        return "loginSuccess";
    }

    @GetMapping("/loginFailure")
    public String loginFailure() {
        return "loginFailure";
    }
}

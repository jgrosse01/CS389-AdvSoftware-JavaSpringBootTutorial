package edu.carroll.cs389;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String index() {
        return "index";
    }
}

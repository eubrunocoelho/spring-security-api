package com.eubrunocoelho.spring_security_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    @GetMapping("/open")
    public String openApi() {
        return "Open";
    }

    @GetMapping("/protected")
    public String protectedApi() {
        return "Protected";
    }
}

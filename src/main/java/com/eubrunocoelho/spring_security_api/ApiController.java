package com.eubrunocoelho.spring_security_api;

import com.eubrunocoelho.spring_security_api.config.LoginUtilityService;
import com.eubrunocoelho.spring_security_api.config.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    String SCREEN_LABEL = "Security API - API Home [%s] [%s]";

    @Autowired
    LoginUtilityService loginUtilityService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/open")
    public String openApi() {
        String label = String.format(
                SCREEN_LABEL,
                "OPEN-API", ""
        );
        logger.info("Request - " + label);

        return label;
    }

    @GetMapping("/protected")
    public String protectedApi() {
        Users user = loginUtilityService.getLoggedInUser();
        String label = String.format(
                SCREEN_LABEL,
                "PROTECTED-API",
                user.getUserName() + " / " + user.getRole()
        );

        logger.info("Request - " + label);

        return label;
    }
}

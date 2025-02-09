package com.mosa.hogwartsartifactsonline.security;

import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class AuthController {

    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);


    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication) {

        LOGGER.debug("Authenticated user: '{}'", authentication.getName());

        return new Result(true, StatusCode.SUCCESS, "User Info and JWT", this.authService.createUserInfo(authentication));
    }


}

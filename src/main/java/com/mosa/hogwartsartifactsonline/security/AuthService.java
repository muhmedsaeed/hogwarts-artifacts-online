package com.mosa.hogwartsartifactsonline.security;

import com.mosa.hogwartsartifactsonline.converter.user.UserToUserDtoConverter;
import com.mosa.hogwartsartifactsonline.dto.UserDto;
import com.mosa.hogwartsartifactsonline.entity.HogwartsUser;
import com.mosa.hogwartsartifactsonline.entity.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userToUserDtoConverter;


    @Autowired
    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }


    public Map<String, Object> createUserInfo(Authentication authentication) {

        Map<String, Object> loginInfo = new HashMap<>();

        // create user info.
        MyUserPrincipal principal = (MyUserPrincipal) authentication.getPrincipal();
        HogwartsUser user = principal.getHogwartsUser();
        UserDto userDto = this.userToUserDtoConverter.convert(user);

        // create a JWT.
        String token = this.jwtProvider.createToken(authentication);

        loginInfo.put("userInfo", userDto);
        loginInfo.put("token", token);

        return loginInfo;
    }
}

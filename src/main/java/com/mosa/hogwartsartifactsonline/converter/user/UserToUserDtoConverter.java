package com.mosa.hogwartsartifactsonline.converter.user;


import com.mosa.hogwartsartifactsonline.dto.UserDto;
import com.mosa.hogwartsartifactsonline.entity.HogwartsUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UserToUserDtoConverter implements Converter<HogwartsUser, UserDto> {


    @Override
    public UserDto convert(HogwartsUser user) {
        final UserDto userDto = new UserDto(
                user.getId(),
                user.getUsername(),
                user.isEnabled(),
                user.getRoles());

        return userDto;
    }
}

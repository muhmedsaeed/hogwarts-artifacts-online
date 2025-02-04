package com.mosa.hogwartsartifactsonline.converter.user;

import com.mosa.hogwartsartifactsonline.dto.UserDto;
import com.mosa.hogwartsartifactsonline.entity.HogwartsUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class UserDtoToUserConverter implements Converter<UserDto, HogwartsUser> {


    @Override
    public HogwartsUser convert(UserDto userDto) {

        HogwartsUser user = new HogwartsUser();
        user.setUsername(userDto.username());
        user.setEnabled(userDto.enabled());
        user.setRoles(userDto.roles());

        return user;
    }


}

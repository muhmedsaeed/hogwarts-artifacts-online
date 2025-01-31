package com.mosa.hogwartsartifactsonline.converter;


import com.mosa.hogwartsartifactsonline.dto.WizardDto;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDto> {


    @Override
    public WizardDto convert(Wizard wizard) {

        return new WizardDto(wizard.getId(),
                wizard.getName(),
                wizard.getNumberOfArtifacts());
    }

}

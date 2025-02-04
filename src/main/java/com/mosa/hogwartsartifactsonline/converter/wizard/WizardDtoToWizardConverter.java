package com.mosa.hogwartsartifactsonline.converter.wizard;


import com.mosa.hogwartsartifactsonline.dto.WizardDto;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class WizardDtoToWizardConverter implements Converter<WizardDto, Wizard> {

    @Override
    public Wizard convert(WizardDto wizardDto) {
        Wizard wizard = new Wizard();
        wizard.setId(wizardDto.id());
        wizard.setName(wizardDto.name());

        return wizard;

    }
}

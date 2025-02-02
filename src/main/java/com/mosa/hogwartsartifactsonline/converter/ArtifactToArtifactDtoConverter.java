package com.mosa.hogwartsartifactsonline.converter;


import com.mosa.hogwartsartifactsonline.dto.ArtifactDto;
import com.mosa.hogwartsartifactsonline.entity.Artifact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDto> {

    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    @Autowired
    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }


    @Override
    public ArtifactDto convert(Artifact artifact) {

        return new ArtifactDto(artifact.getId(),
                artifact.getName(),
                artifact.getDescription(),
                artifact.getImageUrl(),
                artifact.getOwner() != null
                        ? this.wizardToWizardDtoConverter.convert(artifact.getOwner())
                        : null);

    }


}

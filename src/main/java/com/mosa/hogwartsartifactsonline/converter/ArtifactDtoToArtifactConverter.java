package com.mosa.hogwartsartifactsonline.converter;

import com.mosa.hogwartsartifactsonline.dto.ArtifactDto;
import com.mosa.hogwartsartifactsonline.entity.Artifact;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDto, Artifact> {


    @Override
    public Artifact convert(ArtifactDto artifactDto) {

        Artifact artifact = new Artifact();
        artifact.setId(artifactDto.id());
        artifact.setName(artifactDto.name());
        artifact.setDescription(artifactDto.description());
        artifact.setImageUrl(artifactDto.imageUrl());

        return artifact;
    }


}

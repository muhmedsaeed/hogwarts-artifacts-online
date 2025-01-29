package com.mosa.hogwartsartifactsonline.artifact;

import com.mosa.hogwartsartifactsonline.wizard.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks

    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // given
        String artifactId = "1250808601744904191";
        Artifact a = new Artifact();
        a.setId("1250808601744904191");
        a.setName("Deluminator");
        a.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/deluminator.jpg");

        Wizard w1 = new Wizard();
        w1.setName("Albus Dumbledore");
        a.setOwner(w1);

        given(artifactRepository.findById(artifactId)).willReturn(Optional.of(a));
        // when
        Artifact returnedArtifact = artifactService.findById(artifactId);

        // then
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(a.getImageUrl());

        verify(artifactRepository, times(1)).findById(artifactId);
    }


}
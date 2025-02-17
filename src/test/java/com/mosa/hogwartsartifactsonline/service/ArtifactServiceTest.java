package com.mosa.hogwartsartifactsonline.service;

import com.mosa.hogwartsartifactsonline.entity.Artifact;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import com.mosa.hogwartsartifactsonline.exception.ObjectNotFoundException;
import com.mosa.hogwartsartifactsonline.repo.ArtifactRepository;
import com.mosa.hogwartsartifactsonline.utils.IdWorker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdWorker idWorker;

    @InjectMocks
    ArtifactService artifactService;

    List<Artifact> artifacts;

    @BeforeEach
    void setUp() {
        artifacts = new ArrayList<>();

        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/deluminator.jpg");

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/elder-wand.jpg");

        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.");
        a4.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/marauders-map.jpg");

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.");
        a5.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/sword-of-gryffindor.jpg");

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        a6.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/resurrection-stone.jpg");

        this.artifacts.add(a1);
        this.artifacts.add(a2);
        this.artifacts.add(a3);
        this.artifacts.add(a4);
        this.artifacts.add(a5);
        this.artifacts.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // given
        Artifact a = this.artifacts.get(1);
        Wizard w1 = new Wizard();
        w1.setName("Harry Potter");
        a.setOwner(w1);

        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a));
        // when
        Artifact returnedArtifact = artifactService.findById("1250808601744904192");

        // then
        assertThat(returnedArtifact.getId()).isEqualTo(a.getId());
        assertThat(returnedArtifact.getDescription()).isEqualTo(a.getDescription());
        assertThat(returnedArtifact.getName()).isEqualTo(a.getName());
        assertThat(returnedArtifact.getImageUrl()).isEqualTo(a.getImageUrl());

        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindByIdNotFound() {
        // given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // when
        Throwable thrown = catchThrowable(() -> {
            artifactService.findById("1250808601744904192");
        });

        // then
        assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Could not find artifact with Id 1250808601744904192 :(");
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }


    @Test
    void  testFindAllSuccess() {
        // Given
        given(artifactRepository.findAll()).willReturn(artifacts);

        // when
        List<Artifact> returnedArtifacts = this.artifactService.findAll();

        // Then
        assertThat(returnedArtifacts.size()).isEqualTo(artifacts.size());
        assertThat(returnedArtifacts.get(0).getId()).isEqualTo(artifacts.get(0).getId());
        assertThat(returnedArtifacts.get(1).getId()).isEqualTo(artifacts.get(1).getId());

        verify(artifactRepository, times(1)).findAll();

    }


    @Test
    void testSaveSuccess() {
        // Given
        Artifact newArtifact = new Artifact();
        newArtifact.setName("Artifact 3");
        newArtifact.setDescription("Description...");
        newArtifact.setImageUrl("ImageUrl...");

        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(newArtifact)).willReturn(newArtifact);

        // When
        Artifact savedArtifact = artifactService.save(newArtifact);

        // Then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(newArtifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(newArtifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(newArtifact.getImageUrl());

        verify(artifactRepository, times(1)).save(newArtifact);
    }


    @Test
    void testUpdateSuccess() {
        // Given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");


        Artifact update = new Artifact();
        update.setId("1250808601744904192");
        update.setName("Invisibility Cloak");
        update.setDescription("This is a new Description");
        update.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        given(this.artifactRepository.save(oldArtifact)).willReturn(oldArtifact);

        // When
        Artifact updatedArtifact = this.artifactService.update("1250808601744904192", update);

        // Then
        assertThat(updatedArtifact.getId()).isEqualTo(update.getId());
        assertThat(updatedArtifact.getName()).isEqualTo(update.getName());
        assertThat(updatedArtifact.getDescription()).isEqualTo(update.getDescription());
        assertThat(updatedArtifact.getImageUrl()).isEqualTo(update.getImageUrl());

        verify(this.artifactRepository, times(1)).save(oldArtifact);
        verify(this.artifactRepository, times(1)).findById("1250808601744904192");
    }


    @Test
    void testUpdateNotFound() {
        // Given

        Artifact update = new Artifact();
        update.setName("Invisibility Cloak");
        update.setDescription("This is a new Description");
        update.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            artifactService.update("1250808601744904192", update);
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class);
        verify(this.artifactRepository, times(1)).findById("1250808601744904192");
    }


    @Test
    void testDeleteSuccess() {
        // Given
        Artifact deleteArtifact = new Artifact();
        deleteArtifact.setId("1250808601744904196");
        deleteArtifact.setName("Resurrection Stone");
        deleteArtifact.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        deleteArtifact.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/resurrection-stone.jpg");

        given(this.artifactRepository.findById("1250808601744904196")).willReturn(Optional.of(deleteArtifact));
        doNothing().when(this.artifactRepository).deleteById("1250808601744904196");

        // When
        this.artifactService.delete("1250808601744904196");

        // Then
        verify(this.artifactRepository, times(1)).findById("1250808601744904196");
        verify(this.artifactRepository, times(1)).deleteById("1250808601744904196");
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.artifactRepository.findById("1250808601744904196")).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.artifactService.delete("1250808601744904196");
        });

        // Then
        verify(this.artifactRepository, times(1)).findById("1250808601744904196");
    }


}
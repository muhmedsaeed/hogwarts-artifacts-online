package com.mosa.hogwartsartifactsonline.service;

import com.mosa.hogwartsartifactsonline.entity.Artifact;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import com.mosa.hogwartsartifactsonline.exception.ObjectNotFoundException;
import com.mosa.hogwartsartifactsonline.repo.ArtifactRepository;
import com.mosa.hogwartsartifactsonline.repo.WizardRepository;
import com.mosa.hogwartsartifactsonline.utils.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @Mock
    IdGenerator idGenerator;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;

    @BeforeEach
    void setUp() {
        wizards = new ArrayList<>();

        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus Dumbledore");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        wizards.add(w1);
        wizards.add(w2);
        wizards.add(w3);

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void testFindByIdSuccess() {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));

        // When
        Wizard returnedWizard = this.wizardService.findById(1);

        // Then
        assertThat(returnedWizard.getId()).isEqualTo(wizard.getId());
        assertThat(returnedWizard.getName()).isEqualTo(wizard.getName());

        verify(this.wizardRepository, times(1)).findById(1);

    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(this.wizardRepository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            this.wizardService.findById(1);
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find wizard with Id 1 :(");

        verify(this.wizardRepository, times(1)).findById(1);

    }

    @Test
    void testFindAllSuccess() {
        // Given
        given(this.wizardRepository.findAll()).willReturn(this.wizards);

        // When
        List<Wizard> returnedWizards = this.wizardService.findAll();

        // Then
        assertThat(returnedWizards.size()).isEqualTo(wizards.size());
        assertThat(returnedWizards.get(0).getId()).isEqualTo(wizards.get(0).getId());
        assertThat(returnedWizards.get(0).getName()).isEqualTo(wizards.get(0).getName());

        verify(this.wizardRepository, times(1)).findAll();

    }


    @Test
    void testSaveSuccess() {
        // Given
        Wizard saveWizard = new Wizard();
        saveWizard.setName("Hermione Granger");

        given(this.idGenerator.generateId()).willReturn(4);
        given(this.wizardRepository.save(saveWizard)).willReturn(saveWizard);

        // When
        Wizard returnedWizard = this.wizardService.save(saveWizard);

        // Then
        assertThat(returnedWizard.getId()).isEqualTo(saveWizard.getId());
        assertThat(returnedWizard.getName()).isEqualTo(saveWizard.getName());

        verify(this.wizardRepository, times(1)).save(saveWizard);

    }



    @Test
    void testUpdateSuccess() {
        // Given
        Wizard oldWizard = new Wizard();
        oldWizard.setId(1);
        oldWizard.setName("Albus Dumbledore");

        Wizard update = new Wizard();
        update.setName("New Wizard...");

        given(this.wizardRepository.findById(1)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);

        // When
        Wizard returnedWizard = this.wizardService.update(1 ,update);

        // Then
        assertThat(returnedWizard.getId()).isEqualTo(1);
        assertThat(returnedWizard.getName()).isEqualTo(update.getName());

        verify(this.wizardRepository, times(1)).findById(1);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }


    @Test
    void testUpdateNotFound() {
        // Given
        Wizard update = new Wizard();
        update.setName("New Wizard...");

        given(this.wizardRepository.findById(5)).willThrow(new ObjectNotFoundException("wizard", 5));

        // When

        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.update(5 ,update);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(5);
    }


    @Test
    void testDeleteSuccess() {
        // Given
        Wizard deletedWizard = new Wizard();
        deletedWizard.setId(3);
        deletedWizard.setName("Neville Longbottom");

        given(this.wizardRepository.findById(3)).willReturn(Optional.of(deletedWizard));
        doNothing().when(this.wizardRepository).deleteById(3);

        // When
        this.wizardService.delete(3);

        // Then
        verify(this.wizardRepository, times(1)).findById(3);
        verify(this.wizardRepository, times(1)).deleteById(3);

    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.wizardRepository.findById(5)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.delete(5);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(5);

    }


    @Test
    void testAssignArtifactSuccess() {
        // Given
        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/deluminator.jpg");


        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifact(a2);

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a2));
        given(this.wizardRepository.findById(3)).willReturn(Optional.of(w3));

        // When
        this.wizardService.assignArtifact(3, "1250808601744904192");

        // Then
        assertThat(a2.getOwner().getId()).isEqualTo(w3.getId());
        assertThat(w3.getArtifacts()).contains(a2);

        verify(this.artifactRepository, times(1)).findById("1250808601744904192");
        verify(this.wizardRepository, times(1)).findById(3);

    }


    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() {
        // Given
        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/deluminator.jpg");


        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifact(a2);

        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(a2));
        given(this.wizardRepository.findById(3)).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find wizard with Id 3 :(");
        assertThat(a2.getOwner().getId()).isEqualTo(2);

        verify(this.artifactRepository, times(1)).findById("1250808601744904192");
        verify(this.wizardRepository, times(1)).findById(3);

    }


    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId() {
        // Given
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.assignArtifact(3, "1250808601744904192");
        });

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find artifact with Id 1250808601744904192 :(");

        verify(this.artifactRepository, times(1)).findById("1250808601744904192");

    }


}
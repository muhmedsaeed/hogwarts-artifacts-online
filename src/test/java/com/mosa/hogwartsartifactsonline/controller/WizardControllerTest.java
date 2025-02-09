package com.mosa.hogwartsartifactsonline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosa.hogwartsartifactsonline.dto.WizardDto;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import com.mosa.hogwartsartifactsonline.exception.ObjectNotFoundException;
import com.mosa.hogwartsartifactsonline.service.WizardService;
import com.mosa.hogwartsartifactsonline.system.StatusCode;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // it turns off spring security for this test class.
class WizardControllerTest {

    @MockitoBean
    WizardService wizardService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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
    void testFindAllWizardsSuccess() throws Exception {
        // Given
        given(this.wizardService.findAll()).willReturn(wizards);

        // When and Then
        this.mockMvc
                .perform(get(this.baseUrl + "/wizards").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data[0].id").value(wizards.get(0).getId()))
                .andExpect(jsonPath("$.data[0].name").value(wizards.get(0).getName()))
                .andExpect(jsonPath("$.data", Matchers.hasSize(wizards.size())));
    }


    @Test
    void testFindWizardByIdSuccess() throws Exception {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        given(this.wizardService.findById(1)).willReturn(wizard);

        // When and Then
        this.mockMvc
                .perform(get(this.baseUrl + "/wizards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value(wizards.get(0).getId()))
                .andExpect(jsonPath("$.data.name").value(wizards.get(0).getName()));
    }


    @Test
    void testFindWizardByIdNotFound() throws Exception {
        // Given
        given(this.wizardService.findById(1)).willThrow(new ObjectNotFoundException("wizard", 1));

        // When and Then
        this.mockMvc
                .perform(get(this.baseUrl + "/wizards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 1 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    void testAddWizardSuccess() throws Exception {
        // Given
        WizardDto saveWizardDto = new WizardDto(
                null,
                "Albus Dumbledore",
                null);
        String json = this.objectMapper.writeValueAsString(saveWizardDto);

        Wizard saveWizard = new Wizard();
        saveWizard.setName("Albus Dumbledore");

        given(this.wizardService.save(Mockito.any(Wizard.class))).willReturn(saveWizard);

        // When and Then
        this.mockMvc
                .perform(post(this.baseUrl + "/wizards/add").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.name").value("Albus Dumbledore"));
    }


    @Test
    void testUpdateWizardSuccess() throws Exception {
        // Given
        WizardDto updateDto = new WizardDto(null, "A New Name...", null);
        String json = objectMapper.writeValueAsString(updateDto);

        Wizard updated = new Wizard();
        updated.setId(1);
        updated.setName("A New Name...");

        given(this.wizardService.update(eq(1), Mockito.any(Wizard.class))).willReturn(updated);

        // When and Then
        this.mockMvc
                .perform(put(this.baseUrl + "/wizards/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.name").value("A New Name..."));
    }



    @Test
    void testUpdateWizardNotFound() throws Exception {
        // Given
        WizardDto updateDto = new WizardDto(null, "A New Name...", null);
        String json = objectMapper.writeValueAsString(updateDto);

        given(this.wizardService.update(eq(5), Mockito.any(Wizard.class))).willThrow(new ObjectNotFoundException("wizard", 5));

        // When and Then
        this.mockMvc
                .perform(put(this.baseUrl + "/wizards/5").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 5 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    void testDeleteWizardSuccess() throws Exception {
        // Given

        doNothing().when(this.wizardService).delete(2);

        // When and then
        this.mockMvc
                .perform(delete(this.baseUrl + "/wizards/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").isEmpty());

    }


    @Test
    void testDeleteWizardNotFound() throws Exception {
        // Given

        doThrow(new ObjectNotFoundException("wizard", 5)).when(this.wizardService).delete(5);

        // When and then
        this.mockMvc
                .perform(delete(this.baseUrl + "/wizards/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 5 :("))
                .andExpect(jsonPath("$.data").isEmpty());

    }


    @Test
    void testAssignArtifactSuccess() throws Exception {
        // Given
        doNothing().when(this.wizardService).assignArtifact(3, "1250808601744904192");

        // When and then
        this.mockMvc
                .perform(put(this.baseUrl + "/wizards/3/artifacts/1250808601744904192").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Artifact Assignment Success"))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("wizard", 3)).when(this.wizardService).assignArtifact(3, "1250808601744904192");

        // When and then
        this.mockMvc
                .perform(put(this.baseUrl + "/wizards/3/artifacts/1250808601744904192").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find wizard with Id 3 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }


    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("artifact", "1250808601744904192")).when(this.wizardService).assignArtifact(3, "1250808601744904192");

        // When and then
        this.mockMvc
                .perform(put(this.baseUrl + "/wizards/3/artifacts/1250808601744904192").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with Id 1250808601744904192 :("))
                .andExpect(jsonPath("$.data").isEmpty());
    }



}
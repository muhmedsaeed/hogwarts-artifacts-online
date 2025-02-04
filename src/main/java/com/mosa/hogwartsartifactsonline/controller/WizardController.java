package com.mosa.hogwartsartifactsonline.controller;


import com.mosa.hogwartsartifactsonline.converter.wizard.WizardDtoToWizardConverter;
import com.mosa.hogwartsartifactsonline.converter.wizard.WizardToWizardDtoConverter;
import com.mosa.hogwartsartifactsonline.dto.WizardDto;
import com.mosa.hogwartsartifactsonline.entity.Wizard;
import com.mosa.hogwartsartifactsonline.service.WizardService;
import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.endpoint.base-url}/wizards")
public class WizardController {

    private final WizardService wizardService;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

    @Autowired
    public WizardController(WizardService wizardService, WizardToWizardDtoConverter wizardToWizardDtoConverter, WizardDtoToWizardConverter wizardDtoToWizardConverter) {
        this.wizardService = wizardService;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
    }


    @GetMapping
    public Result findAllWizards() {
        List<Wizard> wizards = this.wizardService.findAll();

        List<WizardDto> wizardsDto = wizards.stream()
                .map(this.wizardToWizardDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", wizardsDto);
    }


    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable Integer wizardId) {

        Wizard wizard = this.wizardService.findById(wizardId);

        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(wizard);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", wizardDto);
    }


    @PostMapping("/add")
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto) {
        Wizard wizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard = this.wizardService.save(wizard);
        wizardDto = this.wizardToWizardDtoConverter.convert(wizard);

        return new Result(true, StatusCode.SUCCESS, "Add Success", wizardDto);
    }


    @PutMapping("/{wizardId}")
    public Result updateWizard(@Valid @RequestBody WizardDto wizardDto, @PathVariable Integer wizardId) {

        Wizard wizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        wizard = this.wizardService.update(wizardId, wizard);
        wizardDto = this.wizardToWizardDtoConverter.convert(wizard);

        return  new Result(true, StatusCode.SUCCESS, "Update Success", wizardDto);
    }


    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Integer wizardId) {

        this.wizardService.delete(wizardId);

        return new Result(true, StatusCode.SUCCESS,"Delete Success", null);
    }


    @PutMapping("/{wizardId}/artifacts/{artifactId}")
    public Result assignArtifact(@PathVariable Integer wizardId, @PathVariable String artifactId) {

        this.wizardService.assignArtifact(wizardId, artifactId);

        return new Result(true, StatusCode.SUCCESS, "Artifact Assignment Success", null);
    }


}

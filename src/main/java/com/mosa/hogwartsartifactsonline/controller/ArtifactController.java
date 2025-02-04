package com.mosa.hogwartsartifactsonline.controller;

import com.mosa.hogwartsartifactsonline.converter.artifact.ArtifactDtoToArtifactConverter;
import com.mosa.hogwartsartifactsonline.converter.artifact.ArtifactToArtifactDtoConverter;
import com.mosa.hogwartsartifactsonline.dto.ArtifactDto;
import com.mosa.hogwartsartifactsonline.entity.Artifact;
import com.mosa.hogwartsartifactsonline.service.ArtifactService;
import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("${api.endpoint.base-url}/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    @Autowired
    public ArtifactController(ArtifactService artifactService,
                              ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter,
                              ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }


    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId) {
        Artifact foundArtifact = artifactService.findById(artifactId);
        ArtifactDto artifactDto = artifactToArtifactDtoConverter.convert(foundArtifact);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDto);
    }


    @GetMapping
    public Result findAllArtifacts() {

        List<Artifact> artifacts = this.artifactService.findAll();
        // convert artifacts to list of artifacts dto
        List<ArtifactDto> artifactsDto = artifacts.stream()
                .map(this.artifactToArtifactDtoConverter::convert)
                .toList();

        return new Result(true, StatusCode.SUCCESS, "Find All Success", artifactsDto);
    }


    @PostMapping("/add")
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto) {

        // convert artifact dto to artifact
        Artifact artifact = artifactDtoToArtifactConverter.convert(artifactDto);
        Artifact savedArtifact = artifactService.save(artifact);
        // convert saved artifact to artifact dto
        artifactDto = artifactToArtifactDtoConverter.convert(savedArtifact);

        return new Result(true, StatusCode.SUCCESS, "Add Success", artifactDto);
    }


    @PutMapping("/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto newArtifactDto) {

        Artifact newArtifact = this.artifactDtoToArtifactConverter.convert(newArtifactDto);

        Artifact updatedArtifact = this.artifactService.update(artifactId, newArtifact);

        ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConverter.convert(updatedArtifact);

        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedArtifactDto);
    }


    @DeleteMapping("/{artifactId}")
    public Result deleteArtifact(@PathVariable String artifactId) {

        this.artifactService.delete(artifactId);

        return new Result(true, StatusCode.SUCCESS, "Delete Success", null);
    }




}

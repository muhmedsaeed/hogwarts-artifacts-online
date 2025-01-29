package com.mosa.hogwartsartifactsonline.artifact;

import com.mosa.hogwartsartifactsonline.system.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class ArtifactController {

    private final ArtifactService artifactService;

    @Autowired
    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }


    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result getArtifactById(@PathVariable String artifactId) {

        return null;
    }



}

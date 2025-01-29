package com.mosa.hogwartsartifactsonline.artifact;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    public Artifact findById(String artifactId) {

        return artifactRepository.findById(artifactId).get();

    }

}

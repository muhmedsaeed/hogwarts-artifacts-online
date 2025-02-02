package com.mosa.hogwartsartifactsonline.service;

import com.mosa.hogwartsartifactsonline.repo.ArtifactRepository;
import com.mosa.hogwartsartifactsonline.entity.Artifact;
import com.mosa.hogwartsartifactsonline.exception.ArtifactNotFoundException;
import com.mosa.hogwartsartifactsonline.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ArtifactService {

    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    @Autowired
    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }


    public Artifact findById(String artifactId) {
        return artifactRepository
                .findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public List<Artifact> findAll() {

        return this.artifactRepository.findAll();
    }


    public Artifact save(Artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }


    public Artifact update(String artifactId, Artifact update) {

        // first find the artifact
        return this.artifactRepository
                .findById(artifactId)
                .map(oldArtifact -> {
                    // update the old artifact
                    oldArtifact.setName(update.getName());
                    oldArtifact.setDescription(update.getDescription());
                    oldArtifact.setImageUrl(update.getImageUrl());

                    // save updated artifact then return it
                    return this.artifactRepository.save(oldArtifact);
                })
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId) {

        this.artifactRepository
                .findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));

        this.artifactRepository.deleteById(artifactId);
    }


}

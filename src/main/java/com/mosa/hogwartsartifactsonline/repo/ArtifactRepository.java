package com.mosa.hogwartsartifactsonline.repo;


import com.mosa.hogwartsartifactsonline.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, String> {

    // nothing to do
}

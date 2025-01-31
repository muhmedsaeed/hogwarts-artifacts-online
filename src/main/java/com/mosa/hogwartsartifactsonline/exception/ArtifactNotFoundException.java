package com.mosa.hogwartsartifactsonline.exception;

public class ArtifactNotFoundException extends RuntimeException {

    public ArtifactNotFoundException(String artifactId) {
        super("could not find artifact with Id " + artifactId + " :(");
    }
}

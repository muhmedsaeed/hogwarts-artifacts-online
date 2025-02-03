package com.mosa.hogwartsartifactsonline.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(String objectName, Object objectId) {
        super("Could not find " + objectName + " with Id " + objectId + " :(");
    }
}

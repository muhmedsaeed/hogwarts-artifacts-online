package com.mosa.hogwartsartifactsonline.artifact;

import com.mosa.hogwartsartifactsonline.wizard.Wizard;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Artifact implements Serializable {

    @Id
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    @ManyToOne()
    private Wizard wizard;



}

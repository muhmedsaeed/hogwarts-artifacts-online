package com.mosa.hogwartsartifactsonline.wizard;

import com.mosa.hogwartsartifactsonline.artifact.Artifact;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "wizard")
@Data
@NoArgsConstructor
public class Wizard implements Serializable {

    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "wizard", cascade = CascadeType.PERSIST)
    private List<Artifact> artifacts = new ArrayList<>();




}

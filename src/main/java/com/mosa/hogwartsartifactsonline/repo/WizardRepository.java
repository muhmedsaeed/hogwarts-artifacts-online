package com.mosa.hogwartsartifactsonline.repo;

import com.mosa.hogwartsartifactsonline.entity.Wizard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WizardRepository extends JpaRepository<Wizard, Integer> {

}

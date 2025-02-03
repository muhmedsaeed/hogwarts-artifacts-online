package com.mosa.hogwartsartifactsonline.service;

import com.mosa.hogwartsartifactsonline.entity.Wizard;
import com.mosa.hogwartsartifactsonline.exception.ObjectNotFoundException;
import com.mosa.hogwartsartifactsonline.repo.WizardRepository;
import com.mosa.hogwartsartifactsonline.utils.IdGenerator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class WizardService {

    private final WizardRepository wizardRepository;
    private final IdGenerator idGenerator;

    @Autowired
    public WizardService(WizardRepository wizardRepository, IdGenerator idGenerator) {
        this.wizardRepository = wizardRepository;
        this.idGenerator = idGenerator;
    }



    public Wizard findById(Integer wizardId) {

        Wizard wizard = this.wizardRepository.findById(wizardId).orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        return wizard;
    }

    public List<Wizard> findAll() {

        return this.wizardRepository.findAll();
    }


    public Wizard save(Wizard newWizard) {

        newWizard.setId(idGenerator.generateId());
        return this.wizardRepository.save(newWizard);
    }

    public Wizard update(Integer wizardId, Wizard newWizard) {
        return this.wizardRepository
                .findById(wizardId)
                .map((oldWizard) -> {
                    oldWizard.setName(newWizard.getName());
                    return this.wizardRepository.save(oldWizard);
                })
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }


    public void delete(Integer wizardId) {
        Wizard deleteWizard = this.wizardRepository
                .findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // to avoid a deletion error
        deleteWizard.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }




}

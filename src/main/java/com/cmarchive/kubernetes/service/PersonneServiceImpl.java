package com.cmarchive.kubernetes.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmarchive.kubernetes.model.Personne;
import com.cmarchive.kubernetes.repository.PersonneRepository;

@Service
public class PersonneServiceImpl implements PersonneService{

    @Autowired
    private PersonneRepository personneRepository;

    public PersonneServiceImpl(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public List<Personne> list() {
        return personneRepository.findAllByOrderByNom();
    }

    @Override
    public Personne get(Long id) {
        return personneRepository.findByPersonneId(id);
    }

    @Override
    public Personne save(Personne personne) {
        return personneRepository.save(personne);
    }

    @Override
    public void delete(Long id) {
        personneRepository.deleteById(id);
    }
}

package com.cmarchive.kubernetes.service;

import java.util.List;
import com.cmarchive.kubernetes.model.Personne;

public interface PersonneService {

    List<Personne> list();
    Personne get(Long id);
    Personne save(Personne personne);
    void delete(Personne personne);

}

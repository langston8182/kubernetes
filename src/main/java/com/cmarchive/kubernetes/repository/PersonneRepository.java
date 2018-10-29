package com.cmarchive.kubernetes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cmarchive.kubernetes.model.Personne;

@Repository
public interface PersonneRepository extends JpaRepository<Personne, Long> {

    List<Personne> findAllByOrderByNom();
    Personne findByPersonneId(Long id);
}

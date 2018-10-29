package com.cmarchive.kubernetes.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cmarchive.kubernetes.model.Personne;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class PersonneRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonneRepository personneRepository;

    @Test
    public void listerPersonne_Nominal() {
        Personne personne = new Personne().withDateNaissance(LocalDate.of(1982, 1, 8)).withNom("Marchive").withPrenom("Cyril");
        Personne personne2 = new Personne().withDateNaissance(LocalDate.of(1991, 12, 12)).withNom("Boussat").withPrenom("Prenom");
        entityManager.persist(personne);
        entityManager.persist(personne2);
        entityManager.flush();

        List<Personne> personnes = personneRepository.findAllByOrderByNom();

        assertThat(personnes).isNotEmpty()
                             .containsExactly(personne2, personne);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void recupererPersonne_Nominal() {
        Personne personne = creerPersonne();

        Personne resultat = personneRepository.findByPersonneId(1L);

        assertThat(resultat).isNotNull()
                            .isEqualTo(personne);
    }

    @Test
    public void sauvegarderPersonne_Nominal() {
        Personne personne = new Personne()
                .withDateNaissance(LocalDate.of(1982, 1, 8))
                .withNom("Marchive")
                .withPrenom("Cyril");

        Personne resultat = personneRepository.save(personne);

        assertThat(resultat).isNotNull()
                            .isEqualTo(personne);
    }

    @Test
    public void supprimerPersonne_Nominal() {
        Personne personne = creerPersonne();

        personneRepository.delete(personne);

        assertThat(personneRepository.findAll()).isEmpty();
    }

    private Personne creerPersonne() {
        Personne personne = new Personne()
                .withDateNaissance(LocalDate.of(1982, 1, 8))
                .withNom("Marchive")
                .withPrenom("Cyril");
        entityManager.persist(personne);
        entityManager.flush();
        return personne;
    }
}

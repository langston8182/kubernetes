package com.cmarchive.kubernetes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cmarchive.kubernetes.model.Personne;
import com.cmarchive.kubernetes.repository.PersonneRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class PersonneServiceImplTest {

    @InjectMocks
    private PersonneServiceImpl personneService;

    @Mock
    private PersonneRepository personneRepository;

    @Test
    public void listerPersonne_Nominal() {
        Personne personne1 = new Personne();
        Personne personne2 = new Personne();
        List<Personne> personnes = Stream.of(personne1, personne2).collect(Collectors.toList());
        given(personneRepository.findAllByOrderByNom()).willReturn(personnes);

        List<Personne> resultat = personneService.list();

        then(personneRepository).should().findAllByOrderByNom();
        assertThat(resultat).containsExactlyElementsOf(personnes);
    }

    @Test
    public void recupererPersonne_Nominal() {
        Long id = 1L;
        Personne personne = mock(Personne.class);
        given(personneRepository.findByPersonneId(eq(id))).willReturn(personne);

        Personne resultat = personneService.get(id);

        then(personneRepository).should().findByPersonneId(eq(id));
        assertThat(personne).isEqualTo(resultat);
    }

    @Test
    public void sauvegarderPersonne_Nominal() {
        Personne personne = mock(Personne.class);
        Personne personneSauvegarde = mock(Personne.class);
        given(personneRepository.save(personne)).willReturn(personneSauvegarde);

        Personne resultat = personneService.save(personne);

        then(personneRepository).should().save(personne);
        assertThat(personneSauvegarde).isEqualTo(resultat);
    }

    @Test
    public void supprimerPersonne_Nominal() {
        personneService.delete(1L);

        then(personneRepository).should().deleteById(1L);
    }
}

package com.cmarchive.kubernetes.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.cmarchive.kubernetes.model.Personne;
import com.cmarchive.kubernetes.repository.PersonneRepository;

@RunWith(MockitoJUnitRunner.class)
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
}
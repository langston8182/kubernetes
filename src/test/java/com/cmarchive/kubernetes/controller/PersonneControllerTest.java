package com.cmarchive.kubernetes.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.cmarchive.kubernetes.model.Personne;
import com.cmarchive.kubernetes.service.PersonneService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(PersonneController.class)
public class PersonneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonneService personneService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void listerPersonne_Nominal() throws Exception {
        Personne personne = new Personne().withDateNaissance(LocalDate.of(1982, 1, 8)).withNom("Marchive").withPrenom("Cyril");
        Personne personne2 = new Personne().withDateNaissance(LocalDate.of(1991, 12, 12)).withNom("Boussat").withPrenom("Prenom");
        List<Personne> personnes = Stream.of(personne, personne2).collect(Collectors.toList());
        given(personneService.list()).willReturn(personnes);

        mockMvc.perform(get("/api/personnes")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(2)))
               .andDo(print());
    }

    @Test
    public void recupererPersonne_Nominal() throws Exception {
        Personne personne = new Personne().withDateNaissance(LocalDate.of(1982, 1, 8)).withNom("Marchive").withPrenom("Cyril");
        given(personneService.get(anyLong())).willReturn(personne);

        mockMvc.perform(get("/api/personnes/1")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nom", equalTo(personne.getNom())))
               .andExpect(jsonPath("$.prenom", equalTo(personne.getPrenom())))
               .andExpect(jsonPath("$.dateNaissance", is(personne.getDateNaissance().format(DateTimeFormatter.ISO_DATE))))
               .andDo(print());
    }

    @Test
    public void sauvegarderPersonne_Nominal() throws Exception {
        Personne personne = new Personne().withDateNaissance(LocalDate.of(1982, 1, 8)).withNom("Marchive").withPrenom("Cyril");
        given(personneService.save(any(Personne.class))).willReturn(personne);

        mockMvc.perform(post("/api/personnes")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(personne)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nom", equalTo(personne.getNom())))
               .andExpect(jsonPath("$.prenom", equalTo(personne.getPrenom())))
               .andExpect(jsonPath("$.dateNaissance", is(personne.getDateNaissance().format(DateTimeFormatter.ISO_DATE))))
               .andDo(print());
    }

    @Test
    public void supprimerPersonne_Nominal() throws Exception {
        Long id = 1L;
        willDoNothing().given(personneService).delete(id);

        mockMvc.perform(delete("/api/personnes/1")
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").doesNotExist())
               .andDo(print());
    }

}


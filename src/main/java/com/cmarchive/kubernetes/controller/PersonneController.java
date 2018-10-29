package com.cmarchive.kubernetes.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.cmarchive.kubernetes.model.Personne;
import com.cmarchive.kubernetes.service.PersonneService;

@RestController
@RequestMapping("/api/personnes")
public class PersonneController {

    @Autowired
    private PersonneService personneService;

    @GetMapping
    public List<Personne> list() {
        return personneService.list();
    }

    @GetMapping("/{id}")
    public Personne get(@PathVariable Long id) {
        return personneService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Personne save(@RequestBody Personne personne) {
        return personneService.save(personne);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        personneService.delete(id);
    }
}

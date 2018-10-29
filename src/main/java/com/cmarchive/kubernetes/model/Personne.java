package com.cmarchive.kubernetes.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personneId;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;

    public Personne() {
    }

    public Personne(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
    }

    public Long getPersonneId() {
        return personneId;
    }

    public Personne withPersonneId(Long id) {
        this.personneId = id;
        return this;
    }

    public String getNom() {
        return nom;
    }

    public Personne withNom(String nom) {
        this.nom = nom;
        return this;
    }

    public String getPrenom() {
        return prenom;
    }

    public Personne withPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Personne withDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }
}

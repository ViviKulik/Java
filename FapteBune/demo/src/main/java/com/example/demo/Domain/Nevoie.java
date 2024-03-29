package com.example.demo.Domain;

import java.time.LocalDateTime;

public class Nevoie extends Entity<Long> {
    private String titlu;
    private String descriere;
    private LocalDateTime deadline;
    private Long omnevoie;
    private Long omSalvator;
    private Erou erou;

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getOmnevoie() {
        return omnevoie;
    }

    public void setOmnevoie(Long omnevoie) {
        this.omnevoie = omnevoie;
    }

    public Long getOmSalvator() {
        return omSalvator;
    }

    public void setOmSalvator(Long omSalvator) {
        this.omSalvator = omSalvator;
    }

    public Erou getErou() {
        return erou;
    }

    public void setErou(Erou erou) {
        this.erou = erou;
    }

    public Nevoie(String titlu, String descriere, LocalDateTime deadline) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.deadline = deadline;
        this.omnevoie = null;
        this.omSalvator = null;
        this.erou = Erou.caut_erou;
    }
}

package com.example.demo.Domain.Validator;

public class Validators {

    public void validate(String nume,String Prenume,String username,String parola) throws Exception {
        if(nume.length() < 3)
            throw new Exception("EROARE");
        if(Prenume.length()<3)
            throw  new Exception("eroare");
        if (username.length() < 3)
            throw new Exception("exvepte");
        if (parola.length() < 3)
            throw new Exception("EXCEPTIE");
    }
    public void validate(String titlu,String descriere) throws Exception {
        if(titlu.length() < 3)
            throw new Exception("EXCEPTIE");
        if(descriere.length()<3)
            throw new Exception("EXCEPTIE");
    }
}

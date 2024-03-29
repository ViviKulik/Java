package com.example.demo.Service;

import com.example.demo.Domain.Nevoie;
import com.example.demo.Domain.Validator.Validators;
import com.example.demo.Observer.Observable;
import com.example.demo.Observer.Observer;
import com.example.demo.Repository.RepositoryNevoie;
import com.example.demo.Repository.RepositoryPersoana;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

public class ServiceNevoie implements Observable {

    private RepositoryNevoie repositoryNevoie;
    private RepositoryPersoana repositoryPersoana;

    private Validators validators = new Validators();
    public ServiceNevoie(RepositoryNevoie repositoryNevoie,RepositoryPersoana repositoryPersoana) {
        this.repositoryPersoana =repositoryPersoana;
        this.repositoryNevoie = repositoryNevoie;
    }

    public Iterable<Nevoie> findall()
    {
        return repositoryNevoie.findall();
    }

    public Iterable<Nevoie> nevoies(Long id)
    {
        return StreamSupport.stream(repositoryNevoie.findall().spliterator(),false).filter(x-> Objects.equals(repositoryPersoana.findonebyid(x.getOmnevoie()).getOras(), repositoryPersoana.findonebyid(id).getOras())).toList();

    }
    public void update(Long id,Long id2)
    {
        repositoryNevoie.update(id,id2);
        notifyall();

    }

    public void save(Nevoie nevoie) throws Exception {
        validators.validate(nevoie.getTitlu(),nevoie.getDescriere());
        repositoryNevoie.save(nevoie);
        notifyall();
    }
    public Iterable<Nevoie> fapta_buna(Long id)
    {
        return StreamSupport.stream(repositoryNevoie.findall().spliterator(),false).filter(x->x.getOmSalvator().equals(id)).toList();
    }

    List<Observer> observers = new ArrayList<>();
    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyall() {

        for (var o:observers
             ) {
            o.update();
        }
    }
}

package com.example.demo.Service;

import com.example.demo.Domain.Persoana;
import com.example.demo.Domain.Validator.Validators;
import com.example.demo.Observer.Observable;
import com.example.demo.Observer.Observer;
import com.example.demo.Repository.RepositoryPersoana;

import java.util.ArrayList;
import java.util.List;

public class ServicePersoana implements Observable {


    private RepositoryPersoana repositoryPersoana;

    private Validators validators = new Validators();
    public ServicePersoana(RepositoryPersoana repositoryPersoana) {
        this.repositoryPersoana = repositoryPersoana;
    }

    public Iterable<Persoana> findall()
    {
        return repositoryPersoana.findall();
    }

    public Persoana findonlogin(String username)
    {
        return repositoryPersoana.findonelogin(username);
    }
    public Iterable<String> allusers()
    {
        return repositoryPersoana.findallUsers();
    }
    public void save(Persoana persoana) throws Exception {
        validators.validate(persoana.getNume(),persoana.getPrenume(),persoana.getUsername(),persoana.getParola());
        repositoryPersoana.save(persoana);
        notifyall();

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


package com.example.demo.Repository;

import com.example.demo.Domain.Entity;

public interface RepoInterface <ID,E extends Entity<ID>> {


    public Iterable<E> findall();

    public E findone(E e);

    public void save(E e);


}

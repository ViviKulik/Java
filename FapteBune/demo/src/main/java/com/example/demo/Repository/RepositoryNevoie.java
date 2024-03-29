package com.example.demo.Repository;

import com.example.demo.Domain.Erou;
import com.example.demo.Domain.Nevoie;
import com.example.demo.Domain.Persoana;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryNevoie implements RepoInterface<Long, Nevoie> {
    private String url,user,password;

    public RepositoryNevoie(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Iterable<Nevoie> findall() {
        List<Nevoie> persoanas = new ArrayList<>();
        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement statement=connection.prepareStatement("select * from nevoie")) {
            ResultSet set = statement.executeQuery();
            while (set.next())
            {
                var id = set.getLong("id");
                var nume=set.getString("titlu");
                var prenume = set.getString("descriere");
                var dt= set.getDate("deadline");
                var ajutor = set.getLong("erou");
                var ajutat = set.getLong("ajutat");
                var status= set.getString("status");
                Nevoie p = new Nevoie(nume,prenume, LocalDateTime.of(dt.toLocalDate(),LocalTime.now()));
                p.setId(id);
                p.setOmSalvator(ajutor);
                p.setOmnevoie(ajutat);
                p.setErou(Erou.valueOf(status));
                persoanas.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persoanas;
    }

    @Override
    public Nevoie findone(Nevoie nevoie) {
        return null;
    }

    public void update(Long id,Long id2)
    {
        try(Connection connection = DriverManager.getConnection(url,user, password);
            PreparedStatement statement = connection.prepareStatement("update nevoie set status=?, erou=? where id=?;")
        ){
            statement.setString(1,Erou.erou_gasit.toString());
            statement.setLong(2,id2);
            statement.setLong(3,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Nevoie nevoie) {
        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement statement=connection.prepareStatement("insert into nevoie (titlu,descriere,deadline,erou,ajutat,status) values (?,?,?,null,?,?);")) {

            statement.setString(1,nevoie.getTitlu());
            statement.setString(2,nevoie.getDescriere());
            statement.setDate(3, Date.valueOf(nevoie.getDeadline().toLocalDate()));
            statement.setLong(4,nevoie.getOmnevoie());
            statement.setString(5,Erou.caut_erou.toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}

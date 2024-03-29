package com.example.demo.Repository;

import com.example.demo.Domain.Persoana;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RepositoryPersoana implements RepoInterface<Long, Persoana> {

    private String url,user,password;


    public RepositoryPersoana(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    @Override
    public Iterable<Persoana> findall() {
        List<Persoana>  persoanas = new ArrayList<>();
        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement statement=connection.prepareStatement("select * from persoana")) {
            ResultSet set = statement.executeQuery();
            while (set.next())
            {
                var id = set.getLong("id");
                var nume=set.getString("nume");
                var prenume = set.getString("prenume");
                var usr= set.getString("username");
                var parola = set.getString("parola");
                var oras = set.getString("oras");
                var strada= set.getString("strada");
                var nr_strada= set.getString("nrstrada");
                var tel = set.getString("telefon");
                Persoana p = new Persoana(nume,prenume,usr,parola,oras,strada,nr_strada,tel);
                p.setId(id);
                persoanas.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persoanas;
    }

    @Override
    public Persoana findone(Persoana persoana) {
        return null;
    }


public Persoana findonelogin(String username)
{
    try(Connection connection= DriverManager.getConnection(url,user,password);
        PreparedStatement statement=connection.prepareStatement("select * from persoana where username=?")) {
        statement.setString(1,username);
        ResultSet set = statement.executeQuery();
        if (set.next())
        {
            var id = set.getLong("id");
            var nume=set.getString("nume");
            var prenume = set.getString("prenume");
            var usr= set.getString("username");
            var parola = set.getString("parola");
            var oras = set.getString("oras");
            var strada= set.getString("strada");
            var nr_strada= set.getString("nrstrada");
            var tel = set.getString("telefon");
            Persoana p = new Persoana(nume,prenume,usr,parola,oras,strada,nr_strada,tel);
            p.setId(id);
            return p;
        }
        return null;

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }

}

public Persoana findonebyid(Long id)
{
    try(Connection connection= DriverManager.getConnection(url,user,password);
        PreparedStatement statement=connection.prepareStatement("select * from persoana where id=?")) {
        statement.setLong(1,id);
        ResultSet set = statement.executeQuery();
        if (set.next())
        {
            var id1 = set.getLong("id");
            var nume=set.getString("nume");
            var prenume = set.getString("prenume");
            var usr= set.getString("username");
            var parola = set.getString("parola");
            var oras = set.getString("oras");
            var strada= set.getString("strada");
            var nr_strada= set.getString("nrstrada");
            var tel = set.getString("telefon");
            Persoana p = new Persoana(nume,prenume,usr,parola,oras,strada,nr_strada,tel);
            p.setId(id1);
            return p;
        }
        return null;

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }


}


    @Override
    public void save(Persoana persoana) {
        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement statement=connection.prepareStatement("insert into persoana (nume,prenume,username,parola,oras,strada,nrstrada,telefon) values (?,?,?,?,?,?,?,?);")) {

            statement.setString(1,persoana.getNume());
            statement.setString(2,persoana.getPrenume());
            statement.setString(3,persoana.getUsername());
            statement.setString(4,persoana.getParola());
            statement.setString(5,persoana.getOras());
            statement.setString(6,persoana.getStrada());
            statement.setString(7,persoana.getNumar_strada());
            statement.setString(8,persoana.getTelefon());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public Iterable<String> findallUsers() {
        List<String>  persoanas = new ArrayList<>();
        try(Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement statement=connection.prepareStatement("select username from persoana")) {
            ResultSet set = statement.executeQuery();
            while (set.next())
            {

                var usr= set.getString("username");


                persoanas.add(usr);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persoanas;
    }
}

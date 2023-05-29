package com.example;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table( name = "tester" )
public class Tester extends com.example.Entity<Long>/*Entity<Long>*/ {
    private Long id;
    private String username;
    private String parola;
//    private String functie;

    public Tester() {
        // this form used by Hibernate
    }

    public Tester(String username, String parola) {
        this.username = username;
        this.parola = parola;
//        this.functie = functie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

//    public String getFunctie() {
//        return functie;
//    }
//
//    public void setFunctie(String functie) {
//        this.functie = functie;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tester tester)) return false;
        return Objects.equals(getUsername(), tester.getUsername()) && Objects.equals(getParola(), tester.getParola());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getParola());
    }

    @Override
    public String toString() {
        return "Tester{" +
                "username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    public void setId(Long aLong) {
        this.id = aLong;
    }
}

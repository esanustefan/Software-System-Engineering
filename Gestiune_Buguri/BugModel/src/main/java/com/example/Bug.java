package com.example;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Objects;
import javax.persistence.Entity;


@Entity
@Table( name = "bug" )
public class Bug extends com.example.Entity<Long>/*Entity<Long>*/{
    private Long id;
    private String denumire;
    private String descriere;
    private String risk;
    private String status;

    public Bug() {
        // this form used by Hibernate
    }

    public Bug(String denumire, String descriere, String bugRisk, String bugStatus) {
        this.denumire = denumire;
        this.descriere = descriere;
        this.risk = bugRisk;
        this.status = bugStatus;
    }
    @Column(name = "denumire")
    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Column(name = "descriere")
    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Column(name = "risk")
    public String getBugRisk() {
        return risk;
    }

    public void setBugRisk(String bugRisk) {
        this.risk = bugRisk;
    }

    @Column(name = "status")
    public String getBugStatus() {
        return status;
    }

    public void setBugStatus(String bugStatus) {
        this.status = bugStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bug bug)) return false;
        return Objects.equals(getDenumire(), bug.getDenumire()) && Objects.equals(getDescriere(), bug.getDescriere()) && getBugRisk() == bug.getBugRisk() && getBugStatus() == bug.getBugStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDenumire(), getDescriere(), getBugRisk(), getBugStatus());
    }

    @Override
    public String toString() {
        return "Bug{" +
                "denumire='" + denumire + '\'' +
                ", descriere='" + descriere + '\'' +
                ", bugRisk=" + risk +
                ", bugStatus=" + status +
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

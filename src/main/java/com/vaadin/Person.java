package com.vaadin;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

@Entity()
@Table(name = "person")

public class Person implements Serializable {
    @Id
    @Column(name="idperson")
    private int idperson;
    @Column(name="idaddress")
    private int idaddress;
    @Column(name="firstname")
    private String firstname;
    @Column(name="lastname")
    private String lastname;
    @Column(name="birthday")
    private String birthday;
    @Column(name="street")
    private String street;
    @Column(name="number")
    private String number;
    @Column(name="plz")
    private int plz;
    @Column(name="city")
    private String city;
    @Column(name="country")
    private String country;

    public int getIdperson() {
        return idperson;
    }

    public void setIdperson(int idperson) {
        this.idperson = idperson;
    }

    public int getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(int idaddress) {
        this.idaddress = idaddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public static ArrayList<Person> getBothList(ResultSet rs){
        ArrayList<Person> list = new ArrayList<>();
        try{
            while (rs.next()) {
                Person p = new Person();
                p.setIdperson(rs.getInt("idperson"));
                p.setIdaddress(rs.getInt("idaddress"));
                p.setFirstname(rs.getString("firstname"));
                p.setLastname(rs.getString("lastname"));
                p.setBirthday(rs.getString("birthday"));
                p.setStreet(rs.getString("street"));
                p.setNumber(rs.getString("number"));
                p.setPlz(rs.getInt("plz"));
                p.setCity(rs.getString("city"));
                p.setCountry(rs.getString("country"));
                list.add(p);
            }
        }catch(Exception e){
            System.err.println(e);
        }

        return list;
    }

}

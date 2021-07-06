package com.vaadin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

@Entity()
@Table(name = "address")

public class Address implements Serializable {
    @Id
    @Column(name="idaddress")
    private int idaddress;
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

    public int getIdaddress() {
        return idaddress;
    }

    public void setIdaddress(int idaddress) {
        this.idaddress = idaddress;
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

    public static ArrayList<Address> getAddressList(ResultSet rs){
        ArrayList<Address> list = new ArrayList<>();
        try{
            while (rs.next()) {
                Address a = new Address();
                a.setIdaddress(rs.getInt("idaddress"));
                a.setStreet(rs.getString("street"));
                a.setNumber(rs.getString("number"));
                a.setPlz(rs.getInt("plz"));
                a.setCity(rs.getString("city"));
                a.setCountry(rs.getString("country"));
                list.add(a);
            }
        }catch(Exception e){
            System.err.println(e);
        }

        return list;
    }
}

package com.vaadin;

import java.sql.*;

public class SQL {


    private static Connection con;
    private static final String conString = "jdbc:mysql://localhost/adressen?useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static void open() {
        try {
            con = DriverManager.getConnection(conString, "admin", "admin");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void close() {
        try {
            con.close();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /*public static int lastInsert(){
        String query = "SELECT LAST_INSERT_ID() as 'id'";
        int id = -1;
        try{
            ResultSet rs = con.createStatement().executeQuery(query);

            if(rs.next()){
                id = Integer.parseInt(rs.getString("id"));
            }

        }catch(SQLException e){
            System.err.println(e);
        }
        return id;
    }*/


    public static int addPerson(int addressid, String firstname, String lastname, String birthday){
        String query = "INSERT INTO person (idaddress, firstname, lastname, birthday) VALUES (" + addressid + ", '" + firstname + "', '" + lastname + "', '" + birthday + "');";
        try{
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                return keys.getInt(1);
            }
        }catch(SQLException e){
            System.err.println(e);
        }
        return 0;
    }

    public static void updatePerson(int id, int addressid, String firstname, String lastname, String birthday){
        String query = "UPDATE person SET idaddress="+addressid+", firstname='"+firstname+"', lastname='"+lastname+"', birthday='"+birthday+"' WHERE idperson = " + id + ";";
        try{
            con.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println(e);
        }
    }

    public static void removePerson(int id){
        String query = "DELETE FROM person WHERE idperson = " + id + ";";
        try{
            con.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println(e);
        }
    }

    public static ResultSet selectPerson(){
        String query = "SELECT * FROM person";
        try{
            return con.createStatement().executeQuery(query);
        }catch(SQLException e){
            System.err.println(e);
        }
        return null;
    }


    public static int addAddress(String street, String number, int plz, String city, String country){
        String query = "INSERT INTO address (street, number, plz, city, country) VALUES ('"+street+"', '"+number+"', "+plz+", '"+city+"', '"+country+"')";
        try{
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if(keys.next()){
                return keys.getInt(1);
            }
        }catch(SQLException e){
            System.err.println(e);
        }
        return 0;
    }

    public static void updateAddress(int id, String street, String number, int plz, String city, String country){
        String query = "UPDATE address SET street='"+street+"', number='"+number+"', plz="+plz+", city='"+city+"', country='"+country+"' WHERE idaddress = " + id + ";";
        try{
            con.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println(e);
        }
    }

    public static void removeAddress(int id){
        String query = "DELETE FROM address WHERE idaddress = " + id + ";";
        try{
            con.createStatement().executeUpdate(query);
        }catch(SQLException e){
            System.err.println(e);
        }
    }

    public static ResultSet selectAddress(){
        String query = "SELECT * FROM address";
        try{
            return con.createStatement().executeQuery(query);
        }catch(SQLException e){
            System.err.println(e);
        }
        return null;
    }


}
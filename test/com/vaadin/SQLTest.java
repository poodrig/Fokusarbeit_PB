package com.vaadin;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class SQLTest {
    // no error = good

    @Test
    void openclose() {
        SQL.open();
        SQL.close();
    }

    @Test
    void address() {

        SQL.open();
        int id = SQL.addAddress("Notkerstrasse", "20", 9000, "St. Gallen", "Schweiz");
        SQL.updateAddress(id, "Auerfussweg", "3", 9442, "Berneck", "Schweiz");
        SQL.removeAddress(id);
        SQL.close();
    }

    @Test
    void person() {
        SQL.open();
        int id = SQL.addPerson(1, "Patrick", "Brander", "2001-03-11");
        SQL.updatePerson(id, 1, "Marco", "Brander", "1967-11-25");
        SQL.removePerson(id);
        SQL.close();
    }

    @Test
    void select() {
        SQL.open();
        SQL.selectAddress();
        SQL.selectPerson();
        SQL.selectBoth();
        SQL.close();
    }
}
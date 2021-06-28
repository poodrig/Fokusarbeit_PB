package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.Locale;

/**
 * The main view contains a button and a click listener.
 */
@Route
public class MainView extends VerticalLayout {

    Grid<Person> grid = new Grid<>(Person.class);;

    public MainView() {
        SQL.open();
        grid.setItems(Person.getBothList(SQL.selectBoth()));
        System.out.println();
        add(grid);

        Dialog dialog = dialog();
        add(dialog);

        Button addbtn = new Button("add", event -> {
            dialog.open();
        });
        add(addbtn);

        SQL.close();
    }

    Dialog dialog(){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();


        Label lblfirstname = new Label("Firstname");
        Label lbllastname = new Label("Lastname");
        Label lblbirthday = new Label("Birthday");
        Label lblstreet = new Label("Street");
        Label lblnumber = new Label("Number");
        Label lblplz = new Label("PLZ");
        Label lblcity = new Label("City");
        Label lblcountry = new Label("Country");

        TextField firstname = new TextField();
        TextField lastname = new TextField();
        DatePicker birthday = new DatePicker();
        TextField street = new TextField();
        TextField number = new TextField();
        NumberField plz = new NumberField();
        TextField city = new TextField();
        TextField country = new TextField();

        Button submit = new Button("Submit", event -> {
            SQL.open();
            int idaddress = SQL.addAddress(street.getValue(), number.getValue(), (int)Math.round(plz.getValue()), city.getValue(),country.getValue());
            SQL.addPerson(idaddress, firstname.getValue(), lastname.getValue(), birthday.getValue().toString());
            grid.setItems(Person.getBothList(SQL.selectBoth()));
            SQL.close();
            dialog.close();

        });

        Button cancel = new Button("Cancel", event -> {
            dialog.close();
        });

        form.add(lblfirstname);
        form.add(firstname);

        form.add(lbllastname);
        form.add(lastname);

        form.add(lblbirthday);
        form.add(birthday);

        form.add(lblstreet);
        form.add(street);

        form.add(lblnumber);
        form.add(number);

        form.add(lblplz);
        form.add(plz);

        form.add(lblcity);
        form.add(city);

        form.add(lblcountry);
        form.add(country);

        form.add(submit);
        form.add(cancel);
        dialog.add(form);
        return dialog;
    }
}

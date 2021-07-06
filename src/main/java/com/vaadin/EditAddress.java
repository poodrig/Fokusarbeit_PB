package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class EditAddress {

    TextField street = new TextField();
    TextField number = new TextField();
    NumberField plz = new NumberField();
    TextField city = new TextField();
    TextField country = new TextField();

    Dialog editAddress(AddressActions aa, MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Label lblstreet = new Label("Street");
        Label lblnumber = new Label("Number");
        Label lblplz = new Label("PLZ");
        Label lblcity = new Label("City");
        Label lblcountry = new Label("Country");

        Button submit = new Button("Submit", event -> {
            SQL.open();
            SQL.updateAddress(aa.selectedAddress.getIdaddress(), street.getValue(), number.getValue(), (int)Math.round(plz.getValue()), city.getValue(), country.getValue());
            mv.update();
            aa.update();
            SQL.close();
            dialog.close();

            street.setReadOnly(false);
            number.setReadOnly(false);
            plz.setReadOnly(false);
            city.setReadOnly(false);
            country.setReadOnly(false);
        });

        Button cancel = new Button("Cancel", event -> {
            street.setReadOnly(false);
            number.setReadOnly(false);
            plz.setReadOnly(false);
            city.setReadOnly(false);
            country.setReadOnly(false);

            dialog.close();
        });

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

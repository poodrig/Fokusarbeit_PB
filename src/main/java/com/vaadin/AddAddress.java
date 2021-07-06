package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;

public class AddAddress {
    Dialog addAddress(AddressActions aa, MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Label lblstreet = new Label("Street");
        Label lblnumber = new Label("Number");
        Label lblplz = new Label("PLZ");
        Label lblcity = new Label("City");
        Label lblcountry = new Label("Country");



        Button submit = new Button("Submit", event -> {

            SQL.open();
            SQL.addAddress(mv.street.getValue(), mv.number.getValue(), (int)Math.round(mv.plz.getValue()), mv.city.getValue(), mv.country.getValue());
            mv.update();
            aa.update();
            SQL.close();
            dialog.close();

            mv.street.setReadOnly(false);
            mv.number.setReadOnly(false);
            mv.plz.setReadOnly(false);
            mv.city.setReadOnly(false);
            mv.country.setReadOnly(false);
        });

        Button cancel = new Button("Cancel", event -> {
            mv.street.setReadOnly(false);
            mv.number.setReadOnly(false);
            mv.plz.setReadOnly(false);
            mv.city.setReadOnly(false);
            mv.country.setReadOnly(false);

            dialog.close();
        });

        form.add(lblstreet);
        form.add(mv.street);
        form.add(lblnumber);
        form.add(mv.number);
        form.add(lblplz);
        form.add(mv.plz);
        form.add(lblcity);
        form.add(mv.city);
        form.add(lblcountry);
        form.add(mv.country);

        form.add(submit);
        form.add(cancel);
        dialog.add(form);
        return dialog;
    }
}

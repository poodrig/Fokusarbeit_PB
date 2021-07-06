package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;

public class AddPerson {

    Dialog addPerson(MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Dialog addressDialog = mv.addressDialog();
        mv.add(addressDialog);

        Label lblfirstname = new Label("Firstname*");
        Label lbllastname = new Label("Lastname*");
        Label lblbirthday = new Label("Birthday*");
        Label lblstreet = new Label("Street");
        Label lblnumber = new Label("Number");
        Label lblplz = new Label("PLZ");
        Label lblcity = new Label("City");
        Label lblcountry = new Label("Country");



        Button submit = new Button("Submit", event -> {
            boolean filledOut = true;
            if(mv.firstname.getValue().equals("")){
                //make red
                filledOut = false;
            }
            if(mv.lastname.getValue().equals("")){
                //make red
                filledOut = false;
            }
            if(mv.birthday.getValue().toString().equals("")){
                //make red
                filledOut = false;
            }

            if(filledOut) {
                SQL.open();
                int idaddress = SQL.addAddress(mv.street.getValue(), mv.number.getValue(), (int) Math.round(mv.plz.getValue()), mv.city.getValue(), mv.country.getValue());
                SQL.addPerson(idaddress, mv.firstname.getValue(), mv.lastname.getValue(), mv.birthday.getValue().toString());
                mv.update();
                SQL.close();
                dialog.close();
            }

            mv.firstname.setReadOnly(false);
            mv.lastname.setReadOnly(false);
            mv.birthday.setReadOnly(false);
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

        Button address = new Button("Choose existing address", event -> {
            addressDialog.open();
        });

        form.add(lblfirstname);
        form.add(mv.firstname);
        form.add(lbllastname);
        form.add(mv.lastname);
        form.add(lblbirthday);
        form.add(mv.birthday);
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

        form.add(address);
        form.add(submit);
        form.add(cancel);
        dialog.add(form);
        return dialog;
    }

}

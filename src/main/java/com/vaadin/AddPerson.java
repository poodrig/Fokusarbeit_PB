package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;

public class AddPerson {

    TextField firstname = new TextField();
    TextField lastname = new TextField();
    DatePicker birthday = new DatePicker();
    TextField street = new TextField();
    TextField number = new TextField();
    NumberField plz = new NumberField();
    TextField city = new TextField();
    TextField country = new TextField();

    Address selectedAddress;

    Dialog addPerson(MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Dialog addressDialog = addressDialog();
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
            if(firstname.getValue().equals("")){
                //make red
                filledOut = false;
            }
            if(lastname.getValue().equals("")){
                filledOut = false;
            }
            if(birthday.getValue().toString().equals("")){
                filledOut = false;
            }

            if(filledOut) {
                SQL.open();
                if(selectedAddress != null){
                    SQL.addPerson(selectedAddress.getIdaddress(), firstname.getValue(), lastname.getValue(), birthday.getValue().toString());
                }
                else{
                    int idaddress = SQL.addAddress(street.getValue(), number.getValue(), (int) Math.round(plz.getValue()), city.getValue(), country.getValue());
                    SQL.addPerson(idaddress, firstname.getValue(), lastname.getValue(), birthday.getValue().toString());
                }
                mv.update();
                SQL.close();
                dialog.close();
            }

            firstname.setReadOnly(false);
            lastname.setReadOnly(false);
            birthday.setReadOnly(false);
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

        Button address = new Button("Choose existing address", event -> {
            addressDialog.open();
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

        form.add(address);
        form.add(submit);
        form.add(cancel);
        dialog.add(form);
        return dialog;
    }

    Dialog addressDialog(){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();
        Grid<Address> addressGrid = new Grid<>(Address.class);

        SQL.open();
        addressGrid.setItems(Address.getAddressList(SQL.selectAddress()));
        ArrayList<Grid.Column<Address>> list = new ArrayList<>();
        list.add(addressGrid.getColumnByKey("street"));
        list.add(addressGrid.getColumnByKey("number"));
        list.add(addressGrid.getColumnByKey("plz"));
        list.add(addressGrid.getColumnByKey("city"));
        list.add(addressGrid.getColumnByKey("country"));
        list.add(addressGrid.getColumnByKey("idaddress"));
        addressGrid.setColumnOrder(list);
        dialog.add(addressGrid);
        SQL.close();

        Label lblstreet = new Label("Street");
        Label lblnumber = new Label("Number");
        Label lblplz = new Label("PLZ");
        Label lblcity = new Label("City");
        Label lblcountry = new Label("Country");

        TextField txtstreet = new TextField();
        txtstreet.setReadOnly(true);
        TextField txtnumber = new TextField();
        txtnumber.setReadOnly(true);
        NumberField txtplz = new NumberField();
        txtplz.setReadOnly(true);
        TextField txtcity = new TextField();
        txtcity.setReadOnly(true);
        TextField txtcountry = new TextField();
        txtcountry.setReadOnly(true);

        addressGrid.addSelectionListener(event -> {
            if(addressGrid.getSelectedItems().size() == 0){
                selectedAddress = null;
                return;
            };
            selectedAddress = (Address) addressGrid.getSelectedItems().toArray()[0];
            txtstreet.setValue(selectedAddress.getStreet());
            txtnumber.setValue(selectedAddress.getNumber());
            txtplz.setValue((double) selectedAddress.getPlz());
            txtcity.setValue(selectedAddress.getCity());
            txtcountry.setValue(selectedAddress.getCountry());
        });

        form.add(lblstreet);
        form.add(txtstreet);
        form.add(lblnumber);
        form.add(txtnumber);
        form.add(lblplz);
        form.add(txtplz);
        form.add(lblcity);
        form.add(txtcity);
        form.add(lblcountry);
        form.add(txtcountry);

        Button submit = new Button("Submit", event -> {
            street.setReadOnly(true);
            number.setReadOnly(true);
            plz.setReadOnly(true);
            city.setReadOnly(true);
            country.setReadOnly(true);

            street.setValue(txtstreet.getValue());
            number.setValue(txtnumber.getValue());
            plz.setValue(txtplz.getValue());
            city.setValue(txtcity.getValue());
            country.setValue(txtcountry.getValue());

            dialog.close();
        });

        Button cancel = new Button("Cancel", event -> {
            dialog.close();
        });

        form.add(submit);
        form.add(cancel);
        dialog.add(form);
        return dialog;
    }

}

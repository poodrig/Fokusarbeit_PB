package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route
public class MainView extends VerticalLayout {

    Grid<Person> grid = new Grid<>(Person.class);
    Person selectedPerson;

    TextField firstname = new TextField();
    TextField lastname = new TextField();
    DatePicker birthday = new DatePicker();
    TextField street = new TextField();
    TextField number = new TextField();
    NumberField plz = new NumberField();
    TextField city = new TextField();
    TextField country = new TextField();

    public MainView() {
        SQL.open();
        grid.setItems(Person.getBothList(SQL.selectBoth()));

        ArrayList<Grid.Column<Person>> list = new ArrayList<>();
        list.add(grid.getColumnByKey("firstname"));
        list.add(grid.getColumnByKey("lastname"));
        list.add(grid.getColumnByKey("birthday"));
        list.add(grid.getColumnByKey("street"));
        list.add(grid.getColumnByKey("number"));
        list.add(grid.getColumnByKey("plz"));
        list.add(grid.getColumnByKey("city"));
        list.add(grid.getColumnByKey("country"));
        list.add(grid.getColumnByKey("idaddress"));
        list.add(grid.getColumnByKey("idperson"));

        grid.setColumnOrder(list);
        add(grid);

        grid.addSelectionListener(event -> {
            if(grid.getSelectedItems().size() == 0){
                selectedPerson = null;
                return;
            };
            selectedPerson = (Person) grid.getSelectedItems().toArray()[0];
        });

        DeletePerson dp = new DeletePerson();
        Dialog deletePerson = dp.deletePerson(this);
        add(deletePerson);

        EditPerson ep = new EditPerson();
        Dialog editPerson = ep.editPerson(this);
        add(editPerson);

        AddPerson ap = new AddPerson();
        Dialog addPerson = ap.addPerson(this);
        add(addPerson);

        AddressActions aa = new AddressActions();
        Dialog addressActions = aa.addressDialog(this);
        add(addressActions);

        Button deletebtn = new Button("delete", event -> {
            if(selectedPerson == null) return;
            deletePerson.open();
        });

        Button editbtn = new Button("edit", event -> {
            if(selectedPerson == null) return;
            ep.fill(selectedPerson);
            editPerson.open();
        });

        Button addbtn = new Button("add", event -> {
            addPerson.open();
        });

        Button addressbtn = new Button("Addresses", event -> {
           addressActions.open();
        });

        add(deletebtn);
        add(editbtn);
        add(addbtn);
        add(addressbtn);

        SQL.close();
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
            if(addressGrid.getSelectedItems().size() == 0) return;
            Address selected = (Address) addressGrid.getSelectedItems().toArray()[0];
            txtstreet.setValue(selected.getStreet());
            txtnumber.setValue(selected.getNumber());
            txtplz.setValue((double) selected.getPlz());
            txtcity.setValue(selected.getCity());
            txtcountry.setValue(selected.getCountry());
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

    void update(){
        grid.setItems(Person.getBothList(SQL.selectBoth()));
    }
}

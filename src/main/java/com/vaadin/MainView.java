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

        Button addressbtn = new Button("addresses", event -> {
           addressActions.open();
        });

        add(deletebtn);
        add(editbtn);
        add(addbtn);
        add(addressbtn);

        SQL.close();
    }

    void update(){
        grid.setItems(Person.getBothList(SQL.selectBoth()));
    }
}

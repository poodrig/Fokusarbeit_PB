package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.ArrayList;

public class AddressActions {

    TextField street = new TextField();
    TextField number = new TextField();
    NumberField plz = new NumberField();
    TextField city = new TextField();
    TextField country = new TextField();

    Grid<Address> addressGrid = new Grid<>(Address.class);
    Address selectedAddress;

    Dialog addressDialog(MainView mv) {
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();


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

        addressGrid.addSelectionListener(event -> {
            if(addressGrid.getSelectedItems().size() == 0) {
                selectedAddress = null;
                return;
            }
            selectedAddress = (Address) addressGrid.getSelectedItems().toArray()[0];
        });

        Button done = new Button("Done", event -> {
            dialog.close();
        });

        EditAddress ea = new EditAddress();
        Dialog editAddress = ea.editAddress(this, mv);
        Button edit = new Button("Edit", event -> {
            ea.street.setValue(selectedAddress.getStreet());
            ea.number.setValue(selectedAddress.getNumber());
            ea.plz.setValue((double)selectedAddress.getPlz());
            ea.city.setValue(selectedAddress.getCity());
            ea.country.setValue(selectedAddress.getCountry());
            editAddress.open();
        });

        DeleteAddress da = new DeleteAddress();
        Dialog deleteAddress = da.deleteAddress(this, mv);
        Dialog inuse = inuse();
        Button delete = new Button("Delete", event -> {
            SQL.open();
            if(SQL.AddressInUse(selectedAddress.getIdaddress())){
                inuse.open();
                SQL.close();
                return;
            }
            SQL.close();
            deleteAddress.open();
        });

        AddAddress aa = new AddAddress();
        Dialog addAddress = aa.addAddress(this, mv);
        Button add = new Button("Add", event -> {
            addAddress.open();
        });

        form.add(done);
        form.add(edit);
        form.add(delete);
        form.add(add);
        dialog.add(form);

        return dialog;
    }

    void update(){
        addressGrid.setItems(Address.getAddressList(SQL.selectAddress()));
    }

    Dialog inuse(){
        Dialog dialog = new Dialog();
        Label msg = new Label("Addresses in use cannot be deleted");
        Button ok = new Button("OK", event -> {
            dialog.close();
        });

        dialog.add(msg);
        dialog.add(ok);

        return dialog;
    }
}
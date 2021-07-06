package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;

public class DeleteAddress {
    Dialog deleteAddress(AddressActions aa, MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Button confirm = new Button("Delete", event -> {
            SQL.open();
            SQL.removeAddress(aa.selectedAddress.getIdaddress());
            mv.update();
            aa.update();
            SQL.close();
            dialog.close();
        });

        Button cancel = new Button("Cancel", event -> {
            dialog.close();
        });

        Label text = new Label("Do you really want to delete this Address?");

        dialog.add(text);
        form.add(cancel);
        form.add(confirm);

        dialog.add(form);
        return dialog;
    }

}

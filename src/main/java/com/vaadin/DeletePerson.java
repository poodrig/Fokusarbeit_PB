package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;

public class DeletePerson {

    Dialog deletePerson(MainView mv){
        Dialog dialog = new Dialog();
        FormLayout form = new FormLayout();

        Button confirm = new Button("Delete", event -> {
            SQL.open();
            SQL.removePerson(mv.selectedPerson.getIdperson());
            mv.update();
            SQL.close();
            dialog.close();
        });

        Button cancel = new Button("Cancel", event -> {
            dialog.close();
        });

        Label text = new Label("Do you really want to delete this Person?");

        dialog.add(text);
        form.add(cancel);
        form.add(confirm);

        dialog.add(form);
        return dialog;
    }

}

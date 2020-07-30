package com.krupatek.courier.utils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class ViewUtils {
    public static HorizontalLayout addCloseButton(Dialog dialog){
        HorizontalLayout closeBtnLayout = new HorizontalLayout();
        closeBtnLayout.setWidth("100%");
        closeBtnLayout.setPadding(false);
        closeBtnLayout.setSpacing(false);
        closeBtnLayout.setMargin(false);
        Label leftEmptyLbl = new Label("");
        leftEmptyLbl.setWidth("95%");
        Button closeButton = new Button("", VaadinIcon.CLOSE.create());
        closeButton.setWidth("5%");
        closeButton.addClickListener(buttonClickEvent -> dialog.close());
        closeBtnLayout.add(leftEmptyLbl, closeButton);
        return closeBtnLayout;
    }
}

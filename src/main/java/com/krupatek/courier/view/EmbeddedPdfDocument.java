package com.krupatek.courier.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("object")
public class EmbeddedPdfDocument extends Component implements HasSize {

    public EmbeddedPdfDocument(StreamResource resource, String width, String height) {
        this();
        getElement().setAttribute("type", "application/pdf");
        getElement().setAttribute("data", resource);
        setWidth(width);
        setHeight(height);
    }

    public EmbeddedPdfDocument(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    protected EmbeddedPdfDocument() {
        getElement().setAttribute("type", "application/pdf");
        setSizeFull();
    }
}

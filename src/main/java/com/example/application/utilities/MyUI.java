package com.example.application.utilities;

import java.util.Locale;
import com.example.application.views.MainLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinRequest;

public class MyUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        // Set default locale
        setLocale(Locale.GERMAN);

        // Build the initial UI
        add(new MainLayout());
    }
}
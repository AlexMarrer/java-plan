package com.example.application.utilities;

public record TableView(String label, String value, boolean enabled) {

    public TableView(String label, String value) {
        this(label, value, true);
    }
}

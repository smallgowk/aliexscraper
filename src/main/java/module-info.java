module com.phanduy.aliexscrap.aliexscrapper {
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires javafx.controls;

    // UI libs
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.jfxtras.styles.jmetro;

    // HTTP Client & JSON
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okhttp3.logging;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires com.google.gson;

    // Excel Processing
    requires org.apache.poi.ooxml;
    requires org.apache.commons.collections4;
    requires org.apache.commons.compress;

    // HTML Parsing (JSoup instead of Selenium)
    requires org.jsoup;

    // Utilities
    requires org.apache.commons.io;
    requires org.apache.commons.codec;
    requires commons.math3;

    // XML & Database
    requires java.xml.bind;
    requires java.sql;

    // System
    requires java.prefs;
    requires annotations;

    // Logging
    requires log4j;

    // Cho phép các package bên ngoài dùng FXML controller của bạn (nếu có)
    opens com.phanduy.aliexscrap to javafx.fxml, com.google.gson;
    opens com.phanduy.aliexscrap.api to com.google.gson, retrofit2;
    opens com.phanduy.aliexscrap.model.amazon to com.google.gson, retrofit2;
    exports com.phanduy.aliexscrap;
    opens com.phanduy.aliexscrap.model.request to com.google.gson, retrofit2;
    opens com.phanduy.aliexscrap.model.response to com.google.gson, retrofit2;
}
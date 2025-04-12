module com.phanduy.aliexscrap.aliexscrapper {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    // UI libs
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    // JSON / REST / Retrofit / OkHttp
    requires retrofit2;
    requires retrofit2.converter.gson;
    requires okhttp3;
    requires okhttp3.logging;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    // Excel
    requires org.apache.poi.ooxml;
    requires org.apache.commons.collections4;
    requires org.apache.commons.compress;

    // JAXB
    requires java.xml.bind;
    requires java.prefs;
    requires java.logging;


    // Cho phép các package bên ngoài dùng FXML controller của bạn (nếu có)
    opens com.phanduy.aliexscrap to javafx.fxml;
    exports com.phanduy.aliexscrap;

}
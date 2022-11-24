module hu.petrik.budavari_dominik_javafxrestclientdolgozat {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.budavari_dominik_javafxrestclientdolgozat to javafx.fxml;
    exports hu.petrik.budavari_dominik_javafxrestclientdolgozat;
}
module sadiq.code.addressbook {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens sadiq.code.addressbook to javafx.fxml;
    exports sadiq.code.addressbook;
}
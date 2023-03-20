module com.monterdev.monterdepos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;



    opens com.monterdev.monterdepos to javafx.fxml;
    opens com.monterdev.monterdepos.service to javafx.fxml;
    exports com.monterdev.monterdepos;
    exports com.monterdev.monterdepos.controller;
    opens com.monterdev.monterdepos.controller to javafx.fxml;
    opens com.monterdev.monterdepos.model to org.hibernate.orm.core;
}
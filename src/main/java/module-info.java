module org.example.stockmanagmentsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;

    opens org.example.stockmanagementsystem to javafx.fxml;
    exports org.example.stockmanagementsystem;
    exports org.example.stockmanagementsystem.DAOclasses;
    opens org.example.stockmanagementsystem.DAOclasses to javafx.fxml;
    exports org.example.stockmanagementsystem.searchingModels;
    opens org.example.stockmanagementsystem.searchingModels to javafx.fxml;
    exports org.example.stockmanagementsystem.helperClasses;
    opens org.example.stockmanagementsystem.helperClasses to javafx.fxml;
    exports org.example.stockmanagementsystem.AdminManagerClasses;
    opens org.example.stockmanagementsystem.AdminManagerClasses to javafx.fxml;
    exports org.example.stockmanagementsystem.DAOclassesImplementation;
    opens org.example.stockmanagementsystem.DAOclassesImplementation to javafx.fxml;
}
package com.example.carrentaldesktop;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class HelloController {

    @FXML
    private TableColumn<Car, Integer> colNapidij;
    @FXML
    private TableColumn<Car, String> colRendszam;
    @FXML
    private TableColumn<Car, String> colMarka;
    @FXML
    private TableView<Car> tableview;
    @FXML
    private TableColumn<Car, String> colModel;

    public CarDb db;

    public void initialize() {
        colRendszam.setCellValueFactory(new PropertyValueFactory<>("license_plate_number"));
        colMarka.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        colNapidij.setCellValueFactory(new PropertyValueFactory<>("daily_cost"));

        feltoltes();
    }

    public void feltoltes() {
        try {
            db = new CarDb();
            List<Car> cars = db.getCars();
            tableview.getItems().clear();
            for (Car c : cars) {
                tableview.getItems().add(c);
            }
        } catch (SQLException e) {
            Platform.runLater(() -> {
                error("Nem sikerült csatlakozni az adatbázishoz!");
                System.exit(0);
            });
        }
    }

    public void error(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(uzenet);
        alert.showAndWait();
    }

    public void alert(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(uzenet);
        alert.showAndWait();
    }

    public boolean confirm(String uzenet) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(uzenet);
        Optional<ButtonType> optional = alert.showAndWait();
        return optional.isPresent() && optional.get() == ButtonType.OK;
    }

    @FXML
    public void onTorlesClick(ActionEvent actionEvent) {
        int index = tableview.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            error("Törléshez előbb válasszon ki autót");
            return;
        }

        Car c = tableview.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos szeretné törölni a kiválasztott autót?")) {
            return;
        }

        try {
            db = new CarDb();
            boolean result = db.deleteCar(c.getId());
            if (result) {
                alert("Sikeres törlés");
            } else {
                alert("Sikertelen törlés");
            }
            feltoltes();
        } catch (SQLException e) {
            error("Valami nem jó: " + e.getSQLState());
        }
    }
}
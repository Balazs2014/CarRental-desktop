package com.example.carrentaldesktop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDb {
    Connection conn;

    public CarDb() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost/vizsga-2022", "root", "");
    }

    public List<Car> getCars() throws SQLException {
        List<Car> cars = new ArrayList<>();
        String sql = "SELECT * FROM cars";
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery(sql);
        while (result.next()) {
            int id = result.getInt("id");
            String license_plate_number = result.getString("license_plate_number");
            String brand = result.getString("brand");
            String model = result.getString("model");
            int daily_cost = result.getInt("daily_cost");
            Car c = new Car(id, license_plate_number, brand, model, daily_cost);
            cars.add(c);
        }
        return cars;
    }

    public boolean deleteCar(int id) throws SQLException {
        String sql = "DELETE FROM cars WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int erintettSorok = stmt.executeUpdate();
        return erintettSorok == 1;
    }
}

package com.example.carrentaldesktop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Statisztika {
    public static List<Car> cars = new ArrayList<>();
    public static CarDb db;

    public static List<Car> getCars() {
        List<Car> list = new ArrayList<>();
        try {
            db = new CarDb();
            list = db.getCars();
        } catch (SQLException e) {
            System.out.println("Nem sikerült kapcsolódni az adatbázishgoz!");
            System.exit(0);
        }
        return list;
    }

    public static int count() {
        int db = 0;
        for (Car c : cars) {
            if (c.getDaily_cost() < 20000) {
                db++;
            }
        }
        return db;
    }

    public static void main(String[] args) {
        cars = getCars();
        System.out.printf("20.000 Ft-nál olcsóbb napidíjú autók száma: %d", count());
    }
}

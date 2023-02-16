package ru.qa_scooter;

import com.github.javafaker.Faker;
import java.util.List;

import static java.time.LocalDateTime.now;

public class Order {

    public final String firstName;
    public final String lastName;
    public final String phone;

    public final String address;
    public final String metroStation;

    public final String deliveryDate;
    public final int rentTime;

    public final List<String> color;
    public final String comment;

    public Order(String firstName,
                 String lastName,
                 String phone,

                 String address,
                 String metroStation,

                 String deliveryDate,
                 int rentTime,

                 List<String> color,
                 String comment) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;

        this.address = address;
        this.metroStation = metroStation;

        this.deliveryDate = deliveryDate;
        this.rentTime = rentTime;

        this.color = color;
        this.comment = comment;
    }


    public static Order getParameters(List<String> color) {
        return new Order(
                "Naruto",
                "Uchiha",
                "+7 800 355 35 35",
                "Konoha, 142 apt.",
                "4",
                "2020-06-06",
                5,
                color,
                "Saske, come back to Konoha"
        );
    }
}

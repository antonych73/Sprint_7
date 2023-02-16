package ru.qa_scooter;

import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static ru.qa_scooter.RestSpecBuilderUtils.getSpecBuilder;

public class OrderMethods {

    private final static String PATH = "/api/v1/orders/";

    public ValidatableResponse create(Order order) {
        return given()
                .spec(getSpecBuilder()).body(order).when().post(PATH).then().log().all();
    }

    public ValidatableResponse orderList () {
        return given()
                .spec(getSpecBuilder()).when().get(PATH).then().log().all();
    }

    public ValidatableResponse cancel (OrderCredentials orderCredentials) {
        return given()
                .spec(getSpecBuilder()).body(orderCredentials).when().put(PATH + "cancel").then().log().all();
    }
}

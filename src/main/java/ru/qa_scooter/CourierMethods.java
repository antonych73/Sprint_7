package ru.qa_scooter;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;
import static java.lang.String.valueOf;
import static ru.qa_scooter.RestSpecBuilderUtils.getSpecBuilder;


public class CourierMethods {

    private final static String PATH = "/api/v1/courier/";

    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpecBuilder()).body(courier).when().post(PATH).then().log().all();
    }

    public ValidatableResponse login (CourierCredentials courierCredentials) {
        return given()
                .spec(getSpecBuilder()).body(courierCredentials).when().post(PATH + "login").then().log().all();
    }

    public ValidatableResponse delete (String courierId) {
        return given()
                .spec(getSpecBuilder()).when().delete(PATH + courierId).then().log().all();
    }

    public ValidatableResponse delete (int courierId) {
        return delete(valueOf(courierId));
    }

}

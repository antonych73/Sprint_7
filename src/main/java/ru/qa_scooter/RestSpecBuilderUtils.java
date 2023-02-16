package ru.qa_scooter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import static io.restassured.http.ContentType.JSON;

public final class RestSpecBuilderUtils {
    private RestSpecBuilderUtils() {
    }

    private final static String URL = "http://qa-scooter.praktikum-services.ru/";

    public static RequestSpecification getSpecBuilder() {
        return new RequestSpecBuilder().setContentType(JSON).setBaseUri(URL).build();
    }
}
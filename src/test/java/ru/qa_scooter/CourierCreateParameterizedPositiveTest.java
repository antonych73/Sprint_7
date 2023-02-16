package ru.qa_scooter;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.qa_scooter.Courier.getFull;
import static ru.qa_scooter.Courier.getWithoutName;
import static ru.qa_scooter.Utils.courierPrintln;

@RunWith(Parameterized.class)
public class CourierCreateParameterizedPositiveTest {

    public CourierMethods courierMethods;
    public int courierId;
    private final Courier courier;
    private final int status;
    private final boolean message;

    public CourierCreateParameterizedPositiveTest(Courier courier,
                                                  int status,
                                                  boolean message) {
        this.courier = courier;
        this.status = status;
        this.message = message;
    }

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }

    @After
    @Step("After test: send DELETE request to '/api/v1/courier/courierId' - to delete courier")
    public void tearDown() {
        if (courierId != 0) {
            ValidatableResponse response = courierMethods.delete(courierId);
            courierPrintln(response.extract().statusCode());
        }
    }

    @Step("After test: send POST request to '/api/v1/courier/login' - to get courier id")
    public void getCourierId(Courier courier) {
        courierId = (courierMethods.login(new CourierCredentials(courier.login, courier.password)))
                .extract().path("id");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {getFull(), 201, true},
                {getWithoutName(null), 201, true},
                {getWithoutName(EMPTY), 201, true},
        };
    }

    @Test
    @DisplayName("Check creation of courier: courier was successfully created")
    @Description("It is checked that it is possible to create a courier with all credentials or without first name")
    @Step("Create courier")
    public void testGetResponse() {

        ValidatableResponse response = courierMethods.create(courier); // Создание курьера

        response.assertThat().statusCode(status).and().body("ok", equalTo(message)); // Получение ответа + Проверка

        getCourierId(courier); // Сохранение id курьера для последующего удаления
    }

}


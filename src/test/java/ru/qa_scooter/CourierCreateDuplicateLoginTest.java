package ru.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static ru.qa_scooter.Courier.getFull;
import static ru.qa_scooter.Utils.courierPrintln;

public class CourierCreateDuplicateLoginTest {

    public int courierId;
    public CourierMethods courierMethods;


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

    @Step("Before test: send POST request to '/api/v1/courier' - to create courier")
    public void createCourier(Courier courier) {
        courierMethods.create(courier).assertThat().statusCode(201); // Создание курьера
    }


    @Step("After test: send POST request to '/api/v1/courier/login' - to get courier id")
    public void getCourierId(Courier courier) {
        courierId = (courierMethods.login(new CourierCredentials(courier.login, courier.password)))
                .extract().path("id");
    }


    @Test
    @DisplayName("Check login of courier: this login is already in use")
    @Description("It is checked that it is impossible to create a courier with the same login")
    @Step("Create courier with the same credentials")
    public void testCourierDuplicateLoginNegative() {

        Courier courier = getFull();

        createCourier(courier); // Создание курьера

        ValidatableResponse response = courierMethods.create(courier);

        response.assertThat().statusCode(409)
                .and()
                .body("code",
                        equalTo(409),
                        "message", equalTo("Этот логин уже используется. Попробуйте другой.")
                ); // Получение ответа + Проверка

        getCourierId(courier); // Сохранение id курьера для последующего удаления
    }

}
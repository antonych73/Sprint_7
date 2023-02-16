package ru.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.qa_scooter.Courier.getWithoutLogin;
import static ru.qa_scooter.Courier.getWithoutPassword;

@RunWith(Parameterized.class)
public class CourierCreateParameterizedNegativeTest {

    private final static String ERROR_MESSAGE = "Недостаточно данных для создания учетной записи";

    public CourierMethods courierMethods;
    private final Courier courier;
    private final int status;
    private final String message;

    public CourierCreateParameterizedNegativeTest(Courier courier,
                                                  int status,
                                                  String message) {
        this.courier = courier;
        this.status = status;
        this.message = message;
    }

    @Before
    public void setup() {
        courierMethods = new CourierMethods();
    }

    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {getWithoutLogin(null), 400, ERROR_MESSAGE},
                {getWithoutLogin(EMPTY), 400, ERROR_MESSAGE},
                {getWithoutPassword(null), 400, ERROR_MESSAGE},
                {getWithoutPassword(EMPTY), 400, ERROR_MESSAGE}
        };
    }

    @Test
    @DisplayName("Check creation of courier: not enough data to create")
    @Description("It is checked that it is impossible to create a courier without login or password")
    public void testGetResponse() {

        ValidatableResponse response = courierMethods.create(courier); // Создание курьера

        response.assertThat().statusCode(status).and()
                .body("code", equalTo(status), "message", equalTo(message)); // Полечение ответа + Проверка

    }

}

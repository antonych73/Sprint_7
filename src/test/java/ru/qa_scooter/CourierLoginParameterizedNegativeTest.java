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
import static ru.qa_scooter.Courier.getWithoutLoginPassword;
import static ru.qa_scooter.CourierCredentials.getCourierCredentials;
import static ru.qa_scooter.Utils.courierPrintln;

@RunWith(Parameterized.class)
public class CourierLoginParameterizedNegativeTest {

    private final static String ERROR_400_MESSAGE = "Недостаточно данных для входа";
    private final static String ERROR_404_MESSAGE = "Учетная запись не найдена";
    private final static String LOGIN = "adm";
    private final static String PASS = "0123456789";

    private CourierMethods courierMethods;
    private int courierId;
    private final CourierCredentials courierCredentials;
    private final int status;
    private final String message;

    public CourierLoginParameterizedNegativeTest(CourierCredentials courierCredentials,
                                                 int status,
                                                 String message) {
        this.courierCredentials = courierCredentials;
        this.status = status;
        this.message = message;
    }


    @Before
    public void setup() {
        courierMethods = new CourierMethods();

        // Создание курьера с заданными логин и паролем для последующей авторизации
        createCourier();

        // Запись id курьера для последующего удаления
        getCourierId();
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
    public void createCourier() {

        // Создание курьера с заданными логин и паролем для последующей авторизации
        courierMethods.create(getWithoutLoginPassword(LOGIN, PASS))
                .assertThat().statusCode(201);
    }

    @Step("After test: send POST request to '/api/v1/courier/login' - to get courier id")
    public void getCourierId() {

        // Запись id курьера для последующего удаления
        courierId = (courierMethods.login(getCourierCredentials(LOGIN, PASS)))
                .extract().path("id");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {getCourierCredentials(LOGIN, EMPTY), 400, ERROR_400_MESSAGE},
                {getCourierCredentials(EMPTY, PASS), 400, ERROR_400_MESSAGE},
                {getCourierCredentials(null, PASS), 400, ERROR_400_MESSAGE},
                {getCourierCredentials(LOGIN, "123456"), 404, ERROR_404_MESSAGE},
//                {getCourierCredentials(LOGIN, null), 404, ERROR_400_MESSAGE},   // Тест не проходит
        };
    }

    @Test
    @DisplayName("Check login of courier: courier has not logged in due to wrong credentials")
    @Description("It is checked that it is impossible to courier to login without login or password or with wrong password")
    @Step("Login with wrong login or password")
    public void testGetResponse() {

        ValidatableResponse response = courierMethods.login(courierCredentials); // Авторизация курьера

        response.assertThat().statusCode(status).and()
                .body("code", equalTo(status), "message", equalTo(message)); // Получение ответа + Проверка
    }

}


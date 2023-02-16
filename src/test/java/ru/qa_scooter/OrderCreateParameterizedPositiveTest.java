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

import static java.util.List.of;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.not;
import static ru.qa_scooter.Order.getParameters;
import static ru.qa_scooter.Utils.orderPrintln;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedPositiveTest {

    public OrderMethods orderMethods;
    private int orderTrack;
    private final Order order;
    private final int status;

    public OrderCreateParameterizedPositiveTest(Order order,
                                                int status) {
        this.order = order;
        this.status = status;
    }


    @Before
    public void setup() {
        orderMethods = new OrderMethods();
    }

    @After
    @Step("After test: send PUT request to '/api/v1/orders/cancel' - to cancel order")
    public void tearDown() {

        if (orderTrack != 0) {
            ValidatableResponse response = orderMethods.cancel(new OrderCredentials(orderTrack));
            orderPrintln(response.extract().statusCode());

        }
    }


    @Step("After test: get order track from response")
    public void getOrderTrack(ValidatableResponse response) {

        // Запись track номера заказа для последующей отмены
        orderTrack = response.extract().path("track");
    }


    @Parameterized.Parameters
    public static Object[][] getParams() {
        return new Object[][]{
                {getParameters(of("BLACK")), 201},
                {getParameters(of("GREY")), 201},
                {getParameters(of("BLACK", "GREY")), 201},
                {getParameters(of(EMPTY)), 201}
        };
    }

    @Test
    @DisplayName("Check creation of order: order was successfully created")
    @Description("It is checked that it is possible to create an order with or without color data")
    @Step("Create order")
    public void testGetResponse() {

        ValidatableResponse response = orderMethods.create(order); // Создание заказа

        // Получение ответа + Проверка
        response.assertThat().statusCode(status).and().body("track", allOf(notNullValue(), is(not(0))));

        getOrderTrack(response); // Запись id заказа для последующей отмены

    }

}

